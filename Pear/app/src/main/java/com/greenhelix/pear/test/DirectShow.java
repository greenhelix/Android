package com.greenhelix.pear.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhelix.pear.R;
import java.util.List;

public class DirectShow extends AppCompatActivity {
    private static final String LOG_TAG = "ik";

    Button btnShow ;
    TextView tvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_show);
        Log.d(LOG_TAG,"DirectShow정상 가동되었습니다. OnCreate에 들어왔습니다.");

        btnShow.findViewById(R.id.btn_click);
        tvShow.findViewById(R.id.tv_test_show);
    }

}
