package com.android.hhn.toy;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ApplicationExitInfo;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hhn.toy.jobscheduler.MyJobService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AndroidToy";
    private int[] mProcessIds;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView mTextView;
    private int quitClickCount;
    private Handler mHandler = new Handler();

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        quitClickCount++;
        if (quitClickCount == 1) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    quitClickCount = 0;
                }
            }, 2000);
        } else if (quitClickCount == 2) {
            quitApp();
        }
    }

    private void quitApp() {
        //finish();
        if (null != mProcessIds && mProcessIds.length > 0) {
            Log.d(TAG, "quitApp length: " + mProcessIds.length);
            for (int pid : mProcessIds) {
                Log.d(TAG, "quitApp: kill " + pid);
                Process.killProcess(mProcessIds[0]);
            }
        } else {
            Process.killProcess(Process.myPid());
        }
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);
        mTextView = findViewById(R.id.showDialog_tv);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                getStr().length();
            }
        });
        findViewById(R.id.jumpTo_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestTooLargeActivity.class);
                startActivity(intent);
            }
        });

        getAppExitInfo();

        ThreadPoolUtils poolUtils = new ThreadPoolUtils();
        poolUtils.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 1");
            }
        });
        poolUtils.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 2");
            }
        });
    }

    private String getStr() {
        return null;
    }

    private static class ThreadPoolUtils {
        private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue();
        private static final ThreadFactory sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, "basectx #" + this.mCount.getAndIncrement());
            }
        };

        private static ThreadPoolExecutor executor;

        static {
            executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
            executor.allowCoreThreadTimeOut(true);
            Log.d(TAG, "static initializer: ");
        }

        public ThreadPoolUtils() {
            Log.d(TAG, "ThreadPoolUtils: init");
        }

        private void execute(Runnable runnable) {
            executor.execute(runnable);
        }
    }

    /**
     * 获取崩溃信息
     */
    @RequiresApi(api = 30)
    private void getAppExitInfo() {
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ApplicationExitInfo> exitInfoList = am.getHistoricalProcessExitReasons(this.getPackageName(), Process.myPid(), 10);
        if (exitInfoList != null && !exitInfoList.isEmpty()) {
            for (ApplicationExitInfo info : exitInfoList) {
                Log.d(TAG, "getAppExitInfo: " + info.getReason());
                Log.d(TAG, "getAppExitInfo: " + info.getDescription());
                Log.d(TAG, "getAppExitInfo: " + info.getProcessName());
                Log.d(TAG, "getAppExitInfo: " + info.toString());
            }
        } else {
            Log.d(TAG, "getAppExitInfo: is null");
        }
    }

    /**
     * 测试分区存储
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void testScopeStorage() {

        printPath(this.getFilesDir().getPath());
        printPath(this.getCacheDir().getPath());
        printPath(Environment.getExternalStorageDirectory().getPath());

        printPath(this.getExternalCacheDir().getPath());
        printPath(this.getExternalFilesDir("XXX").getPath());
        for (File f : this.getExternalMediaDirs()) {
            printPath(f.getAbsolutePath());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.i(TAG, "Scoped Storage: " + Environment.isExternalStorageLegacy());
        }
        File file = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "test1.txt");
        try {
            Log.i(TAG, "create：" + file.createNewFile());
        } catch (IOException e) {
            Log.i(TAG, "create fail：" + e.getMessage());
            e.printStackTrace();
        }
        saveGid("test" + System.currentTimeMillis());
        initGuid();
    }

    private void saveGid(String id) {
        BufferedWriter bw = null;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                String path = Environment.getExternalStorageDirectory().getPath() + "/Android/";
                File file = new File(path, ".zest10010");
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
                bw.write(id);
                bw.flush();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e1) {
                    Log.e(TAG, e1.getMessage());
                }
            }
        }
    }

    private void initGuid() {
        String gidFromExternal = null;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                String path = Environment.getExternalStorageDirectory().getPath() + "/Android/";
                File file = new File(path, ".zest10010");//  .unique
                if (file.exists()) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        gidFromExternal = br.readLine();
                        Log.d(TAG, "initGuid: " + gidFromExternal);
                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printPath(String s) {
        Log.d(TAG, s);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startJobScheduler() {
        JobScheduler mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        //Builder构造方法接收两个参数，第一个参数是jobId，每个app或者说uid下不同的Job,它的jobId必须是不同的
        //第二个参数是我们自定义的JobService,系统会回调我们自定义的JobService中的onStartJob和onStopJob方法
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(), MyJobService.class.getName()));
        //指定每三秒钟重复执行一次
        builder.setPeriodic(3000);
        //builder.setMinimumLatency(1000);//设置延迟调度时间
        //builder.setOverrideDeadline(2000);//设置最大延迟截至时间
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);//设置所需网络类型
        builder.setRequiresDeviceIdle(true);//设置在DeviceIdle时执行Job
        builder.setRequiresCharging(true);//设置在充电时执行Job
        //builder.setExtras(extras);//设置一个额外的附加项

        if (mJobScheduler.schedule(builder.build()) <= 0) {
            Toast.makeText(this, "JobScheduler 执行失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void popWindow() {
        LayoutInflater inflater = LayoutInflater.from(this);//获取一个填充器
        View view = inflater.inflate(R.layout.spider_splash_privacy_net_tip, null);
        TextView textView1 = view.findViewById(R.id.privacy_disagree_button);
        TextView textView2 = view.findViewById(R.id.privacy_agree_button);
        final PopupWindow popWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setFocusable(false);
        popWindow.setOutsideTouchable(false);// 一起设置才生效
        popWindow.setTouchable(true);
        popWindow.setClippingEnabled(false);
        //        WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
        //        params.alpha = 0;//设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //        getWindow().setAttributes(params);//把该参数对象设置进当前界面中
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {//设置PopupWindow退出监听器
            @Override
            public void onDismiss() {//如果PopupWindow消失了，即退出了，那么触发该事件，然后把当前界面的透明度设置为不透明
                //                WindowManager.LayoutParams params = getWindow().getAttributes();
                //                params.alpha = 1.0f;//设置为不透明，即恢复原来的界面
                //                getWindow().setAttributes(params);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        //第一个参数为父View对象，即PopupWindow所在的父控件对象，第二个参数为它的重心，后面两个分别为x轴和y轴的偏移量
        popWindow.showAtLocation(inflater.inflate(R.layout.content_main2, null), Gravity.CENTER, 0, 0);

    }


    private AlertDialog mNetTipDialog;

    private void showDialog() {
        ScrollView sc = (ScrollView) getLayoutInflater().inflate(R.layout.spider_splash_dialog, null);
        TextView textView = sc.findViewById(R.id.dialog_privacy_content_tv);

        String textStart = getString(R.string.spider_splash_privacy_text_start);
        String privacyText = getString(R.string.spider_splash_privacy_text);
        String permissionText = getString(R.string.spider_splash_privacy_text_permission_start);
        String permissionLocationTitle = getString(R.string.spider_splash_privacy_text_permission_location_title);
        String permissionLocationContent = getString(R.string.spider_splash_privacy_text_permission_location_content);
        String permissionInfoTitle = getString(R.string.spider_splash_privacy_text_permission_info_title);
        String permissionInfoContent = getString(R.string.spider_splash_privacy_text_permission_info_content);
        String permissionStorageTitle = getString(R.string.spider_splash_privacy_text_permission_storage_title);
        String permissionStorageContent = getString(R.string.spider_splash_privacy_text_permission_storage_content);
        String textEnd = getString(R.string.spider_splash_privacy_text_end);

        SpannableStringBuilder agreementBuilder = new SpannableStringBuilder();
        agreementBuilder.append(textStart)
                .append(dealTextClick(privacyText))
                .append(permissionText)
                .append(dealTextBold(permissionLocationTitle))
                .append(permissionLocationContent)
                .append(dealTextBold(permissionInfoTitle))
                .append(permissionInfoContent)
                .append(dealTextBold(permissionStorageTitle))
                .append(permissionStorageContent)
                .append(dealTextClick(privacyText))
                .append(textEnd);

        textView.setText(agreementBuilder);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog mNetTipDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示").setMessage("测试文案啊-测试文案啊-测试文案啊-测试文案啊-测试文案啊-测试文案啊-测试文案啊-测试文案啊！！！")
                        //                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        //                            @Override
                        //                            public void onClick(DialogInterface dialog, int which) {
                        //                                dialog.dismiss();
                        //                            }
                        //                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                mNetTipDialog.setCancelable(true);
                mNetTipDialog.setCanceledOnTouchOutside(true);
                mNetTipDialog.show();
            }
        });
        mNetTipDialog = new AlertDialog.Builder(this)
                .setTitle("提示").setView(sc)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mNetTipDialog = null;
                    }
                }).create();
        mNetTipDialog.setCancelable(false);
        mNetTipDialog.setCanceledOnTouchOutside(false);
        mNetTipDialog.show();
    }

    private String dealTextBold(String permissionLocationTitle) {
        return permissionLocationTitle;
    }

    private String dealTextClick(String privacyText) {
        return privacyText;
    }

    public void getPidByProcessName(Context context) {
        //        if (TextUtils.isEmpty(packageName)) {
        //            return -1;
        //        }
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager.getRunningAppProcesses();
        if (appProcessList != null && appProcessList.size() > 0) {
            mProcessIds = new int[appProcessList.size()];
            for (int i = 0; i < appProcessList.size(); i++) {
                ActivityManager.RunningAppProcessInfo appProcess = appProcessList.get(i);
                Log.d(TAG, "getPidByProcessName: " + appProcess.processName);
                mProcessIds[i] = appProcess.pid;
                if (appProcess.processName.contains("com.Qunar")) {
                    Log.d(TAG, "getPidByProcessName: " + appProcess.processName + ", appProcess.pid:" + appProcess.pid);
                }
            }
        }
    }


    private String getSupportStoreType(Context context) {
        String vivoStorePkg = "com.Qunar";
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(vivoStorePkg, 0);
            code = info.versionCode;
            Log.d("vivo", info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("vivo", "get code error=" + code);
        }
        Log.d("vivo", "code=" + code);
        //3100：vivo要求的版本
        return code > 100 ? vivoStorePkg : "";
    }

    private void jumpToOtherAppStoreUpdate() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("market://details?id=com.Qunar");//app包名
            intent.setData(uri);
            //intent.setPackage("com.bbk.appstore");//vivo
            //intent.setPackage("com.huawei.appmarket");//hw
            //intent.setPackage("com.sec.android.app.samsungapps");//三星
            startActivity(intent);
            Log.d("vivo", "code= success");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("vivo", "code= error");
        }
    }

    public void goToSamsungappsMarket() {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=com.Qunar");
        Intent goToMarket = new Intent();
        goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
        goToMarket.setData(uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

}

