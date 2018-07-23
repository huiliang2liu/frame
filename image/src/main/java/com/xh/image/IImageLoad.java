package com.xh.image;

import android.view.View;
import android.widget.ImageView;

import com.xh.image.transform.ITransform;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * 2018/7/2 10:17
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public interface IImageLoad {
    public  final  static int GLIDE=0;
    public  final  static int IMAGE_LOAD=1;
    public  final  static int PICASSO=2;
    /**
     * 2018/7/2 10:24
     * annotation：从资源中加载背景图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */

    void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, int res);

    /**
     * 2018/7/2 10:27
     * annotation：从文件中加载背景图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, File file);

    /**
     * 2018/7/2 10:27
     * annotation：从uri中加载背景图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URI uri);

    /**
     * 2018/7/2 10:28
     * annotation：从url中加载背景图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, URL url);

    /**
     * 2018/7/2 10:28
     * annotation：从path中加载背景图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, View view, ITransform transform, String path);

    /**
     * 2018/7/2 10:24
     * annotation：从资源中加载图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */

    void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, int res);

    /**
     * 2018/7/2 10:27
     * annotation：从文件中加载图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, File file);

    /**
     * 2018/7/2 10:27
     * annotation：从uri中加载图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URI uri);

    /**
     * 2018/7/2 10:28
     * annotation：从url中加载图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, URL url);

    /**
     * 2018/7/2 10:28
     * annotation：从path中加载图片
     * author：liuhuiliang
     * email ：825378291@qq.com
     */
    void load(int errId, int defaultId, int width, int heigth, ImageView view, ITransform transform, String path);
    /**
     * 2018/7/6 12:04
     * annotation：设置是否加载
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    void setLoad(boolean load);

}
