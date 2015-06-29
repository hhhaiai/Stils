package cn.safei.utils;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @Copyright © 2015 sanbo Inc. All rights reserved.
 * @Description: 修改界面元素
 * @Version: 1.0
 * @Create: 2015年6月29日 下午8:47:17
 * @Author: sanbo
 */
public class Font {

    /**
     * 修改整个界面所有控件的字体
     * 
     * @param root
     * @param path
     * @param act
     */
    public static void changeFonts(ViewGroup root, String path, Activity act) {
        // path是字体路径
        Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, path, act);
            }
        }
    }

    /**
     * 修改整个界面所有控件的字体大小
     * 
     * @param root
     * @param size
     * @param act
     */
    public static void changeTextSize(ViewGroup root, int size, Activity act) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextSize(size);
            } else if (v instanceof Button) {
                ((Button) v).setTextSize(size);
            } else if (v instanceof EditText) {
                ((EditText) v).setTextSize(size);
            } else if (v instanceof ViewGroup) {
                changeTextSize((ViewGroup) v, size, act);
            }
        }
    }

    /**
     * 不改变控件位置，修改控件大小
     * 
     * @param v
     * @param W
     * @param H
     */
    public static void changeWH(View v, int W, int H) {
        LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.width = W;
        params.height = H;
        v.setLayoutParams(params);
    }

    /**
     * 修改控件的高
     * 
     * @param v
     * @param H
     */
    public static void changeH(View v, int H) {
        LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.height = H;
        v.setLayoutParams(params);
    }

}
