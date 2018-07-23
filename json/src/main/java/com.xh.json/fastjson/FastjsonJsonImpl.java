package com.xh.json.fastjson;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.xh.json.IJson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * 2018/7/11 9:59
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class FastjsonJsonImpl implements IJson {
    public FastjsonJsonImpl(){
        Log.e("Fast","Fast");
    }
    @Override
    public <T> T forJson(InputStream is, Class<T> t) {
       try{
           return JSON.parseObject(is,t);
       }catch (Exception e){
           return null;
       }
    }

    @Override
    public <T> T forJson(byte[] buff, Class<T> t) {
        if(buff==null||t==null)
            return null;
        return JSON.parseObject(buff,t);
    }

    @Override
    public <T> T forJson(String string, Class<T> t) {
        if(string==null||t==null)
            return null;
        return JSON.parseObject(string,t);
    }

    @Override
    public <T> InputStream toJsonStream(T t) {
        byte[] buff=toJsonByte(t);
        if(buff==null)
        return null;
        return new ByteArrayInputStream(buff);
    }

    @Override
    public <T> byte[] toJsonByte(T t) {
        if(t==null)
            return null;
        return JSON.toJSONBytes(t);
    }

    @Override
    public <T> String toJson(T t) {
        if(t==null)
            return null;
        return JSON.toJSONString(t);
    }
}
