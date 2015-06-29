package cn.safei.utils;

import android.Manifest.permission;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Copyright © 2015 Sanbo Inc. All rights reserved.
 * @Description: 获取设备信息和App信息的
 * @Create: 2015年6月29日 下午6:32:35
 * @Author: sanbo
 */
public class DeviceInfo {
    protected static final String LOG_TAG = DeviceInfo.class.getName();
    protected static final String UNKNOW = "Unknown";
    private static final String MOBILE_NETWORK = "2G/3G";
    private static final String WIFI = "Wi-Fi";
    public static final int DEFAULT_TIMEZONE = 8;
    private static int[] screenWidthAndHeight;

    /**
     * 判断是否已安装App
     *
     * @param packageInfo
     *            App包名
     * @param context
     * @return
     */
    public static boolean isAppInstalled(String packageInfo, Context context) {
        if (context == null) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageInfo, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    /**
     * 判断系统语言是否为简体中文
     *
     * @param context
     *            用于获取系统设置的语言环境
     * @return 当前设备系统语言是否为简体中文 (zh_CN) 时返回true,否则返回false (zh_TW,zh_,en...)
     */
    public static boolean isChinese(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.toString().equals(Locale.CHINA.toString());
    }

    /**
     * 获取当前设备已安装的应用集合 </p>
     *
     * @param context
     *            用户获取已安装应用信息
     * @return 已安装应用的package信息集合
     */
    public static Set<String> getInstalledPackages(Context context) {
        Set<String> set = new HashSet<String>();
        List<PackageInfo> pInfos = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < pInfos.size(); i++) {
            PackageInfo pInfo = pInfos.get(i);
            set.add(pInfo.packageName);
        }
        return set;
    }

    /**
     * 判断设备当前屏幕方向是否为竖向
     *
     * @param context
     * @return 当且仅当设备竖直显示返回 true 否则 返回 false
     */
    public static boolean isScreenPortrait(Context context) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前应用是否具有指定的权限
     *
     * @param context
     * @param permission
     *            权限信息的完整名称 如：<code>android.permission.INTERNET</code>
     * @return 当前仅当宿主应用含有 参数 permission 对应的权限 返回true 否则返回 false
     */
    public static boolean checkPermission(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission(permission, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 读取GPU信息
     *
     * @param gl
     *            openGL实例
     * @return 返回数组 String[]{GPU Vender, GPU Reader}, 读不到将返回 String[]{null}
     */
    public static String[] getGPU(GL10 gl) {
        try {
            String[] res = new String[2];
            String vendor = gl.glGetString(GL10.GL_VENDOR);
            String renderer = gl.glGetString(GL10.GL_RENDERER);
            res[0] = vendor;
            res[1] = renderer;
            return res;
        } catch (Exception e) {
            Log.e(DeviceInfo.LOG_TAG, "Could not read gpu infor:", e);
            return new String[0];
        }
    }

    /**
     * 获取设备的 Cpu 信息
     *
     * @return 设备 Cpu 型号信息 可能返回""
     */
    public static String getCPU() {
        String cpuInfo = null;

        FileReader fstream = null;
        BufferedReader in = null;

        try {
            fstream = new FileReader("/proc/cpuinfo");
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    cpuInfo = in.readLine();
                    in.close();
                    fstream.close();
                } catch (IOException e) {
                    Log.e(DeviceInfo.LOG_TAG, "Could not read from file /proc/cpuinfo", e);
                }
            }
        } catch (FileNotFoundException e) {
            Log.e(DeviceInfo.LOG_TAG, "Could not open file /proc/cpuinfo", e);
        }

        if (cpuInfo != null) {
            int start = cpuInfo.indexOf(':') + 1;
            cpuInfo = cpuInfo.substring(start);
        }
        return cpuInfo.trim();
    }

    /**
     * 获取设备唯一标识 </p> 注意：需要权限 android.permission.READ_PHONE_STATE
     *
     * @param context
     * @return 返回设备唯一标识IMEI码 , 如果读取不到，将返回MAC 地址，出错返回"Unknow"
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (tm == null) {
            Log.w(LOG_TAG, "No IMEI.");
        }

        String imei = "";
        try {
            if (checkPermission(context, permission.READ_PHONE_STATE)) {
                imei = tm.getDeviceId();
            }
        } catch (Exception ex) {
            Log.w(LOG_TAG, "No IMEI.", ex);
        }

        if (TextUtils.isEmpty(imei)) {
            Log.w(LOG_TAG, "No IMEI.");
            imei = getMac(context);
            if (TextUtils.isEmpty(imei)) {
                Log.w(LOG_TAG, "Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead.");
                imei = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
                Log.w(LOG_TAG, "getDeviceId: Secure.ANDROID_ID: " + imei);
                return imei;
            }
        }
        return imei;
    }

    /**
     * 获取运营商信息
     *
     * @param context
     * @return the alphabetic name of current registered operator. 出错返回"Unknow"
     */
    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            if (tm == null) {
                return DeviceInfo.UNKNOW;
            }
            return tm.getNetworkOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
            return DeviceInfo.UNKNOW;
        }
    }

    /**
     * Get the mobile network access mode.
     *
     * @param context
     * @return A 2-elements String array, 1st specifies the network type, the
     *         2nd specifies the network subtype. If the network cannot be
     *         retrieved, "Unknown" is filled instead.
     */
    public static String[] getNetworkAccessMode(Context context) {

        String[] res = new String[] { DeviceInfo.UNKNOW, DeviceInfo.UNKNOW };
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission(permission.ACCESS_NETWORK_STATE, context.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
            res[0] = DeviceInfo.UNKNOW;
            return res;
        }

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            res[0] = DeviceInfo.UNKNOW;
            return res;
        } else {
            NetworkInfo wifi_network = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifi_network.getState() == NetworkInfo.State.CONNECTED) {
                res[0] = DeviceInfo.WIFI;
                return res;
            }
            NetworkInfo mobile_network = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile_network.getState() == NetworkInfo.State.CONNECTED) {
                res[0] = DeviceInfo.MOBILE_NETWORK;
                res[1] = mobile_network.getSubtypeName();
                return res;
            }
        }
        return res;
    }

    public static boolean isWiFiAvailable(Context context) {
        return WIFI.equals(DeviceInfo.getNetworkAccessMode(context)[0]);
    }

    /**
     * <p>
     * Get the {@link android.location.Location#member Location}
     * </p>
     * <b>NOTE:</b> 需要权限：<code>android.permission.ACCESS_FINE_LOCATION
     * </p>
     *
     * @param context
     * @return 如果没有权限或无法获取位置信息返回 null
     */
    public static Location getLocation(Context context) {
        LocationManager lm = null;
        try {
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (DeviceInfo.checkPermission(context, "android.permission.ACCESS_FINE_LOCATION")) {
                final Location lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    Log.i(DeviceInfo.LOG_TAG, "get location from gps:" + lastKnownLocation.getLatitude() + ","
                            + lastKnownLocation.getLongitude());
                    return lastKnownLocation;
                }
            }

            if (DeviceInfo.checkPermission(context, "android.permission.ACCESS_COARSE_LOCATION")) {
                final Location lastKnownLocationNet = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (lastKnownLocationNet != null) {
                    Log.i(DeviceInfo.LOG_TAG, "get location from network:" + lastKnownLocationNet.getLatitude() + ","
                            + lastKnownLocationNet.getLongitude());
                    return lastKnownLocationNet;
                }
            }

            Log.i(DeviceInfo.LOG_TAG,
                    "Could not get location from GPS or Cell-id, lack ACCESS_COARSE_LOCATION or ACCESS_COARSE_LOCATION permission?");
            return null;
        } catch (Exception ex) {
            Log.e(DeviceInfo.LOG_TAG, ex.getMessage());
            return null;
        }
    }

    /**
     * <p>
     * True if the device is connected or connection to network.
     * </p>
     * 需要权限: <code>android.permission.ACCESS_NETWORK_STATE</code> </p>
     *
     * @param context
     * @return 如果当前有网络连接返回 true 如果网络状态访问权限或没网络连接返回false
     */
    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                return ni.isConnectedOrConnecting();
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 检测网络是否可用，且是否有网络权限
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // 检测网络状况
        if (DeviceInfo.checkPermission(context, permission.ACCESS_NETWORK_STATE) && DeviceInfo.isOnline(context)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断Sd Card 是否可读可写
     *
     * @return 当且仅当Sdcard既可读又可写返回 true 否则返回false
     */
    public static boolean isSdCardWrittenable() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读取国家和语言
     *
     * @param context
     * @return 返回数组 String[]{Country, Language}, 读不到返回
     *         String[]{"Unknown","Unknown"}
     */
    public static String[] getLocaleInfo(Context context) {
        String[] cl = new String[] { "Unknown", "Unknown" };

        try {
            Configuration userConfig = new Configuration();
            Settings.System.getConfiguration(context.getContentResolver(), userConfig);
            if (userConfig != null && userConfig.locale != null) {
                cl[0] = userConfig.locale.getCountry();
                cl[1] = userConfig.locale.toString();
            } else {
                Locale locale = Locale.getDefault();
                if (locale != null) {
                    cl[0] = locale.getCountry();
                    cl[1] = locale.getLanguage();
                }
            }

            if (TextUtils.isEmpty(cl[0])) {
                cl[0] = "Unknown";
            }
            if (TextUtils.isEmpty(cl[1])) {
                cl[1] = "Unknown";
            }

            return cl;
        } catch (Exception e) {
            Log.e(LOG_TAG, "error in getLocaleInfo", e);
        }

        return cl;

    }

    /**
     * 读取手机MAC地址
     *
     * @param context
     * @return 返回mac地址
     */
    public static String getMac(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                Log.w(LOG_TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Could not get mac address." + e.toString());
        }
        return "";
    }

    /**
     * 读取分辨率
     *
     * @param context
     * @return 返回分辨率 width*height ,否则返回 Unknown
     */
    public static String getResolution(Context context) {
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
            wm.getDefaultDisplay().getMetrics(metrics);

            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            StringBuffer msb = new StringBuffer();
            msb.append(width);
            msb.append("*");
            msb.append(height);

            return msb.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, "read resolution fail", e);
        }
        return "Unknown";

    }

    /**
     * 读取运营商信息
     *
     * @param context
     * @return 返回运营商信息，否则返回 Unknown
     */
    public static String getOperator(Context context) {
        // Get Carrier
        try {
            return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName();
        } catch (Exception e) {
            Log.i(LOG_TAG, "read carrier fail", e);
        }
        return "Unknown";
    }

    /**
     * 返回应用名
     *
     * @param context
     * @return
     */
    public static String getApplicationLable(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    /**
     * <p>
     *
     * @Title: getScreenWidthAndHeight
     *         </p>
     *         <p>
     * @Description:获取手机屏幕的宽高 </p>
     * @param context
     * @return
     * @throws
     */
    public static int[] getScreenWidthAndHeight(Context context) {
        if (screenWidthAndHeight != null) {
            return screenWidthAndHeight;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;
            return new int[] { width, height };
        }
        return new int[] { 480, 800 };
    }

    /**
     * @Title: getTotalMemory
     * @Description: 获取内存总大小
     * @param context
     * @return
     * @throws
     */
    public static String getTotalMemory(Context context) {

        // 系统内存信息文件
        String file_path = "/proc/meminfo";
        String ram_info;
        String[] arrayOfRam;
        long initial_memory = 0;
        try {
            FileReader fr = new FileReader(file_path);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            // 读取meminfo第一行，系统总内存大小
            ram_info = localBufferedReader.readLine();
            // 实现多个空格切割的效果
            arrayOfRam = ram_info.split("\\s+");
            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            initial_memory = Integer.valueOf(arrayOfRam[1]).intValue() * 1024;
            localBufferedReader.close();
        } catch (IOException e) {
        }
        // Byte转换为KB或者MB，内存大小
        return Formatter.formatFileSize(context, initial_memory);
    }

    /**
     * @Title: getAvailMemory
     * @Description: // 获取android当前可用内存大小
     * @param context
     * @return
     * @throws
     */
    public static String getAvailMemory(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        // mi.availMem; 当前系统的可用内存
        am.getMemoryInfo(mi);
        // 将获取的内存大小规格化
        return Formatter.formatFileSize(context, mi.availMem);
    }
}
