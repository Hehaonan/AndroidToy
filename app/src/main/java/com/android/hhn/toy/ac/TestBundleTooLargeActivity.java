package com.android.hhn.toy.ac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hhn.toy.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestBundleTooLargeActivity extends AppCompatActivity {

    private static final String TAG = "BundleTooLarge";
    private TextView mJumpTv;
    private TextView mClearTaskTv;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_bundle_too_large);
        mJumpTv = findViewById(R.id.jump_page_tv);
        mJumpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestBundleTooLargeActivity.this, TestBundleTooLargeActivity.class);
                intent.putExtras(getBundle());
                // Parcel data = Parcel.obtain();
                // intent.writeToParcel(data, 0);
                // int intentSize = data.dataSize();
                // Log.d(TAG, "传递前 intentSize :--------> " + intentSize);
                boolean judge = isBundleSizeTooLarge(" --- 传递前 ---> ", intent);
                // Log.d(TAG, "传递前 :--------> " + intent.getExtras().toString());
                if (!judge) {
                    TestBundleTooLargeActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(TestBundleTooLargeActivity.this, "TransactionTooLargeException", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (intent != null && bundle != null) {
            // Parcel data = Parcel.obtain();
            // intent.writeToParcel(data, 0);
            // int intentSize = data.dataSize();
            // Log.d(TAG, "传递后 : intent size " + intentSize);

            // 传递完之后bundle序列化过 就有真实大小了
            Log.d(TAG, "传递后 >>>>>> : bundle size " + bundle.toString());
            isBundleSizeTooLarge("传递后 ", intent);
        }
        mClearTaskTv = findViewById(R.id.clear_task_tv);
        mClearTaskTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog mNetTipDialog = new AlertDialog.Builder(TestBundleTooLargeActivity.this)
                        .setTitle("提示").setMessage("很抱歉，您打开的页面太多，无法打开新的页面，请点击“返回首页”后重新操作。")
                        .setPositiveButton("返回首页", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setClassName(getApplicationContext(), "com.android.hhn.toy.MainActivity");
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).create();
                mNetTipDialog.setCancelable(false);
                mNetTipDialog.setCanceledOnTouchOutside(false);
                mNetTipDialog.show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isBundleSizeTooLarge(String pos, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            DecimalFormat df = new DecimalFormat("0.00");
            long start = System.nanoTime();
            Bundle bundle = intent.getExtras();
            int dataSize = getBundleSize(bundle);
            Log.d(TAG, pos + ": bundle 原始 size ：" + dataSize);

            // Android O API=26 以上才有这个方法 需要处理
            long temp = System.nanoTime();
            Bundle copyBundle = bundle.deepCopy();
            Log.d(TAG, pos + ": deepCopy耗时 >>>>>>>>>>>：" + df.format(((System.nanoTime() - temp)) / 1000000D) + " 毫秒 ");
            int realSize = getBundleSize(copyBundle);
            Log.d(TAG, pos + ": writeToParcel耗时 >>>>>>>>>>>：" + df.format(((System.nanoTime() - temp)) / 1000000D) + " 毫秒 ");
            Log.d(TAG, pos + ": bundle deepCopy size ：" + realSize);

            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("[");
            for (Object key : copyBundle.keySet().toArray()) {
                copyBundle.remove(key.toString());
                int newBundleSize = getBundleSize(copyBundle);
                strBuilder.append(key).append(":").append(realSize - newBundleSize).append(",");
                realSize = newBundleSize;
            }
            strBuilder.append("]");
            Log.d(TAG, pos + "bundle get each key size : " + strBuilder);

            if (realSize > 512 * 1024) {// 大于512k
                return true;
            }

            long end = System.nanoTime();
            long diff = end - start;

            String s = df.format(diff / 1000000D);
            Log.d(TAG, pos + ": bundle ----> kb size ：" + Double.parseDouble(df.format(realSize / 1024D)));
            Log.d(TAG, pos + ": getBundleSize耗时：" + s + " 毫秒 ");// 耗时有点过分 需要异步处理
            Log.d(TAG, pos + ": 当前页面：" + intent.getComponent().getClassName());

            // try { // 反射调用 Bundle(Parcel parcelledData) 行不通
            //     Constructor constructor = Bundle.class.getDeclaredConstructor(android.os.Parcel.class);
            //     constructor.setAccessible(true);
            //     Bundle temp = (Bundle) constructor.newInstance(data);
            //     Log.d(TAG, "反射: " + temp.toString());
            // } catch (NoSuchMethodException e) {
            //     e.printStackTrace();
            // } catch (IllegalAccessException e) {
            //     e.printStackTrace();
            // } catch (InstantiationException e) {
            //     e.printStackTrace();
            // } catch (InvocationTargetException e) {
            //     e.printStackTrace();
            // }

            try {
                Method m = bundle.getClass().getMethod("getSize");
                Log.d(TAG, pos + ": bundle 原始 size，反射法：" + m.invoke(bundle, null));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private int getBundleSize(Bundle bundle) {
        Parcel deepData = Parcel.obtain();
        bundle.writeToParcel(deepData, 0);
        // bundle数据大耗时会长 需要异步处理
        return deepData.dataSize();// 单位是byte
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("key_String", "The Binder transaction failed because it was too large.\n" +
                "During a remote procedure call, the arguments and the return value of the call are transferred as Parcel objects stored in the " +
                "Binder transaction buffer. If the arguments or the return value are too large to fit in the transaction buffer, then the call will" +
                " fail and TransactionTooLargeException will be thrown.\n" +
                "The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all transactions in progress for the " +
                "process. Consequently this exception can be thrown when there are many transactions in progress even when most of the individual " +
                "transactions are of moderate size.\n" +
                "// 一般进程中有 1MB Binder transaction buffer 共享传递的数据，大小超过这个buffer，则会抛出该异常。The Binder transaction failed because it was too large.");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test_ic_launcher);
        //bundle.putParcelable("key_bitmap1", bitmap);
        bundle.putParcelable("key_Class", new Person("Person1", 34, false));
        ArrayList<Person> list = new ArrayList<>();
        list.add(new Person("Person1", 34, false));
        list.add(new Person("Person2", 3, false));
        list.add(new Person("Person4", 4, false));
        list.add(new Person("Person3", 41, false));
        list.add(new Person("Person5", 21, false));
        bundle.putParcelableArrayList("key_List", list);
        bundle.putParcelable("key_bitmap1", bitmap);
        return bundle;
    }

    private static class Person implements Parcelable {
        String name;
        int age;
        Boolean isAlive;

        public Person(String name, int age, Boolean isAlive) {
            this.name = name;
            this.age = age;
            this.isAlive = isAlive;
        }

        protected Person(Parcel in) {
            name = in.readString();
            age = in.readInt();
            byte tmpIsAlive = in.readByte();
            isAlive = tmpIsAlive == 0 ? null : tmpIsAlive == 1;
        }

        public static final Creator<Person> CREATOR = new Creator<Person>() {
            @Override
            public Person createFromParcel(Parcel in) {
                return new Person(in);
            }

            @Override
            public Person[] newArray(int size) {
                return new Person[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(age);
            dest.writeByte((byte) (isAlive == null ? 0 : isAlive ? 1 : 2));
        }
    }

    public String bitmapToString(Bitmap bitmap) {
        //将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

}

