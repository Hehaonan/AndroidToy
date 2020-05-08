package com.android.hhn.toy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        //outState.putAll(getBundle());
        //getBundleSize("onSaveInstanceState", outState);
    }

    private void getBundleSize(String pos, Bundle bundle) {
        if (bundle != null) {
            long time = System.nanoTime();
            Parcel data = Parcel.obtain();
            bundle.writeToParcel(data, 0);
            int dataSize = data.dataSize();

            Log.d(TAG, pos + " getBundleSize: dataSize " + dataSize);
            long end = System.nanoTime();
            Log.d(TAG, "getBundleSize: time " + (end - time));
            Log.d(TAG, "getBundleSize: " + this.getLocalClassName());
            Log.d(TAG, "getBundleSize: " + this.getComponentName());

        }

        //        try {
        //            Method m = bundle.getClass().getMethod("getSize");
        //            Log.d(TAG, "getBundleSize: getSize " + m.invoke(bundle, null));
        //        } catch (NoSuchMethodException e) {
        //            e.printStackTrace();
        //        } catch (IllegalAccessException e) {
        //            e.printStackTrace();
        //        } catch (InvocationTargetException e) {
        //            e.printStackTrace();
        //        }

        //int getSize = ReflectUtils.invokeMethod(bundle.getClass(), "getSize");
        //Log.d(TAG, pos + "getBundleSize getSize: " + getSize);
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
                for (String key : intent.getExtras().keySet()) {
                    Log.d(TAG, key + "->");
                }
                getBundleSize("onClick()", intent.getExtras());

                //                Parcel data = Parcel.obtain();
                //                intent.writeToParcel(data, 0);
                //                int dataSize = data.dataSize();
                //                Log.d(TAG, "onClick: " + dataSize);

                startActivity(intent);
            }
        });

        getBundleSize("onCreate()", getIntent().getExtras());
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
                "// 一般进程中有 1MB Binder transaction buffer 共享传递的数据，大小超过这个buffer，则会抛出该异常。");
        //        bundle.putString("bundle4", "The Binder transaction failed because it was too large.\n" +
        //                "During a remote procedure call, the arguments and the return value of the call are transferred as Parcel objects stored
        //                in the " +
        //                "Binder transaction buffer. If the arguments or the return value are too large to fit in the transaction buffer, then the
        //                call will" +
        //                " fail and TransactionTooLargeException will be thrown.\n" +
        //                "The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all transactions in progress
        //                for the " +
        //                "process. Consequently this exception can be thrown when there are many transactions in progress even when most of the
        //                individual " +
        //                "transactions are of moderate size.\n" +
        //                "// 一般进程中有 1MB Binder transaction buffer 共享传递的数据，大小超过这个buffer，则会抛出该异常。");
        //        bundle.putString("bundle2", "The Binder transaction failed because it was too large.\n" +
        //                "During a remote procedure call, the arguments and the return value of the call are transferred as Parcel objects stored
        //                in the " +
        //                "Binder transaction buffer. If the arguments or the return value are too large to fit in the transaction buffer, then the
        //                call will" +
        //                " fail and TransactionTooLargeException will be thrown.\n" +
        //                "The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all transactions in progress
        //                for the " +
        //                "process. Consequently this exception can be thrown when there are many transactions in progress even when most of the
        //                individual " +
        //                "transactions are of moderate size.\n" +
        //                "// 一般进程中有 1MB Binder transaction buffer 共享传递的数据，大小超过这个buffer，则会抛出该异常。");
        //        bundle.putString("bundle3", "The Binder transaction failed because it was too large.\n" +
        //                "During a remote procedure call, the arguments and the return value of the call are transferred as Parcel objects stored
        //                in the " +
        //                "Binder transaction buffer. If the arguments or the return value are too large to fit in the transaction buffer, then the
        //                call will" +
        //                " fail and TransactionTooLargeException will be thrown.\n" +
        //                "The Binder transaction buffer has a limited fixed size, currently 1Mb, which is shared by all transactions in progress
        //                for the " +
        //                "process. Consequently this exception can be thrown when there are many transactions in progress even when most of the
        //                individual " +
        //                "transactions are of moderate size.\n" +
        //                "// 一般进程中有 1MB Binder transaction buffer 共享传递的数据，大小超过这个buffer，则会抛出该异常。");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
        bundle.putParcelable("bitmap", bitmap);
        //        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
        //        bundle.putParcelable("bitmap3", bitmap2);
        //        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.aa_spider_ic_launcher);
        //        bundle.putParcelable("bitmap2", bitmap3);
        return bundle;
    }

}

