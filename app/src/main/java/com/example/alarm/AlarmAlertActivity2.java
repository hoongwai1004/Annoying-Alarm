package com.example.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by 정예린 on 12/2/2017.
 */

public class AlarmAlertActivity2 extends Activity{
    private Alarm alarm;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private boolean alarmActive;

    private int min = 20;
    private int max = 40;
    private int r = new Random().nextInt((max - min) + 1) + min;
    private int randomTime = new Random().nextInt((30 - 10) + 1) + 10;
    private int time = randomTime;
    private int clicks = 0;

    private CountDownTimer cdTimer;
    private TextView required;
    private TextView countDown;
    private Button pressMe;
    private Button start;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.taptap);

        Bundle bundle = this.getIntent().getExtras();
        alarm = (Alarm) bundle.getSerializable("alarm");

        this.setTitle(alarm.getAlarmName());

        countDown = (TextView) findViewById(R.id.countDownTimer);
        required = (TextView) findViewById(R.id.numRequired);
        pressMe = (Button)  findViewById(R.id.btnPressMe);
        start = (Button) findViewById(R.id.btnStart);
        confirm = (Button) findViewById(R.id.btnConfirm);

        start.setEnabled(true);
        pressMe.setEnabled(false);
        confirm.setEnabled(false);

        required.setText(String.valueOf(r));
        countDown.setText(String.valueOf(randomTime));

        cdTimer = new CountDownTimer(randomTime * 1000, 1000) {
            @Override
            public void onTick(long l) {
                time--;
                countDown.setText(String.valueOf(time));
            }

            @Override
            public void onFinish() {
                start.setEnabled(true);
                pressMe.setEnabled(false);
                confirm.setEnabled(false);
                pressMe.setEnabled(false);
                confirm.setEnabled(false);
                cdTimer.cancel();
                countDown.setText(String.valueOf(0));
                failedAlertDialog();
            }
        };

        pressMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks++;
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdTimer.start();
                start.setEnabled(false);
                pressMe.setEnabled(true);
                confirm.setEnabled(true);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicks == r && time >= 0) {
                    pressMe.setEnabled(false);
                    cdTimer.cancel();
                    successAlertDialog();
                }
                else if(clicks != r) {
                    pressMe.setEnabled(false);
                    cdTimer.cancel();
                    failedAlertDialog();
                }
            }
        });

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(getClass().getSimpleName(), "Incoming call: "
                                + incomingNumber);
                        try {
                            mediaPlayer.pause();
                        } catch (IllegalStateException e) {

                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d(getClass().getSimpleName(), "Call State Idle");
                        try {
                            mediaPlayer.start();
                        } catch (IllegalStateException e) {

                        }
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        startAlarm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmActive = true;
    }

    private void startAlarm() {
        if (alarm.getAlarmTonePath() != "") {
            mediaPlayer = new MediaPlayer();
            if (alarm.getVibrate()) {
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                long[] pattern = { 1000, 200, 200, 200 };
                vibrator.vibrate(pattern, 0);
            }
            try {
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setDataSource(this,
                        Uri.parse(alarm.getAlarmTonePath()));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                mediaPlayer.release();
                alarmActive = false;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!alarmActive)
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StaticWakeLock.lockOff(this);
    }

    @Override
    protected void onDestroy() {
        try {
            if (vibrator != null)
                vibrator.cancel();
        } catch (Exception e) {

        }
        try {
            mediaPlayer.stop();
        } catch (Exception e) {

        }
        try {
            mediaPlayer.release();
        } catch (Exception e) {

        }
        super.onDestroy();
    }

    public void successAlertDialog() {
        AlertDialog.Builder successBuilder = new AlertDialog.Builder(AlarmAlertActivity2.this);
        successBuilder.setCancelable(false);
        successBuilder.setTitle("Congratulations!");
        successBuilder.setMessage("You pressed the button " + clicks + " times button successfully in time limit");
        successBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        successBuilder.create().show();
    }

    public void failedAlertDialog() {
        AlertDialog.Builder failedBuilder = new AlertDialog.Builder(AlarmAlertActivity2.this);
        failedBuilder.setCancelable(false);
        failedBuilder.setTitle("Lol, failed.");
        failedBuilder.setMessage("You pressed " + clicks + " times! ");
        failedBuilder.setPositiveButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                randomTime = new Random().nextInt((30 - 10) + 1) + 10;
                time = randomTime;
                clicks = 0;
                recreate();
            }
        });
        failedBuilder.create().show();
    }
}
