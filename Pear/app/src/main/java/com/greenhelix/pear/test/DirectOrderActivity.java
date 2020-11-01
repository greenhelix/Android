package com.greenhelix.pear.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.greenhelix.pear.R;

/*주문 직접 입력 화면*/
public class DirectOrderActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ik";
    Spinner sender_tel1;
    Spinner recipient_tel1;
    EditText sender_name, sender_tel2, sender_tel3;
    EditText recipient_name, recipient_tel2, recipient_tel3;
    EditText address_num, address_detail1, address_detail2;
    Button direct_Next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_direct_info);
        Log.d(LOG_TAG, "직접 입력화면 정상 OnCreate 되었습니다.");

        //보내는사람
        sender_name = findViewById(R.id.et_direct_sender);
        //보내는사람 연락처
        sender_tel1=findViewById(R.id.sp_direct_sender_tel1);
        sender_tel2= findViewById(R.id.et_direct_sender_tel2);
        sender_tel3= findViewById(R.id.et_direct_sender_tel3);
        //받는사람
        recipient_name= findViewById(R.id.et_direct_recipient);
        //받는사람 연락처
        recipient_tel1= findViewById(R.id.sp_direct_recipient_tel1);
        recipient_tel2= findViewById(R.id.et_direct_recipient_tel2);
        recipient_tel3= findViewById(R.id.et_direct_recipient_tel3);
        //주소
        address_num= findViewById(R.id.et_direct_address_num);
        address_detail1= findViewById(R.id.et_direct_address_show);
        address_detail2= findViewById(R.id.et_direct_address_detail);

        direct_Next = findViewById(R.id.btn_direct_next);

    }//onCreate
}//class

//        direct_Next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent directData = new Intent(DirectOrderActivity.this, DirectShow.class);
//                startActivity(directData);
//                finish();
//            }
//        });