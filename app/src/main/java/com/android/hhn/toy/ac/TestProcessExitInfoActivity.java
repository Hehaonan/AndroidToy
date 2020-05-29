package com.android.hhn.toy.ac;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hhn.toy.R;

public class TestProcessExitInfoActivity extends AppCompatActivity {

    private static final String TAG = "ProcessExitInfo";
    private TextView mCrashTv;
    private TextView mANRTv;

    @RequiresApi(api = 30)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_process_exit_info);
        mCrashTv = findViewById(R.id.crash_tv);
        mCrashTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "create null exception", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + getStr().length());
            }
        });
        mANRTv = findViewById(R.id.anr_tv);
        mANRTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "create anr", Toast.LENGTH_SHORT).show();
                createANR();
            }
        });
        getAppExitInfo();
    }

    private String getStr() {
        // ApplicationExitInfo(timestamp = 2020 / 5 / 22下午4:04 pid = 13360 realUid = 10214 packageUid = 10214 definingUid = 10214 user = 0
        // process = com.android.hhn.toy reason = 4 (APP CRASH(EXCEPTION))status = 0 importance = 100 pss = 0.00 rss = 0.00 description = crash
        return null;
    }

    private void createANR() {
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ApplicationExitInfo(timestamp = 2020 / 5 / 22下午3:51 pid = 11154 realUid = 10214 packageUid = 10214 definingUid = 10214 user = 0
        // process = com.android.hhn.toy reason = 6 (ANR) status = 0 importance = 100 pss = 0.00 rss = 0.00 description = user request after error

        // System.exit(0);退出方法也会记录
        // ApplicationExitInfo(timestamp = 2020 / 5 / 22下午4:05 pid = 13697 realUid = 10214 packageUid = 10214 definingUid = 10214 user = 0
        // process = com.android.hhn.toy reason = 1 (EXIT_SELF) status = 0 importance = 100 pss = 0.00 rss = 0.00 description = null

        // 杀进程触发
        // ApplicationExitInfo(timestamp = 2020 / 5 / 22下午4:08 pid = 14155 realUid = 10214 packageUid = 10214 definingUid = 10214 user = 0
        // process = com.android.hhn.toy reason = 10 (OTHER KILLS BY SYSTEM)status = 0 importance = 400 pss = 0.00 rss = 0.00 description = remove
        // task
    }

    /**
     * 获取进程退出信息
     */
    @RequiresApi(api = 30)
    private void getAppExitInfo() {
//        Log.d(TAG, "getAppExitInfo: " + Build.VERSION.SDK_INT);
//        Log.d(TAG, "getAppExitInfo: " + android.os.Build.BRAND);
//        Log.d(TAG, "getAppExitInfo: " + android.os.Build.VERSION.RELEASE);
//        if (android.os.Build.VERSION.RELEASE.equals("R")) {
//            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//            Log.d(TAG, "getAppExitInfo myPid: " + android.os.Process.myPid());
//            // 0为所有进程信息
//            List<ApplicationExitInfo> exitInfoList = am.getHistoricalProcessExitReasons(this.getPackageName(), 0, 10);
//            if (exitInfoList != null && !exitInfoList.isEmpty()) {
//                for (ApplicationExitInfo info : exitInfoList) {
//                    Log.d(TAG, "getAppExitInfo: " + info.toString());
//                }
//            } else {
//                Log.d(TAG, "getAppExitInfo: is null");
//            }
//        } else {
//            Log.d(TAG, "android version too low");
//        }
    }
}

