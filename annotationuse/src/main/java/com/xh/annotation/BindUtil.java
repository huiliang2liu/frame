package com.xh.annotation;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

import java.lang.reflect.Constructor;

/**
 * 2018/7/17 17:29
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class BindUtil {

    public static Bind createBind(Object target, Context context) {
        if (target == null || context == null)
            return null;
        try {
            Class bindClass = Class.forName(target.getClass().getName() + "$XhAnnotatoin");
            Constructor<? extends Bind> constructor = bindClass.getConstructor(new Class[]{Object.class, Context.class});
            return constructor.newInstance(new Object[]{target, context});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bind createBind(Object target, View view) {
        if (view == null)
            return null;
        Bind bind = createBind(target, view.getContext());
        bind.bind(view);
        return bind;
    }

    public static Bind createBind(Object target, Activity parant) {
        if (parant == null)
            return null;
        Bind bind = createBind(target, (Context) parant);
        if (bind != null) {
            int layout = bind.layout();
            if (layout > 0)
                parant.setContentView(layout);
            bind.bind(parant.getWindow().getDecorView());
        }

        return bind;
    }

    public static Bind createBind(Object target, Dialog parant) {
        if (parant == null)
            return null;
        Bind bind = createBind(target, parant.getContext());
        if (bind != null) {
            int layout = bind.layout();
            if (layout > 0)
                parant.setContentView(layout);
            bind.bind(parant.getWindow().getDecorView());
        }
        return bind;
    }

    public static Bind createBind(Object target, Fragment parant) {
        if(parant==null)
            return null;
        Bind bind = createBind(target, parant.getView());
        return bind;
    }

    public static Bind createBind(Object target, android.support.v4.app.Fragment parant) {
        if(parant==null)
            return  null;
        Bind bind = createBind(target, parant.getView());
        return bind;
    }


    public static Bind createBind(Activity target) {
        if (target == null)
            return null;
        return createBind(target, target);
    }

    public static Bind createBind(Fragment target) {
        if (target == null)
            return null;
        return createBind(target, target);
    }

    public static Bind createBind(android.support.v4.app.Fragment target) {
        if (target == null)
            return null;
        return createBind(target, target);
    }

    public static Bind createBind(Dialog target) {
        if (target == null)
            return null;
        return createBind(target, target);
    }
}
