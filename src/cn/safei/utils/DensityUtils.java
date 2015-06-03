package cn.safei.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 *
 * <p>
 * Copyright © 2015. All rights reserved.
 * </p>
 * <p>
 * Description: 常用单位转换的辅助类
 * </p>
 *
 * <p>
 * Version: 1.0
 * </p>
 * <p>
 * Created: 2015年6月3日 下午3:04:31
 * </p>
 * <p>
 * Author: sanbo
 * </p>
 *
 * <p>
 * Revision: initial draft
 * </p>
 */
public class DensityUtils {
    private DensityUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @param val
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param val
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param fontScale
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

}
