package com.example.timemachine;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends MainActivity {

    LinearLayout dynamicContent,bottonNavBar,setter;

    // Set Timer
    private NumberPicker minute, second;
    private TextView display;
    private ImageView timeUp;

    // Buttons
    private Button start;

    // Time
    long startTime = 0;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = startTime - System.currentTimeMillis();
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            display.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_timer);
        dynamicContent = (LinearLayout)  findViewById(R.id.dynamicContent);
        bottonNavBar= (LinearLayout) findViewById(R.id.bottonNavBar);
        View wizard = getLayoutInflater().inflate(R.layout.activity_timer, null);
        dynamicContent.addView(wizard);

        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton rb=(RadioButton)findViewById(R.id.timer);

        minute = findViewById(R.id.timer_minute);
        minute.setMinValue(0);
        minute.setMaxValue(59);
        minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startTime -= oldVal * 1000*60;
                startTime += newVal * 1000*60;
            }
        });
        second = findViewById(R.id.timer_second);
        second.setMinValue(0);
        second.setMaxValue(59);
        second.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                startTime -= oldVal * 1000;
                startTime += newVal * 1000;
            }
        });

        setter = findViewById(R.id.linearLayout_setTimer);
        display = findViewById(R.id.textView_timer_time);
        start = findViewById(R.id.button_timer_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setter.setVisibility(View.INVISIBLE);
                display.setVisibility(View.VISIBLE);
                startTime += System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                if (startTime - System.currentTimeMillis() == 0) {
                    timerHandler.removeCallbacks(timerRunnable);
                    timeUp = findViewById(R.id.imageView);
                    timeUp.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

//https://stackoverflow.com/questions/4597690/android-timer-how-to
