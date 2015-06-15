package cn.safei.utils;

import android.content.Context;
import com.umeng.fb.common.Constants;
import java.io.File;

public class FileUtil {
    private final static String TAG = FileUtil.class.getName();

    /**
     * 创建文件夹
     *
     * @param dirPath
     */
    public synchronized static boolean createDir(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public synchronized static void delete(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(path);
                if (!file.exists()) {
                    return;
                }
                // 若是文件则删除
                if (file.isFile()) {
                    file.delete();
                    return;
                }
                // 若是目录递归删除
                if (file.isDirectory()) {
                    File[] childFiles = file.listFiles();
                    for (File f : childFiles) {
                        f.delete();
                    }
                }
            }
        }).start();
    }

    /**
     * 清空缓存
     */
    public static void clearCache(Context context) {
        delete(getAudioCachePath(context));
    }

    /**
     * 根据文件名删除未使用的文件
     */
    public static void deleteFileWithName(Context context, String name) {
        delete(getAudioDataPath(context) + name + Constants.FILE_END_OPUS);
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isExists(String path) {
        return new File(path).exists();
    }

    public static String getAudioDataPath(Context context) {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FILE_DATA_PATH;
        String path = context.getFilesDir().getAbsolutePath() + Constants.FILE_DATA_PATH;
        return path;
    }

    public static String getAudioCachePath(Context context) {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FILE_CACHE_PATH;
        String path = context.getFilesDir().getAbsolutePath() + Constants.FILE_CACHE_PATH;
        Log.d(TAG, "getAudioCachePath=" + path);
        return path;
    }

    public static String getImagePathWithName(Context context,String name){
        return getImagePath(context)  + name + Constants.FILE_END_PNG ;
    }

    public static String getImagePath(Context context){
      //  return Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FILE_IMAGE_PATH ;
        String path = context.getFilesDir().getAbsolutePath() + Constants.FILE_IMAGE_PATH ;
        createDir(path);
        return path;
    }
}
