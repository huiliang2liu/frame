package com.xh.annotation;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Constructor;

/**
 * 2018/7/17 17:29
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class BindUtil {
    public static final int F = Integer.valueOf("f", 16);
    public static final int FF = Integer.valueOf("ff", 16);
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
            if (layout > 0){
                int theme=bind.theme();
                if(theme>0)
                    parant.setTheme(theme);
                parant.setContentView(layout);
            }
            String color=bind.color();
            if(color!=null){
                setColor(parant.getWindow(),color,parant,bind.packageName());
            }
            bind.bind(parant.getWindow().getDecorView());
        }

        return bind;
    }
    private  static  void setColor(Window window,String color,Context context,String packageName){
        int colorId=Util.name2id(context.getResources(),color,"color",packageName);
        int acolor=-1;
        if(colorId>0){
            acolor=context.getResources().getColor(colorId);
        }else{
            acolor=color(color);
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(acolor);
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Integer color(String string) {
        boolean is16 = false;
        if (string.startsWith("#")) {
            is16 = true;
            string = string.substring(1);
        } else if (string.startsWith("0x")) {
            is16 = true;
            string = string.substring(2);
        }
        int len = string.length();
        if (len != 3 && len != 4 && len != 6 && len != 8)
            return null;
        switch (len) {
            case 3: {
                int r = string2int(string.substring(0, 1), is16);
                r = r * FF / F;
                int g = string2int(string.substring(1, 2), is16);
                g = g * FF / F;
                int b = string2int(string.substring(2, 3), is16);
                b = b * FF / F;
                return Color.rgb(r, g, b);
            }
            case 4: {
                int a = string2int(string.substring(0, 1), is16);
                a = a * FF / F;
                int r = string2int(string.substring(1, 2), is16);
                r = r * FF / F;
                int g = string2int(string.substring(2, 3), is16);
                g = g * FF / F;
                int b = string2int(string.substring(3, 4), is16);
                b = b * FF / F;
                return Color.argb(a, r, g, b);
            }
            case 6: {
                int r = string2int(string.substring(0, 2), is16);
                int g = string2int(string.substring(2, 4), is16);
                int b = string2int(string.substring(4, 6), is16);
                return Color.rgb(r, g, b);
            }
            default: {
                int a = string2int(string.substring(0, 2), is16);
                int r = string2int(string.substring(2, 4), is16);
                int g = string2int(string.substring(4, 6), is16);
                int b = string2int(string.substring(6, 8), is16);
                return Color.argb(a, r, g, b);
            }
        }
    }
    public static int string2int(String string, boolean is16) {
        return Integer.valueOf(string, is16 ? 16 : 10);
    }
    public static Bind createBind(Object target, Dialog parant) {
        if (parant == null)
            return null;
        Bind bind = createBind(target, parant.getContext());
        if (bind != null) {
            int layout = bind.layout();
            if (layout > 0){
                int theme=bind.theme();
                if(theme>0)
                parant.getContext().setTheme(theme);
                parant.setContentView(layout);
            }
            String color=bind.color();
            if(color!=null){
                setColor(parant.getWindow(),color,parant.getContext(),bind.packageName());
            }
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
