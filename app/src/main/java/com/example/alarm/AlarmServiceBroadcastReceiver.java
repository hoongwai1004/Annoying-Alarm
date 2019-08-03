package com.example.alarm;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 정예린 on 12/2/2017.
 */

public class AlarmServiceBroadcastReceiver extends BroadcastReceiver {
    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AlarmServiceBroadcastReciever", "onReceive()");
        Intent serviceIntent = new Intent(context, AlarmService.class);
        context.startService(serviceIntent);
    }
}
