package com.greenhelix.pear.directOrder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhelix.pear.R;
import com.greenhelix.pear.cloudDB.CloudStore;

import java.util.ArrayList;

public class DirectShow extends AppCompatActivity {
    private static final String LOG_TAG = "ik";

    Button btnShow ;
    Button btnresultNext;
    Button btnresultBefore;
    TextView tvShow;
    ArrayList<String> senderData;
    ArrayList<String> recipientData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct_order_result);
        Log.d(LOG_TAG,"DirectShow정상 가동되었습니다. OnCreate에 들어왔습니다.");

        btnShow = (Button) findViewById(R.id.btn_click);
        tvShow = (TextView) findViewById(R.id.tv_test_show);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senderData = getIntent().getExtras().getStringArrayList("sender");
                recipientData = getIntent().getExtras().getStringArrayList("recipient");
                senderData.add("주문 정보입니다.\n");
                tvShow.append("번호 : "+senderData.get(4)+"\n");
                tvShow.append("보내는 사람 \n");
                tvShow.append("보내는 사람 이름 : "+senderData.get(0)+"\n");
                tvShow.append("보내는 사람 전화번호 : "+senderData.get(1)+"-"+senderData.get(2)+"-"+senderData.get(3)+"\n");

                tvShow.append("받는 사람 \n");
                tvShow.append("받는 사람 이름 :"+recipientData.get(0)+"\n");
                tvShow.append("받는 사람 전화번호 : "+recipientData.get(1)+"-"+recipientData.get(2)+"-"+recipientData.get(3)+"\n");
                tvShow.append("받는 사람 우편번호 :"+recipientData.get(4)+"\n");
                tvShow.append("받는 사람 주소 :"+recipientData.get(5)+"\n");
                tvShow.append("받는 사람 상세주소 :"+recipientData.get(6)+"\n");
            }
        });

        btnresultBefore = (Button) findViewById(R.id.btn_direct_result_before);
        btnresultBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnresultNext = (Button) findViewById(R.id.btn_direct_result_next);
        btnresultNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent directData = new Intent(DirectShow.this, CloudStore.class);
                directData.putExtra("direct_sender", senderData);
                directData.putExtra("direct_recipient", recipientData);
                startActivity(directData);
            }
        });
    }

}
