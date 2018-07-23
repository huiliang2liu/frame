package com.xh.annotation;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 2018/7/16 15:22
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class Util {

    public static View findView(String name, String packageName, View parant) {
        if (name == null || name.isEmpty() || parant == null)
            return null;
        if (packageName == null || packageName.isEmpty()||packageName.equals("null"))
            packageName = parant.getContext().getPackageName();
        try {
            Resources resources = parant.getResources();
            int id = name2id(resources, name, "id", packageName);
            return parant.findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int layout(String name, String packageName, Context context) {
        if (name == null || name.isEmpty() || context == null)
            return -1;
        if (packageName == null || packageName.isEmpty()||packageName.equals("null"))
            packageName = context.getPackageName();
        try {
            Resources resources = context.getResources();
           return name2id(resources, name, "layout", packageName);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static View layoutView(int id, Context context) {
        if (id<=0 || context == null)
            return null;
        return LayoutInflater.from(context).inflate(id,null);
    }

    public static View findView(String name, View parant) {
        return findView(name, null, parant);
    }

    public static String string(String name,
                                String packageName, View parant) {
        if (name == null || name.isEmpty() || parant == null)
            return null;
        if (packageName == null || packageName.isEmpty()||packageName.equals("null"))
            packageName = parant.getContext().getPackageName();
        try {
            Resources resources = parant.getResources();
            int id = name2id(resources, name, "string", packageName);
            return resources.getString(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String string(String name, View parant) {
        return string(name, null, parant);
    }

    public static String[] strings(String name,
                                String packageName, View parant) {
        if (name == null || name.isEmpty() || parant == null)
            return null;
        if (packageName == null || packageName.isEmpty()||packageName.equals("null"))
            packageName = parant.getContext().getPackageName();
        try {
            Resources resources = parant.getResources();
            int id = name2id(resources, name, "string", packageName);
            return resources.getStringArray(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] strings(String name, View parant) {
        return strings(name, null, parant);
    }

    public static int name2id(Resources resources, String name, String type,
                              String packageName) {
        if (resources == null || name == null || name.isEmpty()
                || type == null || type.isEmpty())
            return -1;
        try {
            return resources.getIdentifier(name, type, packageName);
        } catch (Exception e) {
            // TODO: handle exception
            return -1;
        }
    }

}
