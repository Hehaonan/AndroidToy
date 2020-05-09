package com.android.hhn.toy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;

import androidx.navigation.ui.AppBarConfiguration;

public class TestTooLargeActivity extends AppCompatActivity {

    private static final String TAG = "TooLarge";
    private int[] mProcessIds;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView mTextView;
    private int quitClickCount;
    private Handler mHandler = new Handler();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState: ");
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_toolarge);
        mTextView = findViewById(R.id.main_tv);
        mTextView.setText("TestTooLargeActivity");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestTooLargeActivity.class);
                intent.putExtras(getBundle());
                // Parcel data = Parcel.obtain();
                // intent.writeToParcel(data, 0);
                // int intentSize = data.dataSize();
                // Log.d(TAG, "传递前 intentSize :--------> " + intentSize);
                boolean judge = isBundleSizeTooLarge(" --- 传递前 ---> ", intent);
                // Log.d(TAG, "传递前 :--------> " + intent.getExtras().toString());
                if (!judge) {
                    startActivity(intent);
                } else {
                    Toast.makeText(TestTooLargeActivity.this, "TransactionTooLargeException", Toast.LENGTH_SHORT).show();
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
            // Log.d(TAG, "传递后 : bundle size " + bundle.toString());
            isBundleSizeTooLarge("传递后 ", intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isBundleSizeTooLarge(String pos, Intent intent) {
        if (intent != null && intent.getExtras() != null) {
            long start = System.nanoTime();
            Bundle bundle = intent.getExtras();
            Parcel data = Parcel.obtain();
            bundle.writeToParcel(data, 0);
            int dataSize = data.dataSize();
            Log.d(TAG, pos + ": bundle 原始 size ：" + dataSize);

            // Android O API=26 以上才有这个方法 需要处理
            Bundle copyBundle = bundle.deepCopy();
            Parcel deepData = Parcel.obtain();
            copyBundle.writeToParcel(deepData, 0);
            int realSize = deepData.dataSize();
            Log.d(TAG, pos + ": bundle 传递前原始 size ：" + realSize);

            if (realSize > 512 * 1024) {// 大于512k
                return true;
            }

            long end = System.nanoTime();
            long diff = end - start;
            DecimalFormat df = new DecimalFormat("0.00");
            String s = df.format((double) diff / 1000000L);
            // Log.d(TAG, pos + "getBundleSize耗时: 纳秒 " + diff);
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
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("bundle", "The Binder transaction failed because it was too large.\n" +
                "During a remote procedure call, the arguments and the return value of the call are transferred as Parcel objects stored in the " +
                "Binder transaction buffer. If the arguments or the return value are too large to fit in the transaction buffer, then the call will" +
                " fail and TransactionTooLargeException will be thrown.\n" +
                "The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all transactions in progress for the " +
                "process. Consequently this exception can be thrown when there are many transactions in progress even when most of the individual " +
                "transactions are of moderate size.\n" +
                "// 一般进程中有 1MB Binder transaction buffer 共享传递的数据，大小超过这个buffer，则会抛出该异常。The Binder transaction failed because it was too large.");

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
//        bundle.putParcelable("bitmap", bitmap);

        //        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
        //        bundle.putParcelable("bitmap2", bitmap2);

        //        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
        //        bundle.putParcelable("bitmap2", bitmap2);
        //        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
        //        bundle.putParcelable("bitmap3", bitmap2);
        //        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
        //        bundle.putParcelable("bitmap2", bitmap3);
        return bundle;
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

