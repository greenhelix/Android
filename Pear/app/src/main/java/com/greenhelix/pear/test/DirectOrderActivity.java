package com.greenhelix.pear.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.greenhelix.pear.R;

import java.util.List;

/*주문 직접 입력 화면*/
public class DirectOrderActivity extends AppCompatActivity {

    Spinner sender_tel1, recipient_tel1;
    EditText sender_name, sender_tel2, sender_tel3;
    EditText recipient_name, recipient_tel2, recipient_tel3;
    EditText address_num, address_detail1, address_detail2;

    Button direct_Next;
    List<String> senderInfo, recipientInfo;

    private static final String LOG_TAG = "ik";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_direct_info);
        //보내는사람
        sender_name.findViewById(R.id.et_direct_sender);
        //보내는사람 연락처
        sender_tel1.findViewById(R.id.sp_direct_sender_tel1);
        sender_tel2.findViewById(R.id.et_direct_sender_tel2);
        sender_tel3.findViewById(R.id.et_direct_sender_tel3);
        //받는사람
        recipient_name.findViewById(R.id.et_direct_recipient);
        //받는사람 연락처
        recipient_tel1.findViewById(R.id.sp_direct_recipient_tel1);
        recipient_tel2.findViewById(R.id.et_direct_recipient_tel2);
        recipient_tel3.findViewById(R.id.et_direct_recipient_tel3);
        //주소
        address_num.findViewById(R.id.et_direct_address_num);
        address_detail1.findViewById(R.id.et_direct_address_show);
        address_detail2.findViewById(R.id.et_direct_address_detail);

        recipientInfo.add(address_num.getText().toString());
        recipientInfo.add(address_detail1.getText().toString());
        recipientInfo.add(address_detail2.getText().toString());


        direct_Next.findViewById(R.id.btn_direct_next);
        direct_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(sender_name.getText())){
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    senderInfo.add(sender_name.getText().toString());
                }
                if(TextUtils.isEmpty(sender_tel2.getText()) && TextUtils.isEmpty(sender_tel3.getText())){
                    Toast.makeText(getApplicationContext(), "전화번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    senderInfo.add(sender_tel1.getSelectedItem().toString()+sender_tel2.getText().toString()+sender_tel3.getText().toString());
                }

                if(TextUtils.isEmpty(recipient_name.getText())){
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    senderInfo.add(recipient_name.getText().toString());
                }
                if(TextUtils.isEmpty(recipient_tel2.getText()) && TextUtils.isEmpty(recipient_tel3.getText())){
                    Toast.makeText(getApplicationContext(), "전화번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    recipientInfo.add(recipient_tel1.getSelectedItem().toString()+recipient_tel2.getText().toString()+recipient_tel3.getText().toString());
                }




            }
        });



    }



}
//        try{
//            Log.d(LOG_TAG, "보내는 사람 이름 가져옴::"+ senderInfo.get(0));
//        }catch (NullPointerException e){
//            Log.d(LOG_TAG, "보내는 사람에 입력이 안되었습니다.");
//        }