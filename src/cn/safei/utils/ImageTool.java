package cn.safei.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * @Copyright © 2015 sanbo Inc. All rights reserved.
 * @Description: 图片处理 初稿
 * @Create: 2015年6月29日 下午6:31:55
 * @Author: sanbo
 */
public class ImageTool {
    private static final String TAG = ImageTool.class.getName();

    /**
     * 从view 获取图片
     *
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param path
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(String path, int screenWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, screenWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 计算缩放比例
     *
     * @param options
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int screenWidth) {
        int height = options.outHeight;
        int width = options.outWidth;
        float maxLength = screenWidth * 0.36f;
        return (int) (height > width ? height / maxLength : width / maxLength);
    }

    /**
     * 回收Bitmap资源
     *
     * @param bitmap
     */
    private static void safeRecycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        int height = bitmap.getHeight();
        int size = bitmap.getRowBytes() * bitmap.getHeight() / 1024 / 1024;
        // size >1M
        if (size > 15) {
            Matrix matrix = new Matrix();
            matrix.postScale(0.5f, 0.5f);
            Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), height, matrix, true);
            safeRecycle(bitmap);
            return bmp;
        } else {
            return bitmap;
        }
    }

    /**
     * 缩放、裁剪图片
     * 
     * @param bm
     * @param newWidth
     *            目标图片宽
     * @param newHeight
     *            目标图片高
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 通过Uri获取指定Bitmap
     *
     * @param context
     * @param originalUri
     * @return
     */
    private synchronized static Bitmap getBitmapWithUri(Context context, Uri originalUri) throws IOException {
        ContentResolver resolver = context.getContentResolver();
        InputStream input = resolver.openInputStream(originalUri);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        if ((bitmapOptions.outWidth == -1) || (bitmapOptions.outHeight == -1))
            return null;
        int originalSize = (bitmapOptions.outHeight > bitmapOptions.outWidth) ? bitmapOptions.outHeight
                : bitmapOptions.outWidth;
        int screenHeight = getScreenLength(context);
        int ratio = (originalSize > screenHeight) ? (originalSize / screenHeight) : 1;
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = ratio;
        input = resolver.openInputStream(originalUri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    /**
     * 获取屏幕的长度
     *
     * @param context
     * @return
     */
    private static int getScreenLength(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels > metrics.widthPixels ? metrics.heightPixels : metrics.widthPixels;
    }

    /**
     * inJustDecodeBounds=false 这样是因为设置为true的时候可以decode，但是设置成false就无法decode
     * 所以还是按照true的方式来验证吧
     *
     * @param context
     * @param originalUri
     * @return
     */
    public static boolean isImage(Context context, Uri originalUri) {
        ContentResolver resolver = context.getContentResolver();
        Bitmap bitmap = null;
        try {
            InputStream input = resolver.openInputStream(originalUri);
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inJustDecodeBounds = false;
            bitmapOptions.inSampleSize = 4;
            bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
            input.close();
            if ((bitmapOptions.outWidth == -1) || (bitmapOptions.outHeight == -1)) {
                return false;
            }
        } catch (Exception ignore) {
            return false;
        } finally {
            safeRecycle(bitmap);
        }
        return true;
    }

    /**
     * 通过文件路径获取到bitmap
     * 
     * @param path
     * @param w
     * @param h
     * @return
     */
    public static Bitmap getBitmapFromPath(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    // 把bitmap转换成base64
    public static String getBase64FromBitmap(Bitmap bitmap, int bitmapQuality) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // 把base64转换成bitmap
    public static Bitmap getBitmapFromBase64(String string) {
        byte[] bitmapArray = null;
        try {
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

}
