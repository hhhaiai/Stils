package cn.safei.utils;

import android.util.Log;

/**
 *
 * <p>
 * Copyright © 2015. All rights reserved.
 * </p>
 * <p>
 * Description: Log统一管理类
 * </p>
 *
 * <p>
 * Version: 1.0
 * </p>
 * <p>
 * Created: 2015年6月3日 下午3:00:31
 * </p>
 * <p>
 * Author: sanbo
 * </p>
 *
 * <p>
 * Revision: initial draft
 * </p>
 */
public class L {

    private L() {
        /* cannot be instantiated */

        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "sanbo";

    // 下面四个是默认tag的函数
    // 哈哈的哈师大会上
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
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
}