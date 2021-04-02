package com.android.hhn.toy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

/**
 * Author: haonan.he ;<p/>
 * Date: 4/2/21,4:07 PM ;<p/>
 * Description: 自定义View 固定View的宽高;<p/>
 * Other: ;
 */
@SuppressLint("AppCompatCustomView")
public class InnerImageView extends ImageView {

    public InnerImageView(Context context) {
        super(context);
    }

    // widthMeasureSpec heightMeasureSpec 代表父View对子View的宽高限制
    // : horizontal space requirements as imposed by the parent.
    // The requirements are encoded with View.MeasureSpec.
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 先执行原有的父view测量逻辑
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 通过widthMeasureSpec 得到真实的尺寸
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 获取限制类型
        // int mode = MeasureSpec.getMode(widthMeasureSpec);

        if (height > width) {
            // 从新计算 heightMeasureSpec
            height = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        }

        // 如果子View需要适应父View的限制 需要使用resolveSize
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, widthMeasureSpec);// 返回符合父view修正后的尺寸

        // 保存自身计算结果
        setMeasuredDimension(width, height);
    }

    /**
     * resolveSize 的简单实现逻辑 调用view的 resolveSizeAndState()
     *
     * @param size        子view想要实现的大小
     * @param measureSpec 父view的限制
     *
     * @return 符合父view要求的size
     */
    public static int resolveSize(int size, int measureSpec) {
        final int specMode = MeasureSpec.getMode(measureSpec);// 父view的限制
        final int specSize = MeasureSpec.getSize(measureSpec);
        final int result;
        switch (specMode) {
            case MeasureSpec.AT_MOST:// 限制上限模式
                if (specSize < size) { // size操作上限 返回父view的限制
                    result = specSize;
                } else {
                    result = size;// 反之 返回size
                }
                break;
            case MeasureSpec.EXACTLY: // 精确模式
                result = specSize;// 子view不需要计算size 直接返回父view要求的 specSize
                break;
            case MeasureSpec.UNSPECIFIED: // 不限制模式 返回要求的size
            default:
                result = size;
        }
        return result;
    }

}
