package com.huashengmi.devlibs.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: huangsanm@foxmail.com
 * Date: 2016-03-16
 * Time: 15:50
 */
public class Globals {

    public static void log(Object args) {
        if (args instanceof Exception) {
            Exception e = (Exception) args;
            Log.e("Meijuu", e.getMessage(), e);
        } else {
            Log.i("Meijuu", "" + args);
        }
    }

    // 手机验证
    public static boolean isMobile(String mobile) {
        if (TextUtils.isEmpty(mobile))
            return false;
        String check = "^1\\d{10}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mobile);
        return matcher.matches();
    }

    // 获取设备id
    public static String getDeviceID(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 生成图片
     *
     * @param context
     * @param drawableName 名称
     * @return
     */
    public static int createDrawableByIdentifier(Context context,
                                                 String drawableName) {
        String pkgName = context.getPackageName();
        return context.getResources().getIdentifier(drawableName, "drawable",
                pkgName);
    }

    /**
     * 生成图片
     *
     * @param context
     * @return
     */
    public static View createViewByIdentifier(Context context, View root, String viewName) {
        final int viewId = context.getResources().getIdentifier(viewName, "id", context.getPackageName());
        return root.findViewById(viewId);
    }

    public static String getDeviceName() {
        String name = android.os.Build.MODEL;
        String manufacturer = android.os.Build.MANUFACTURER;
        if (name.indexOf(manufacturer) >= 0) {
            return name;
        } else {
            return manufacturer + " " + name;
        }
    }

    // 邮箱验证
    public static boolean isEmail(String textStr) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(textStr);
        return matcher.matches();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForegro(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 图片圆形处理
     * @param bitmap
     * @return
     */
    public static Bitmap convertRoundBitmap(Bitmap bitmap, int radius) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}
