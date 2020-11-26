package com.greenhelix.pear.directOrder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.greenhelix.pear.R;
//import com.greenhelix.pear.server.FirebaseHosting;
import com.greenhelix.pear.server.YeongGift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/*주문 직접 입력 화면*/
public class DirectOrderActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ik";
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
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
    Button address_find;
    List<String> senderList = new ArrayList<>();
    List<String> recipientList= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_direct_info);
        sender_name = (EditText) findViewById(R.id.et_direct_sender);
        sender_tel1 = (EditText) findViewById(R.id.et_direct_sender_tel1);
        sender_tel2 = (EditText) findViewById(R.id.et_direct_sender_tel2);
        sender_tel3 = (EditText) findViewById(R.id.et_direct_sender_tel3);
        recipient_name = (EditText) findViewById(R.id.et_direct_recipient);
        recipient_tel1 = (EditText) findViewById(R.id.et_direct_recipient_tel1);
        recipient_tel2 = (EditText) findViewById(R.id.et_direct_recipient_tel2);
        recipient_tel3 = (EditText) findViewById(R.id.et_direct_recipient_tel3);
        address_num = (EditText) findViewById(R.id.et_direct_address_num);
        address_detail1 = (EditText) findViewById(R.id.et_direct_address_show);
        address_detail2 = (EditText) findViewById(R.id.et_direct_address_detail);
        direct_Next = (Button) findViewById(R.id.btn_direct_next);
        direct_Before = (Button) findViewById(R.id.btn_direct_before);
        direct_Before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        address_find = findViewById(R.id.btn_direct_address);
        if (address_find != null) {
            address_find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(DirectOrderActivity.this, YeongGift.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }
    }//OnCreate

    public void showData(View v){

        senderList.add(sender_name.getText().toString());
        senderList.add(sender_tel1.getText().toString());
        senderList.add(sender_tel2.getText().toString());
        senderList.add(sender_tel3.getText().toString());
        recipientList.add(recipient_name.getText().toString());
        recipientList.add(recipient_tel1.getText().toString());
        recipientList.add(recipient_tel2.getText().toString());
        recipientList.add(recipient_tel3.getText().toString());
        recipientList.add(address_num.getText().toString());
        recipientList.add(address_detail1.getText().toString());
        recipientList.add(address_detail2.getText().toString());
        Log.d(LOG_TAG,"sender ::"+senderList);
        Log.d(LOG_TAG,"recipient ::"+recipientList);
        Intent directData = new Intent(DirectOrderActivity.this, DirectShow.class);
        directData.putExtra("sender", (Serializable) senderList);
        directData.putExtra("recipient", (Serializable) recipientList);
        startActivity(directData);
    }//showData END


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        address_detail1.setText(data);
                    }
                }
                break;
        }
    }//activity result END

}// DirectOrderActivity class END

/*//////////////////////*/
//    public void addressAPI (View v){
//
//        address_find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(LOG_TAG, "주소찾기가 눌렸습니다.");
////                Intent apiCon = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pear-57581.web.app/jusoPopup.html"));
////                startActivity(apiCon);
//                Intent api = new Intent(DirectOrderActivity.this, FirebaseHosting.class);
//                startActivityForResult(api, KAKAO_ADR_API);
//            }
//        });
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent intent)
//    {
//        super.onActivityResult(requestCode, resultCode, intent);
//        switch (requestCode) {
//            case SEARCH_ADDRESS_ACTIVITY:
//                if (resultCode == RESULT_OK) {
//                    String data = intent.getExtras().getString("data");
//                    if (data != null) {
//                        et_address.setText(data);
//                    }
//                }
//                break;
//        }
//    }






