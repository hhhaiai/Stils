package cn.safei.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.util.Log;

/**
 *
 * @Copyright © 2015 Sanbo Inc.. All rights reserved.
 * @Title: L.java
 * @Description: Log统一管理类
 * @Version: 1.0
 * @Create: 2015年6月18日 下午4:14:01
 * @Author: sanbo
 *
 */
public class L {

    private L() {
        /* cannot be instantiated */

        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static String TAG = "sanbo";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            v(TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        if (isDebug)
            e(TAG, msg, e);
    }

    public static void e(Throwable e) {
        if (isDebug)
            e(TAG, null, e);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    private static void e(String tag, String msg, Throwable e) {
        if (isDebug)
            Log.e(tag, (msg == null ? "" : (msg + "\r\n")) + getStackTrace(e));
    }

    /**********************************************************************/

    public static void setTAG(String tag) {
        TAG = tag;
    }

    /**
     * 将error转换成字符串
     */
    private static String getStackTrace(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

}