package cn.safei.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * @Copyright © 2015 Sanbo Inc. All rights reserved.
 * @Description: 剪切板相关操作
 * @Create: 2015年6月29日 下午6:44:29
 * @Author: sanbo
 */
public class ClipboardUtil {

    /**
     * 获取剪切板中的内容
     *
     * @param context
     *            剪切板中的内容
     * @return
     */
    public static String getContent(Context context) {
        if (context == null) {
            return "";
        }
        String text = "";

        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager cbm = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if (cbm != null && !TextUtils.isEmpty(cbm.getText())) {
                text = cbm.getText().toString().trim();
            }
        } else {
            android.text.ClipboardManager cbm = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);

            if (cbm != null && !TextUtils.isEmpty(cbm.getText())) {
                text = cbm.getText().toString().trim();
            }
        }
        return text;
    }

}
