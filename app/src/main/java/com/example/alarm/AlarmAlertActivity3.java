package com.example.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by 정예린 on 12/2/2017.
 */

public class AlarmAlertActivity3 extends Activity{
    private Alarm alarm;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private boolean alarmActive;

    Button btnNewGame;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    List<Integer> buttons;
    int currLV, currGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_just_tap_it);

        Bundle bundle = this.getIntent().getExtras();
        alarm = (Alarm) bundle.getSerializable("alarm");

        this.setTitle(alarm.getAlarmName());

        btnNewGame = (Button) findViewById(R.id.buttonNew);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn10 = (Button) findViewById(R.id.button10);
        btn11 = (Button) findViewById(R.id.button11);
        btn12 = (Button) findViewById(R.id.button12);

        btn1.setTag(1);
        btn2.setTag(2);
        btn3.setTag(3);
        btn4.setTag(4);
        btn5.setTag(5);
        btn6.setTag(6);
        btn7.setTag(7);
        btn8.setTag(8);
        btn9.setTag(9);
        btn10.setTag(10);
        btn11.setTag(11);
        btn12.setTag(12);

        btnSetDisabled();

        buttons = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            buttons.add(i);
        }

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNewGame.setVisibility(View.INVISIBLE);
                currGuess = 0;
                currLV = 1;
                generateBtn(currLV);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
            }
        });

        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogic(view);
                ((Button) view).setText("X");
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

    private void btnLogic(View view) {
        List<Integer> listTemp = new ArrayList<>();

        for(int i = 0; i < currLV; i++) {
            listTemp.add(buttons.get(i));
        }

        if(listTemp.contains(view.getTag())) {
            currGuess++;
            win();
        }
        else {
            btnSetDisabled();
            btnNewGame.setVisibility(View.VISIBLE);
            alertFail();
        }
    }

    private void win() {
        if(currGuess == currLV) {
            btnSetDisabled();
            if(currLV == 5) {
                alertSuccess();
            }
            else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currGuess = 0;
                        currLV++;
                        generateBtn(currLV);
                    }
                }, 1000);
            }
        }
    }

    private void generateBtn(int number) {
        setBtnBlank();

        Collections.shuffle(buttons);
        for (int i = 0; i < number; i++) {
            showButton(buttons.get(i));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setBtnBlank();
                btnSetEnabled();
            }
        }, 1000);
    }

    private void showButton(int num) {
        switch (num) {
            case 1:
                btn1.setText("X");
                break;
            case 2:
                btn2.setText("X");
                break;
            case 3:
                btn3.setText("X");
                break;
            case 4:
                btn4.setText("X");
                break;
            case 5:
                btn5.setText("X");
                break;
            case 6:
                btn6.setText("X");
                break;
            case 7:
                btn7.setText("X");
                break;
            case 8:
                btn8.setText("X");
                break;
            case 9:
                btn9.setText("X");
                break;
            case 10:
                btn10.setText("X");
                break;
            case 11:
                btn11.setText("X");
                break;
            case 12:
                btn12.setText("X");
                break;
        }
    }

    private void btnSetEnabled() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
        btn10.setEnabled(true);
        btn11.setEnabled(true);
        btn12.setEnabled(true);
    }

    private void btnSetDisabled() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);
        btn10.setEnabled(false);
        btn11.setEnabled(false);
        btn12.setEnabled(false);
    };

    private void setBtnBlank() {
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
        btn10.setText("");
        btn11.setText("");
        btn12.setText("");
    }

    public void alertSuccess() {
        AlertDialog.Builder success = new AlertDialog.Builder(AlarmAlertActivity3.this);
        success.setCancelable(false);
        success.setTitle("Congratulations!");
        success.setMessage("Yes, you WIN!");
        success.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        success.create().show();
    }

    public void alertFail() {
        AlertDialog.Builder fail = new AlertDialog.Builder(AlarmAlertActivity3.this);
        fail.setCancelable(false);
        fail.setTitle("Lol, failed.");
        fail.setMessage("Sorry, but you LOST!");
        fail.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btnNewGame.setVisibility(View.INVISIBLE);
                currGuess = 0;
                currLV = 1;
                generateBtn(currLV);
            }
        });
        fail.create().show();
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
}
