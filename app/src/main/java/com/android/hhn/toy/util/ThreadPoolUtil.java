package com.android.hhn.toy.util;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/5/22,11:52 AM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class ThreadPoolUtil {

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue();
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AndroidToy #" + this.mCount.getAndIncrement());
        }
    };

    private static ThreadPoolExecutor executor;

    private static final String TAG = "ThreadPoolUtil";

    static {
        executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
        executor.allowCoreThreadTimeOut(true);
        Log.d(TAG, "static initializer: ");
    }

    private ThreadPoolUtil() {
        Log.d(TAG, "ThreadPoolUtils: init");
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }
}
