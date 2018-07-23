package com.xh.image.imageload;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.xh.image.transform.ITransform;


/**
 * 2018/7/6 11:54
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ImageloadTarget implements BitmapDisplayer {
    public ImageloadTarget(ITransform transform){

    }
    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        imageAware.setImageBitmap(bitmap);
    }
}
