package com.opengl.youyang.yumulibrary.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;

/**
 * Created by youyang on 16/3/30.
 */
public class CommonUtil {
    /**
     * 先测试是否为模拟器，如果是的话就假设其能运行OpenGL。不是模拟器的话就判断其是否支持OpenGL ES2.0
     *
     * @return
     */
    private boolean isSupportES2(Context context) {
        final ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = manager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic"))
                || (Build.FINGERPRINT.startsWith("unknow"))
                || (Build.MODEL.contains("google_sdk"))
                || (Build.MODEL.contains("Emulator"))
                || (Build.MODEL.contains("Android SDK built for x86"))
        );
    }
}
