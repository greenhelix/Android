package com.greenhelix.pear.cameraOrder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;
import com.greenhelix.pear.selectPear.SelectPearActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
1. 직접 주문
2. 카메라 주문
주문내역이 이쪽으로 모여야 함.*/

public class  CameraShow extends AppCompatActivity {

    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String ORDER_DOC = "direct";

    List<String> senderData = new ArrayList<>();
    List<String> recipientData = new ArrayList<>();
    Boolean isItCamera = false;
    Boolean isItDirect = false;
    Button goSelectPear;
    Button goBefore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        senderData = getIntent().getExtras().getStringArrayList("direct_sender");
        recipientData = getIntent().getExtras().getStringArrayList("direct_recipient");
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        if(senderData!= null) isItDirect = true;

        if(isItDirect){
            directAddRow(table);
        }
        if(isItCamera){
            cameraAddRow();
        }
        //배송물품 선택 버튼
        /*화면이동과 클라우드DB 에 해당 정보를 저장한다.*/
        goSelectPear = (Button) findViewById(R.id.btn_cloud_next);

        //이전버튼
        goBefore = (Button) findViewById(R.id.btn_cloud_befpre);
        goBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    //직접주문입력 화면, 주문 테이블 생성
    public void directAddRow(TableLayout t){
        // 체크박수 중앙 위치 방법 까다로워서 그냥 다 왼쪽정렬시킴.
        TableRow row = new TableRow(this);

        TextView index = new TextView(this);
        index.setText(String.valueOf(1));
        index.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        row.addView(index);

        TextView recipient = new TextView(this);
        recipient.setText(recipientData.get(0));
        recipient.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        row.addView(recipient);

        TextView sender = new TextView(this);
        sender.setText(senderData.get(0));
        sender.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        row.addView(sender);

        addCheck(row);


        t.addView(row);
    }
    //체크박스 추가
    public void addCheck(TableRow row){
        CheckBox check = new CheckBox(this);
        row.addView(check);
    }
    //카메라 입력시 로직 공간
    public void cameraAddRow(){

    }
    /*화면이동과 클라우드DB 에 해당 정보를 저장한다.*/
    public void saveAndPass(View v){
        //cloud DB save - document생성 (물품은 비어있는값),(주문자,수령인 정보는 저장)
        //SenderData. cameraData -> arrayList [0,1,2,3][0,1,2,3,4,5,6]
        Map<String, Object> orderInfo = new HashMap<>();
        orderInfo.put("sender",senderData.get(0)); //그냥들어가는지 String변환해야하는지 확인 완료.
        orderInfo.put("sender_tel",senderData.get(1)+senderData.get(2)+senderData.get(3));
        orderInfo.put("recipient",recipientData.get(0));
        orderInfo.put("recipient_tel",recipientData.get(1)+recipientData.get(2)+recipientData.get(3));
        ArrayList<String> reAdr = new ArrayList<>();
        reAdr.add(recipientData.get(4));
        reAdr.add(recipientData.get(5));
        reAdr.add(recipientData.get(6));
        orderInfo.put("recipient_addr",reAdr);
        GoogleSignInAccount userInfo = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        Log.d(LOG_TAG, "유저정보? :: "+ userInfo + "\n toString())) "+ userInfo.toString());

        orderInfo.put("user",  FirebaseAuth.getInstance().getUid());
        String time = new SimpleDateFormat("yyMMddHHmm").format(Calendar.getInstance().getTime());
        db.collection("pear_orders").document(ORDER_DOC+time)
                .set(orderInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "저장성공!",Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "저장이 되었네요^^");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "실패실패!!!",Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "저장이 안되었네요!!");
                        e.printStackTrace();
                    }
                });
        //인텐트
        Intent next = new Intent(CameraShow.this, SelectPearActivity.class);
        next.putExtra("index",time);
        startActivity(next);
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "카메라 정보확인 화면 종료 확인");
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraShow.this);
        builder.setTitle("알림");
        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG," 종료 합니다.");
                ActivityCompat.finishAffinity(CameraShow.this);
            }
        }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG,"유지합니다.");

            }
        }).show();
    }
}
