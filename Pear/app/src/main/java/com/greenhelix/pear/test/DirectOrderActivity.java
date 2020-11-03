package com.greenhelix.pear.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.greenhelix.pear.R;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*주문 직접 입력 화면*/
public class DirectOrderActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ik";
        EditText sender_tel1;
        EditText recipient_tel1;
        EditText sender_name;
        EditText sender_tel2;
        EditText sender_tel3;
        EditText recipient_name;
        EditText recipient_tel2;
        EditText recipient_tel3;
        EditText address_num;
        EditText address_detail1;
        EditText address_detail2;
        Button direct_Next;
        Button direct_Before;
        List<String> senderList = new ArrayList<>();
        List<String> recipientList= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_direct_info);
        direct_Next = (Button) findViewById(R.id.btn_direct_next);
        direct_Before = (Button) findViewById(R.id.btn_direct_before);
        direct_Before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try { //예외처리

            sender_tel1.findViewById(R.id.et_direct_sender_tel1);
            sender_tel2.findViewById(R.id.et_direct_sender_tel2);
            sender_tel3.findViewById(R.id.et_direct_sender_tel3);
            recipient_name.findViewById(R.id.et_direct_recipient);
            recipient_tel1.findViewById(R.id.et_direct_recipient_tel1);
            recipient_tel2.findViewById(R.id.et_direct_recipient_tel2);
            recipient_tel3.findViewById(R.id.et_direct_recipient_tel3);
            address_num.findViewById(R.id.et_direct_address_num);
            address_detail1.findViewById(R.id.et_direct_address_show);
            address_detail2.findViewById(R.id.et_direct_address_detail);
        }catch (NullPointerException e){
            Log.d(LOG_TAG, "아무것도 안쓴상태"+e);
        }
        setResult(1);


    }//OnCreate
    public void showData(View v){
        sender_name = (EditText) findViewById(R.id.et_direct_sender);
        senderList.add(sender_name.getText().toString());
        Log.d(LOG_TAG,"show"+senderList);
        Intent directData = new Intent(DirectOrderActivity.this, DirectShow.class);
        directData.putExtra("sender", (Serializable) senderList);
        directData.putExtra("recipient", (Serializable) recipientList);
        startActivity(directData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "보내는 사람 리스트:: " +senderList);
        Log.d(LOG_TAG, "받는 사람 리스트:: " +recipientList);
    }


}//class