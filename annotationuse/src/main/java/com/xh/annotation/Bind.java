package com.xh.annotation;

import android.view.View;

/**
 * 2018/7/16 14:39
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface Bind {
    void unBind();
    void bind(View vew);
    int layout();
    String packageName();
    int theme();
    String color();
}
