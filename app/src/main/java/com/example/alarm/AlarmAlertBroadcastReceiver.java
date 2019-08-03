package com.example.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.alarm.Alarm;
import com.example.alarm.AlarmServiceBroadcastReceiver;

import java.util.Random;

/**
 * Created by 정예린 on 12/2/2017.
 */

public class AlarmAlertBroadcastReceiver extends BroadcastReceiver {
    private int selectNum = new Random().nextInt((3  - 1) + 1) + 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mathAlarmServiceIntent = new Intent(context, AlarmServiceBroadcastReceiver.class);
        context.sendBroadcast(mathAlarmServiceIntent, null);

        StaticWakeLock.lockOn(context);
        Bundle bundle = intent.getExtras();
        final Alarm alarm = (Alarm) bundle.getSerializable("alarm");

        switch (selectNum) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    Intent mathAlarmAlertActivityIntent;
                    mathAlarmAlertActivityIntent = new Intent(context, AlarmAlertActivity.class);
                    mathAlarmAlertActivityIntent.putExtra("alarm", alarm);
                    mathAlarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mathAlarmAlertActivityIntent);
                }
                break;
            case 2:
                Intent taptapIntent;
                taptapIntent = new Intent(context, AlarmAlertActivity2.class);
                taptapIntent.putExtra("alarm", alarm);
                taptapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(taptapIntent);
                break;
            case 3:
                Intent tapItIntent;
                tapItIntent = new Intent(context, AlarmAlertActivity3.class);
                tapItIntent.putExtra("alarm", alarm);
                tapItIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(tapItIntent);
                break;
        }
    }
}
