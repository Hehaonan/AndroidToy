package com.android.hhn.toy.ac;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    @RequiresApi(api = Build.VERSION_CODES.Q)
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
        getGuid();

        // 可以指定的外部存储卷名
        for (String volumeName : MediaStore.getExternalVolumeNames(this)) {
            Log.d(TAG, ">>>>> " + volumeName);
            Log.d(TAG, ">>>>> " + MediaStore.Images.Media.getContentUri(volumeName));
            Log.d(TAG, ">>>>> " + MediaStore.Video.Media.getContentUri(volumeName));
            Log.d(TAG, ">>>>> " + MediaStore.Audio.Media.getContentUri(volumeName));
            Log.d(TAG, ">>>>> " + MediaStore.Downloads.getContentUri(volumeName));
            Log.d(TAG, ">>>>> " + MediaStore.Files.getContentUri(volumeName));
        }
        // 默认的外部存储卷名
        Log.d(TAG, "##### " + MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Log.d(TAG, "##### " + MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        Log.d(TAG, "##### " + MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Log.d(TAG, "##### " + MediaStore.Downloads.EXTERNAL_CONTENT_URI);
        Log.d(TAG, "##### " + MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL));

        //createFile();
        readFile();
    }

    private void createFile() {
        // 构建文件
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "myTest.txt");
        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
        // 可以指定具体路径，不指定默认使用Download文件夹
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);

        // 指定根目录Uri
        Uri external = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
        ContentResolver resolver = this.getContentResolver();

        // 合成目标地址的Uri
        Uri insertUri = resolver.insert(external, values);
        // 此uri可以用于操作文件
        Log.d(TAG, "createFile: " + insertUri);

        OutputStream os = null;
        try {
            if (insertUri != null) {
                os = resolver.openOutputStream(insertUri);
                if (os != null) {
                    os.write("test_id:123456".getBytes());
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "createFile fail: " + e.getCause());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "createFile fail in close: " + e.getCause());
            }
        }
    }

    private void readFile() {
        InputStream inputStream = null;
        BufferedReader br = null;
        Uri uri = Uri.parse("content://media/external/file/726258");
        try {
            inputStream = this.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
                Log.d(TAG, "readFile:" + br.readLine());
            }
        } catch (IOException e) {
            Log.d(TAG, "readFile fail: " + e.getCause());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "readFile fail in close: " + e.getCause());
            }
        }
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
            Log.e(TAG, "save gid fail :" + e.getMessage());
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

    private void getGuid() {
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

