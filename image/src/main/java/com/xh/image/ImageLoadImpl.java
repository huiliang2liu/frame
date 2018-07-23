package com.xh.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.xh.image.glide.GlideImageLoadImpl;
import com.xh.image.imageload.ImageloadImageLoadImpl;
import com.xh.image.load.ImageImageLoadImpl;
import com.xh.image.picasso.PicassoImageLoadImpl;
import com.xh.image.transform.ITransform;
import com.xh.image.transform.NoTransform;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2018/7/3 18:21
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ImageLoadImpl implements IImageLoad {
    IImageLoad mImageLoad;
    private int mErrId;
    private int mDefaultId;
    private ITransform mTransform;
    private ExecutorService executorService;

    public ImageLoadImpl(Context context, Bitmap.Config bitmapConfig, boolean cacheDis, long cacheDisSize, String file, int type, int errId, int defaultId, ITransform transform) {
        mErrId = errId;
        mDefaultId = defaultId;
        executorService = Executors.newFixedThreadPool(5);
        if (transform == null)
            mTransform = new NoTransform();
        else
            mTransform = transform;
        switch (type) {
            case GLIDE:
                mImageLoad = new GlideImageLoadImpl(context, cacheDisSize, file, bitmapConfig, executorService);
                break;
            case IMAGE_LOAD:
                mImageLoad = new ImageloadImageLoadImpl(context, bitmapConfig, cacheDis, cacheDisSize, file, executorService);
                break;
            case PICASSO:
                mImageLoad = new PicassoImageLoadImpl(context, bitmapConfig, executorService);
                break;
            default:
                mImageLoad = new ImageImageLoadImpl(executorService, context);
                break;
        }

    }

    public ImageLoadImpl(Context context, Bitmap.Config bitmapConfig, boolean cacheDis, long cacheDisSize, String file, int errId, int defaultId, ITransform transform) {
        int type = 3;
        try {
            Class.forName("com.bumptech.glide.Glide");
            type = GLIDE;
        } catch (Exception e) {
            try {

                Class.forName("com.nostra13.universalimageloader.core.ImageLoader");
                type = IMAGE_LOAD;
            } catch (Exception e1) {
                try {
                    Class.forName("com.squareup.picasso.Picasso");
                    type = PICASSO;
                } catch (Exception e2) {

                }
            }
        }
        mErrId = errId;
        mDefaultId = defaultId;
        executorService = Executors.newFixedThreadPool(5);
        if (transform == null)
            mTransform = new NoTransform();
        else
            mTransform = transform;
        switch (type) {
            case GLIDE:
                mImageLoad = new GlideImageLoadImpl(context, cacheDisSize, file, bitmapConfig, executorService);
                break;
            case IMAGE_LOAD:
                mImageLoad = new ImageloadImageLoadImpl(context, bitmapConfig, cacheDis, cacheDisSize, file, executorService);
                break;
            case PICASSO:
                mImageLoad = new PicassoImageLoadImpl(context, bitmapConfig, executorService);
                break;
            default:
                mImageLoad = new ImageImageLoadImpl(executorService, context);
                break;
        }

    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, int res) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, res);
    }

    public void load(int width, int heigth, View view, ITransform transform, int res) {
        load(mErrId, mDefaultId, width, heigth, view, transform, res);
    }

    public void load(View view, ITransform transform, int res) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, res);
    }

    public void load(View view, int res) {
        load(view, mTransform, res);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, File file) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, file);
    }

    public void load(int width, int heigth, View view, ITransform transform, File file) {
        load(mErrId, mDefaultId, width, heigth, view, transform, file);
    }

    public void load(View view, ITransform transform, File file) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, file);
    }


    public void load(View view, File file) {
        load(view, mTransform, file);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URI uri) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, uri);
    }

    public void load(int width, int heigth, View view, ITransform transform, URI uri) {
        load(mErrId, mDefaultId, width, heigth, view, transform, uri);
    }

    public void load(View view, ITransform transform, URI uri) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, uri);
    }

    public void load(View view, URI uri) {
        load(view, mTransform, uri);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URL url) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, url);
    }

    public void load(int width, int heigth, View view, ITransform transform, URL url) {
        load(mErrId, mDefaultId, width, heigth, view, transform, url);
    }

    public void load(View view, ITransform transform, URL url) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, url);
    }

    public void load(View view, URL url) {
        load(view, mTransform, url);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, String path) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, path);
    }

    public void load(int width, int heigth, View view, ITransform transform, String path) {
        load(mErrId, mDefaultId, width, heigth, view, transform, path);
    }

    public void load(View view, ITransform transform, String path) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, path);
    }

    public void load(View view, String path) {
        load(view, mTransform, path);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, int res) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, res);
    }

    public void load(int width, int heigth, ImageView view, ITransform transform, int res) {
        load(mErrId, mDefaultId, width, heigth, view, transform, res);
    }

    public void load(ImageView view, ITransform transform, int res) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, res);
    }

    public void load(ImageView view, int res) {
        load(view, mTransform, res);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, File file) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, file);
    }

    public void load(int width, int heigth, ImageView view, ITransform transform, File file) {
        load(mErrId, mDefaultId, width, heigth, view, transform, file);
    }

    public void load(ImageView view, ITransform transform, File file) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, file);
    }

    public void load(ImageView view, File file) {
        load(view, mTransform, file);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URI uri) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, uri);
    }

    public void load(int width, int heigth, ImageView view, ITransform transform, URI uri) {
        load(mErrId, mDefaultId, width, heigth, view, transform, uri);
    }

    public void load(ImageView view, ITransform transform, URI uri) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, uri);
    }

    public void load(ImageView view, URI uri) {
        load(view, mTransform, uri);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URL url) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, url);
    }

    public void load(int width, int heigth, ImageView view, ITransform transform, URL url) {
        load(mErrId, mDefaultId, width, heigth, view, transform, url);
    }

    public void load(ImageView view, ITransform transform, URL url) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, url);
    }

    public void load(ImageView view, URL url) {
        load(view, mTransform, url);
    }

    @Override
    public void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, String path) {
        mImageLoad.load(errId, defaultId, width, heigth, view, transform, path);
    }

    public void load(int width, int heigth, ImageView view, ITransform transform, String path) {
        load(mErrId, mDefaultId, width, heigth, view, transform, path);
    }

    public void load(ImageView view, ITransform transform, String path) {
        load(Util.getViewWidth(view, 0), Util.getViewHeight(view, 0), view, transform, path);
    }

    public void load(ImageView view, String path) {
        load(view, mTransform, path);
    }

    @Override
    public void setLoad(boolean load) {
        if (mImageLoad != null)
            mImageLoad.setLoad(load);
    }
}
