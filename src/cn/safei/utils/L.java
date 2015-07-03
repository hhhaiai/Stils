package cn.safei.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Formatter;
import java.util.Locale;

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

    /**********************************************************************/
    /**
     *
     * @param l
     *            The {@linkplain java.util.Locale locale} to apply during
     *            formatting. If {@code l} is {@code null} then no localization
     *            is applied.
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     * @param args
     *            Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification
     *
     */
    public static void i(Locale l, String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter(l).format(format, args).toString();
                i(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**
     *
     * @param l
     *            The {@linkplain java.util.Locale locale} to apply during
     *            formatting. If {@code l} is {@code null} then no localization
     *            is applied.
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     * @param args
     *            Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification
     *
     */
    public static void d(Locale l, String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter(l).format(format, args).toString();
                d(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**
     *
     * @param l
     *            The {@linkplain java.util.Locale locale} to apply during
     *            formatting. If {@code l} is {@code null} then no localization
     *            is applied.
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     * @param args
     *            Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification
     *
     */
    public static void e(Locale l, String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter(l).format(format, args).toString();
                e(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**
     *
     * @param l
     *            The {@linkplain java.util.Locale locale} to apply during
     *            formatting. If {@code l} is {@code null} then no localization
     *            is applied.
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     * @param args
     *            Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification
     *
     */
    public static void v(Locale l, String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter(l).format(format, args).toString();
                v(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**********************************************************************/
    /**
     *
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     *
     * @param args
     *            * Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification.
     */
    public static void i(String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter().format(format, args).toString();
                i(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**
     *
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     *
     * @param args
     *            * Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification.
     */
    public static void d(String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter().format(format, args).toString();
                d(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**
     *
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     *
     * @param args
     *            * Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification.
     */
    public static void e(String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter().format(format, args).toString();
                e(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**
     *
     * @param format
     *            A <a href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >format string</a>
     *
     * @param args
     *            * Arguments referenced by the format specifiers in the format
     *            string. If there are more arguments than format specifiers,
     *            the extra arguments are ignored. The number of arguments is
     *            variable and may be zero. The maximum number of arguments is
     *            limited by the maximum dimension of a Java array as defined by
     *            <cite>The Java&trade; Virtual Machine Specification</cite>.
     *            The behaviour on a {@code null} argument depends on the <a
     *            href=
     *            "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax"
     *            >conversion</a>.
     * @throws java.util.IllegalFormatException
     *             If a format string contains an illegal syntax, a format
     *             specifier that is incompatible with the given arguments,
     *             insufficient arguments given the format string, or other
     *             illegal conditions. For specification of all possible
     *             formatting errors, see the <a href=
     *             "http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#detail"
     *             >Details</a> section of the formatter class specification.
     */
    public static void v(String format, Object... args) {
        if (isDebug) {
            try {
                String msg = new Formatter().format(format, args).toString();
                v(TAG, msg);
            } catch (Throwable e) {
                e(e);
            }
        }
    }

    /**********************************************************************/
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

    /**********************************************************************/
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

    /**********************************************************************/
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