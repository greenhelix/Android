package com.greenhelix.pear.directOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.greenhelix.pear.R;
import com.greenhelix.pear.server.AddressAPIkakao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DirectSenderActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    private static final int SEARCH_ADDRESS_ACTIVITY = 20000;
    EditText sender_name;
    EditText sender_tel1, sender_tel2, sender_tel3;
    EditText senderAddress1, senderAddress2, senderAddress3;
    List<String> senderInfo = new ArrayList<>();
    Button direct_sender_next, direct_sender_before;
    Button address_find_sender;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "받는사람 정보입력 종료 확인");
        if(doubleBackToExitPressedOnce){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DirectSenderActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG," 종료 합니다.");
                    ActivityCompat.finishAffinity(DirectSenderActivity.this);
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
        senderInfo = new ArrayList<>();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_sender);

        sender_name = (EditText) findViewById(R.id.et_direct_sender);
        sender_tel1 = (EditText) findViewById(R.id.et_direct_sender_tel1);
        sender_tel2 = (EditText) findViewById(R.id.et_direct_sender_tel2);
        sender_tel3 = (EditText) findViewById(R.id.et_direct_sender_tel3);
        senderAddress1 = (EditText) findViewById(R.id.et_direct_address_num);
        senderAddress2 = (EditText) findViewById(R.id.et_direct_address_show);
        senderAddress3 = (EditText) findViewById(R.id.et_direct_address_detail);
        address_find_sender = findViewById(R.id.btn_sender_direct_address);
        address_find_sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(DirectSenderActivity.this, AddressAPIkakao.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });
        direct_sender_next = findViewById(R.id.btn_direct_sender_next);
        direct_sender_before = findViewById(R.id.btn_direct_sender_before);
        direct_sender_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void senderData(View v){

        senderInfo.add(sender_name.getText().toString());
        senderInfo.add(sender_tel1.getText().toString());
        senderInfo.add(sender_tel2.getText().toString());
        senderInfo.add(sender_tel3.getText().toString());
        senderInfo.add(senderAddress1.getText().toString());
        senderInfo.add(senderAddress2.getText().toString());
        senderInfo.add(senderAddress3.getText().toString());
        Log.d(LOG_TAG,"데이터1 : sender ::"+senderInfo);
        Intent directSenderData = new Intent(DirectSenderActivity.this, DirectRecipientActivity.class);
        directSenderData.putExtra("sender", (Serializable) senderInfo);

        if(TextUtils.isEmpty(sender_name.getText().toString())){
            Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(sender_tel1.getText().toString()) || TextUtils.isEmpty(sender_tel2.getText().toString()) || TextUtils.isEmpty(sender_tel3.getText().toString())){
            Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(senderAddress1.getText().toString()) || TextUtils.isEmpty(senderAddress2.getText().toString()) || TextUtils.isEmpty(senderAddress3.getText().toString())){
            Toast.makeText(getApplicationContext(), "주소를 다 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else{
            startActivity(directSenderData);
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
                        Log.d(LOG_TAG, "### 보내는 주소값 ### \n"+ data);
                        String[] address = data.split(",");
                        senderAddress1.setText(address[0]);
                        senderAddress2.setText(address[1]);
                    }
                }
                break;
        }
    }
}