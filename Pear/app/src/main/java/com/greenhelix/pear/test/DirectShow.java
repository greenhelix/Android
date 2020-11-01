package com.greenhelix.pear.test;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhelix.pear.R;

public class DirectShow extends AppCompatActivity {
    private static final String LOG_TAG = "ik";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_show);
        Log.d(LOG_TAG,"DirectShow정상 가동되었습니다., OnCreate에 들어왔습니다.");


    }
}
