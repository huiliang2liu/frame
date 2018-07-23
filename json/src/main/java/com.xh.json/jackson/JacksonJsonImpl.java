package com.xh.json.jackson;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xh.json.IJson;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * 2018/7/10 18:10
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class JacksonJsonImpl implements IJson {
    private ObjectMapper objectMapper;
    public JacksonJsonImpl(){
        Log.e("Jackson","Jackson");
        objectMapper=new ObjectMapper();
    }
    @Override
    public <T> T forJson(InputStream is, Class<T> t) {
       try{
           return objectMapper.readValue(is,t);
       }catch (Exception e){
           return null;
       }
    }

    @Override
    public <T> T forJson(byte[] buff, Class<T> t) {
        try{
            return objectMapper.readValue(buff,t);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public <T> T forJson(String string, Class<T> t) {
        try{
            return objectMapper.readValue(string,t);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public <T> InputStream toJsonStream(T t) {
        byte[] buff=toJsonByte(t);
        if(buff==null||buff.length<=0)
            return null;
        return new ByteArrayInputStream(buff);
    }

    @Override
    public <T> byte[] toJsonByte(T t) {
        try{
           return objectMapper.writeValueAsBytes(t);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public <T> String toJson(T t) {
       try{
           return objectMapper.writeValueAsString(t);
       }catch (Exception e){
           return null;
       }
    }
}
