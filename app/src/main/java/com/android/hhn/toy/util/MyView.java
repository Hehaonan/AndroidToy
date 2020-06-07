package com.android.hhn.toy.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Author: haonan.he ;<p/>
 * Date: 2020/6/6,11:55 AM ;<p/>
 * Description: ;<p/>
 * Other: ;
 */
public class MyView extends View {


    public MyView(Context context) {
        super(context);
    }

    Camera camera = new Camera();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.clipRect(1, 2, 3, 4);
        canvas.translate(1, 1);
        canvas.rotate(1);
        canvas.scale(1, 1);
        canvas.skew(1, 1);


        Matrix matrix = getMatrix();
        matrix.postTranslate(1, 1);
        matrix.setRotate(10);

        camera.rotateX(10);
        camera.applyToCanvas(canvas);

        canvas.restore();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // View真实的期望尺寸 super中已经保存
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取原始尺寸
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        // 重新计算尺寸
        if (width > height) {
            width = height;
        } else {
            height = width;
        }
        resolveSize(width, widthMeasureSpec);
        resolveSize(height, heightMeasureSpec);
        // 触发保存
        setMeasuredDimension(width, height);
        Handler handler = new Handler();
        handler.sendMessage(Message.obtain());//  Return a new Message instance from the global pool
        Looper.loop();

        ReferenceQueue<Activity> queue = new ReferenceQueue<>();
        WeakReference<Activity> wrA = new WeakReference<Activity>(new Activity(), queue);

        //        Looper.getMainLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
        //            @Override
        //            public boolean queueIdle() {
        //                removeWeaklyReachableReferences();//延迟五秒触发
        //                // 返回true可使您的空闲处理程序保持活动状态，返回false使其被删除。
        //                return false;
        //            }
        //        });
        //        // retainedKeys集合了所有destoryed了的但没有被回收的Activity的key，
        //        // 这个集合可以用来判断一个Activity有没有被回收，
        //        // 但是判断之前需要用removeWeaklyReachableReferences()这个方法更新一下
        //        if (gone(reference)) {// 然后在gone()函数里面判断key是否被移除.
        //            return DONE;
        //        }


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }
}
