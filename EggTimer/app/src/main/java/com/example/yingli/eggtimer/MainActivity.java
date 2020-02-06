package com.example.yingli.eggtimer;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    SeekBar timeSeekBar;
    TextView timeTextView;
    MediaPlayer mediaPlayer;
    int startPos = 30;

    public void restart(Button startButton){
        startButton.setText("GO!");

        timeTextView.setText("00:30");

        timeSeekBar.setVisibility(View.VISIBLE);
        timeSeekBar.setProgress(30);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer(view);

            }
        });
    }

    public void startTimer(final View view){

        timeSeekBar = (SeekBar) findViewById(R.id.timeSeekBar);
        timeTextView = (TextView) findViewById(R.id.timeTextView);

        final Button startButton = (Button) findViewById(R.id.startButton);

        startButton.setText("Stop");


        int time = timeSeekBar.getProgress();

        timeSeekBar.setVisibility(View.INVISIBLE);

        Log.i("time:", Integer.toString(time));
        final int milliTime = time*1000;

        final CountDownTimer countDown = new CountDownTimer(milliTime, 1000){
            public void onTick(long millisecondUntilDone){
                //Log.i("seconds left!", String.valueOf(millisecondUntilDone/1000));

                long minute = (millisecondUntilDone/1000) / 60;
                long seconds = (millisecondUntilDone/1000) % 60;

                String formattedMinutes = String.format("%02d", minute);
                String formattedSec = String.format("%02d", seconds);


                timeTextView.setText(formattedMinutes + ":" + formattedSec);

            }

            public void onFinish(){
                //Log.i("we're done", "mo more countdoen");
                mediaPlayer.start();

                restart(startButton);


            }


        }.start();


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.cancel();

                restart(startButton);

            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int max = 500;


        timeSeekBar = (SeekBar) findViewById(R.id.timeSeekBar);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        mediaPlayer = MediaPlayer.create(this, R.raw.horn);


        timeSeekBar.setMax(max);
        timeSeekBar.setProgress(startPos);
        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                int min = 1;
                int timesTableNumber;

                if(i < min){
                    timesTableNumber = min;

                }else{
                    timesTableNumber = i;
                }

                Log.i("seekbar changed", Integer.toString(timesTableNumber));

                int minute = timesTableNumber / 60;
                int seconds = timesTableNumber % 60;

                String formattedMinutes = String.format("%02d", minute);
                String formattedSec = String.format("%02d", seconds);

                timeTextView.setText(formattedMinutes + ":" + formattedSec);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
