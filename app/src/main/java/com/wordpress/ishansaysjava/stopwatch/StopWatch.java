package com.wordpress.ishansaysjava.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;


public class StopWatch extends Activity {

    private boolean running;
    private int seconds;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        if(savedInstanceState != null)
        {
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            running = savedInstanceState.getBoolean("running");
            seconds = savedInstanceState.getInt("seconds");
        }
        runTime();
    }

    public void start(View view)
    {
        running = true;
    }

    public void stop(View view)
    {
        running = false;
    }

    public void reset(View view)
    {
        running = false;
        seconds = 0;
    }

    private void runTime()
    {
        final TextView display = (TextView) findViewById(R.id.display);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int min = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, min, secs);

                display.setText(time);
                if (running) seconds++;
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
        savedInstanceState.putBoolean("running", running);
    }

    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    protected void onResume()
    {
        super.onResume();
        if(wasRunning) running =true;
    }
}
