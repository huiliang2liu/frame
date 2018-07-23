package com.xh.json;

import com.xh.json.fastjson.FastjsonJsonImpl;
import com.xh.json.gson.GsonJsonImpl;
import com.xh.json.jackson.JacksonJsonImpl;

import java.io.InputStream;

/**
 * 2018/7/11 10:16
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class JsonImpl implements IJson {
    private IJson json;
    public  JsonImpl(){
     try{
         Class.forName("com.alibaba.fastjson.JSON");
         json=new FastjsonJsonImpl();
     } catch (Exception e){
    try{
        Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
        json=new JacksonJsonImpl();
    }catch (Exception e1){
        try {
Class.forName("com.google.gson.Gson");
json=new GsonJsonImpl();
        }catch (Exception e2){
throw new RuntimeException("not found json paras");
        }
    }
     }
    }
    @Override
    public <T> T forJson(InputStream is, Class<T> t) {
        return json.forJson(is,t);
    }

    @Override
    public <T> T forJson(byte[] buff, Class<T> t) {
        return json.forJson(buff,t);
    }

    @Override
    public <T> T forJson(String string, Class<T> t) {
        return json.forJson(string,t);
    }

    @Override
    public <T> InputStream toJsonStream(T t) {
        return json.toJsonStream(t);
    }

    @Override
    public <T> byte[] toJsonByte(T t) {
        return json.toJsonByte(t);
    }

    @Override
    public <T> String toJson(T t) {
        return json.toJson(t);
    }
}
