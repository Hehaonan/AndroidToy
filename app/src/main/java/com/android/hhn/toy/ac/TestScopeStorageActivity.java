package com.android.hhn.toy.ac;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.hhn.toy.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TestScopeStorageActivity extends AppCompatActivity {

    private static final String TAG = "ScopeStorage";
    private TextView mTextView;

    @RequiresApi(api = 30)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_common);
        mTextView = findViewById(R.id.ac_common_tv);
        mTextView.setText(getClass().getSimpleName());
        testScopeStorage();
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

}

