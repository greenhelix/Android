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

import java.util.ArrayList;
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

        btnShow = (Button) findViewById(R.id.btn_click);
        tvShow = (TextView) findViewById(R.id.tv_test_show);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> senderData = getIntent().getExtras().getStringArrayList("sender");
                ArrayList<String> recipientData = getIntent().getExtras().getStringArrayList("recipient");
                tvShow.append("보내는 사람 \n");
                tvShow.append("보내는 사람 이름 : "+senderData.get(0)+"\n");
//                tvShow.append("보내는 사람 전화번호 : "+senderData.get(1)+"-"+senderData.get(2)+"-"+senderData.get(3)+"\n");
//
//                tvShow.append("받는 사람 \n");
//                tvShow.append("받는 사람 이름 :"+recipientData.get(0)+"\n");
//                tvShow.append("받는 사람 전화번호 : "+recipientData.get(1)+"-"+recipientData.get(2)+"-"+recipientData.get(3)+"\n");
//                tvShow.append("받는 사람 우편번호 :"+recipientData.get(4)+"\n");
//                tvShow.append("받는 사람 주소 :"+recipientData.get(5)+"\n");
//                tvShow.append("받는 사람 상세주소 :"+recipientData.get(6)+"\n");
            }
        });
    }

}
