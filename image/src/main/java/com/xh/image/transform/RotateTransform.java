package com.xh.image.transform;

import android.graphics.Bitmap;

import com.xh.image.Util;

/**
 * 2018/7/4 11:15
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class RotateTransform implements ITransform {
    private float mDegree;

    public RotateTransform(float degree) {
        mDegree = degree<=0?20:degree;
    }
    public RotateTransform() {
        this(20);
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        return Util.rotate_bitmap(bitmap, mDegree);
    }
}
