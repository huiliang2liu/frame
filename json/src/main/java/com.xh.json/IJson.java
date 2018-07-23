package com.xh.json;

import java.io.InputStream;

/**
 * 2018/7/10 11:26
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public interface IJson {
    /**
     * 2018/7/10 11:28
     * annotation：将字节流转化为javabean
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    <T> T forJson(InputStream is, Class<T> t);
    /**
     * 2018/7/10 11:29
     * annotation：将字节数组转化为javabean
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    <T> T forJson(byte[] buff, Class<T> t);
    /**
     * 2018/7/10 11:30
     * annotation：将字符串转化为javabean
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    <T> T forJson(String string, Class<T> t);
    /**
     * 2018/7/10 11:31
     * annotation：将javabean转化为字节流
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    <T> InputStream toJsonStream(T t);
    /**
     * 2018/7/10 11:32
     * annotation：将javabean转化为字节数组
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    <T> byte[] toJsonByte(T t);
    /**
     * 2018/7/10 11:33
     * annotation：将javabean转化为字符串
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    <T> String  toJson(T t);
}
