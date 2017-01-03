package cn.com.ssii.android.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Name:DeviceUtil
 * Description:
 * Author:leix
 * Time: 2016/10/17 15:17
 */

public class DeviceUtil {
    public static String getUUid(Context context) {
        String id = getId(context);
        if (TextUtils.isEmpty(id)) id = getSerialId(context);
        if(TextUtils.isEmpty(id)) id=getAndroidId(context);
        if (TextUtils.isEmpty(id)) id = getMac(context);
        if (TextUtils.isEmpty(id)) id = getSId(context);
        return id;
    }

    /**
     * 设备制造商
     *
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 设备型号
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 设置品牌
     *
     * @return
     */
    public static String getGrand() {
        return Build.BRAND;
    }

    /**
     * 设备名
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 系统版本号
     *
     * @return
     */
    public static String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 系统编号
     *
     * @return
     */
    public static String getVersionCode() {
        return Build.VERSION.CODENAME;
    }

    /**
     * 获取设备编号
     * 要获取设备的READ_PHONE_STATUS权限
     *
     * @param context
     * @return
     */
    public static String getId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String id = tm.getDeviceId();
        return id;
    }

    /**
     * 获取设备的Mac地址
     *
     * @param context
     * @return
     */
    public static String getMac(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String mac = tm.getSimSerialNumber();
        return mac;
    }

    public static String getAndroidId(Context context) {
        String id = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return id;
    }

    public static String getSerialId(Context context) {
        return android.os.Build.SERIAL;
    }

    /**
     * 生成唯一的识别码
     *
     * @param context
     * @return
     */
    private static String SID = null;
    private static final String INSTALLATION = "idou";

    public synchronized static String getSId(Context context) {
        if (SID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists()) writeInstallationFile(installation);
                SID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return SID + "xd";
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
    }
}



