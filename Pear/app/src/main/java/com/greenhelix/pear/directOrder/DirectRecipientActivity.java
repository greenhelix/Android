package com.greenhelix.pear.directOrder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;
import com.greenhelix.pear.orderStatus.OrderStatusActivity;
import com.greenhelix.pear.server.AddressAPIkakao;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/*주문 직접 입력 화면*/
public class DirectRecipientActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ik";
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    EditText recipient_tel1;
    EditText recipient_name;
    EditText recipient_tel2;
    EditText recipient_tel3;
    EditText address_num;
    EditText address_detail1;
    EditText address_detail2;
    Button direct_next;
    Button direct_before;
    Button address_find;
    List<String> senderList = new ArrayList<>();
    List<String> recipientList= new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "받는사람 정보입력 종료 확인");
        if(doubleBackToExitPressedOnce){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DirectRecipientActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG," 종료 합니다.");
                    ActivityCompat.finishAffinity(DirectRecipientActivity.this);
                }
            }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG,"유지합니다.");

                }
            }).show();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "뒤로버튼을 한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        recipientList = new ArrayList<>();
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_recipient_info);

        recipient_name = (EditText) findViewById(R.id.et_direct_recipient);
        recipient_tel1 = (EditText) findViewById(R.id.et_direct_recipient_tel1);
        recipient_tel2 = (EditText) findViewById(R.id.et_direct_recipient_tel2);
        recipient_tel3 = (EditText) findViewById(R.id.et_direct_recipient_tel3);
        address_num = (EditText) findViewById(R.id.et_direct_address_num);
        address_detail1 = (EditText) findViewById(R.id.et_direct_address_show);
        address_detail2 = (EditText) findViewById(R.id.et_direct_address_detail);
        direct_next = (Button) findViewById(R.id.btn_direct_next);
        direct_before = (Button) findViewById(R.id.btn_direct_before);
        direct_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        address_find = findViewById(R.id.btn_direct_address);

        address_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(DirectRecipientActivity.this, AddressAPIkakao.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });

    }//OnCreate

    public void showData(View v){

        //보내는 사람 정보 수령(이전화면에서)
        senderList = getIntent().getStringArrayListExtra("sender");
        //받는 사람 정보 입력
        recipientList.add(recipient_name.getText().toString());
        recipientList.add(recipient_tel1.getText().toString());
        recipientList.add(recipient_tel2.getText().toString());
        recipientList.add(recipient_tel3.getText().toString());
        recipientList.add(address_num.getText().toString());
        recipientList.add(address_detail1.getText().toString());
        recipientList.add(address_detail2.getText().toString());

        //주문 정보 데이터 확인
        Log.d(LOG_TAG,"데이터2 확인 sender ::"+senderList);
        Log.d(LOG_TAG,"데이터2 확인 recipient ::"+recipientList);

        //정보 합치기
        Intent directData = new Intent(DirectRecipientActivity.this, DirectShow.class);
        directData.putExtra("sender", (Serializable) senderList);
        directData.putExtra("recipient", (Serializable) recipientList);

        if(TextUtils.isEmpty(recipient_name.getText().toString())){
            Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(recipient_tel1.getText().toString())  || TextUtils.isEmpty(recipient_tel2.getText().toString())  || TextUtils.isEmpty(recipient_tel3.getText().toString())){
            Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(address_num.getText().toString())  || TextUtils.isEmpty(address_detail1.getText().toString())  || TextUtils.isEmpty(address_detail2.getText().toString())){
            Toast.makeText(getApplicationContext(), "주소를 다 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(directData);
        }

    }//showData END

    /*주소찾기 작업후 조회 데이터 가져오는 부분*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.d(LOG_TAG, "### 받는 주소값 ### \n"+ data);
                        String[] address = data.split(",");
                        address_num.setText(address[0]);
                        address_detail1.setText(address[1]);
                    }
                }
                break;
        }
    }//activity result END

}// DirectOrderActivity class END