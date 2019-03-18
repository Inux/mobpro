package com.example.communicationandconcurrency;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class MainActivityBkp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startCommHttpServer(View view) {
        Log.i("MainActivity", "startCommHttpServer");
    }

    public void startHttpDemos(View view) {
        Log.i("MainActivity", "startHttpDemos");
    }

    public void startAsyncDemoTask(View view) {
        Log.i("MainActivity", "startHttpDemos");
    }

    public void startDemoThread(View view) {
        Log.i("MainActivity", "startHttpDemos");
    }

    public void blockUIforSevenSeconds(View view) {
        Log.i("MainActivity", "startHttpDemos");
    }

    public void startAsyncMultiTask(View view) {
        Log.i("MainActivity", "startHttpDemos");
    }
}
