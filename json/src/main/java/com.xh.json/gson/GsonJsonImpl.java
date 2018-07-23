package com.xh.json.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.xh.json.IJson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * 2018/7/10 14:45
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class GsonJsonImpl implements IJson {
    private Gson gson;

    public GsonJsonImpl() {
        Log.e("Gson","Gson");
        gson = new Gson();
    }

    @Override
    public <T> T forJson(InputStream is, Class<T> t) {
        if (is == null)
            return null;
        return gson.fromJson(new InputStreamReader(is), t);
    }

    @Override
    public <T> T forJson(byte[] buff, Class<T> t) {
        if (buff == null || buff.length <= 0)
            return null;
        return forJson(new ByteArrayInputStream(buff), t);
    }

    @Override
    public <T> T forJson(String string, Class<T> t) {
        if (string == null || string.isEmpty())
            return null;
        return gson.fromJson(string, t);
    }

    @Override
    public <T> InputStream toJsonStream(T t) {
        byte[] buff = toJsonByte(t);
        if (buff == null)
            return null;
        return new ByteArrayInputStream(buff);
    }

    @Override
    public <T> byte[] toJsonByte(T t) {
        String json = toJson(t);
        if (json == null)
            return null;
        return json.getBytes();
    }

    @Override
    public <T> String toJson(T t) {
        return gson.toJson(t);
    }
}
