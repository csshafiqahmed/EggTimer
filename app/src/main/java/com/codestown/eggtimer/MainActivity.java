package com.codestown.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    ImageView eggImageView;
    TextView countDownTextView;
    Button goButton;
    CountDownTimer countDownTimer;

    boolean isCounterActive = false;

    public void resetTimer() {
        countDownTextView.setText("00:30");
        goButton.setText("GO");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        isCounterActive = false;
        countDownTimer.cancel();
    }

    public void buttonClicked(View view) {
        if (isCounterActive) {
            resetTimer();

        } else {
            isCounterActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();


        }

    }

    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        countDownTextView.setText(minutes + " : " + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        timerSeekBar.setProgress(30);
        timerSeekBar.setMax(600);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initViews() {
        timerSeekBar = findViewById(R.id.timerSeekBar);
        eggImageView = findViewById(R.id.eggImageView);
        countDownTextView = findViewById(R.id.countDownTextView);
        goButton = findViewById(R.id.goButton);
    }
}