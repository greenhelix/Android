package com.greenhelix.pear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Intro extends Activity {
    private static final String LOG_TAG = "ik";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "인트로 화면...");
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        }, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}