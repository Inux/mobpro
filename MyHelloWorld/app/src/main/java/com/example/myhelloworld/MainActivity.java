package com.example.myhelloworld;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("onCreate savedInstanceState", savedInstanceState);
        super.onCreate(savedInstanceState);
        Log.w("after calling super constructor", savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
