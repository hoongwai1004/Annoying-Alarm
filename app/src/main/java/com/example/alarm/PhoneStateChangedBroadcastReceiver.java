package com.example.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 정예린 on 12/2/2017.
 */

public class PhoneStateChangedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getSimpleName(), "onReceive()");
    }
}
