package com.example.alarm;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by 정예린 on 12/2/2017.
 */

public class StaticWakeLock {
    private static PowerManager.WakeLock wl = null;

    public static void lockOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (wl == null)
            wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "MATH_ALARM");
        wl.acquire();
    }

    public static void lockOff(Context context) {
//		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        try {
            if (wl != null)
                wl.release();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
