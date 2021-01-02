package com.greenhelix.pear.directOrder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;
import com.greenhelix.pear.cloudDB.CloudStore;
import com.greenhelix.pear.selectPear.SelectPearActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DirectShow extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String ORDER_DOC = "direct";
    private FirebaseAuth fAuth;
    Button btnResultNext;
    Button btnResultBefore;
    TextView tvShow;
    ArrayList<String> senderData;
    ArrayList<String> recipientData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct_order_result);
        Log.d(LOG_TAG,"DirectShow 정상 가동되었습니다. OnCreate에 들어왔습니다.");

        tvShow = (TextView) findViewById(R.id.tv_test_show1);

        senderData = getIntent().getExtras().getStringArrayList("sender");
        recipientData = getIntent().getExtras().getStringArrayList("recipient");

        tvShow.append("주문 정보입니다.\n");
        tvShow.append("번호 : "+senderData.get(4)+"\n");
        tvShow.append("보내는 사람 \n");
        tvShow.append("보내는 사람 이름 : "+senderData.get(0)+"\n");
        tvShow.append("보내는 사람 전화번호 : "+senderData.get(1)+"-"+senderData.get(2)+"-"+senderData.get(3)+"\n");
        tvShow.append("받는 사람 우편번호 :"+senderData.get(4)+"\n");
        tvShow.append("받는 사람 주소 :"+senderData.get(5)+"\n");
        tvShow.append("받는 사람 상세주소 :"+senderData.get(6)+"\n");
        tvShow.append("================\n");
        tvShow.append("받는 사람 \n");
        tvShow.append("받는 사람 이름 :"+recipientData.get(0)+"\n");
        tvShow.append("받는 사람 전화번호 : "+recipientData.get(1)+"-"+recipientData.get(2)+"-"+recipientData.get(3)+"\n");
        tvShow.append("받는 사람 우편번호 :"+recipientData.get(4)+"\n");
        tvShow.append("받는 사람 주소 :"+recipientData.get(5)+"\n");
        tvShow.append("받는 사람 상세주소 :"+recipientData.get(6)+"\n");

        btnResultBefore = (Button) findViewById(R.id.btn_direct_result_before);
        btnResultBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnResultNext = (Button) findViewById(R.id.btn_direct_result_next);
        btnResultNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터를 Map 형태로 변환하여 넣어준다. 집주소는 ArrayList형태로 넣어주면 DB에 array형태로 들어감.
                Map<String, Object> orderInfo = new HashMap<>();
                // 보내는 사람 정보
                orderInfo.put("sender",senderData.get(0));
                orderInfo.put("sender_tel",senderData.get(1)+senderData.get(2)+senderData.get(3));
                ArrayList<String> seAdr = new ArrayList<>();
                seAdr.add(senderData.get(4));
                seAdr.add(senderData.get(5));
                seAdr.add(senderData.get(6));
                orderInfo.put("sender_addr",seAdr);
                // 받는 사람 정보
                orderInfo.put("recipient",recipientData.get(0));
                orderInfo.put("recipient_tel",recipientData.get(1)+recipientData.get(2)+recipientData.get(3));
                ArrayList<String> reAdr = new ArrayList<>();
                reAdr.add(recipientData.get(4));
                reAdr.add(recipientData.get(5));
                reAdr.add(recipientData.get(6));
                orderInfo.put("recipient_addr",reAdr);

                // 주문정보 입력자 구분
                fAuth = FirebaseAuth.getInstance();
                FirebaseUser fUser = fAuth.getCurrentUser();
                String name = "";
                if (fUser.getEmail().equals("dlrghks4444@gmail.com")){
                    name = "개발자";
                }else if( fUser.getEmail().equals("ckck7658@gmail.com")){
                    name = "우창";
                }else if( fUser.getEmail().equals("limlim7300@gmail.com")){
                    name = "유림";
                }
                orderInfo.put("user", name);


                // 문서 이름 설정을 위해 현재 시간을 원하는 형태로 문자열로 가져온다.
                String time = new SimpleDateFormat("yyMMddHHmm").format(Calendar.getInstance().getTime());

                // DB를 호출하여 원하는 컬렉션에 원하는 문서 명을 설정하여 map 데이터를 넣어준다. set이 새로 만드는 명령어
                db.collection("pear_orders").document(ORDER_DOC+time)
                        .set(orderInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "저장 성공~",Toast.LENGTH_SHORT).show();
                                Log.d(LOG_TAG, "저장완료^^");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "저장 실패!",Toast.LENGTH_SHORT).show();
                                Log.d(LOG_TAG, "저장실패ㅜㅜ");
                                e.printStackTrace();
                            }
                        });
                //인텐트
                Intent next = new Intent(DirectShow.this, SelectPearActivity.class);
                next.putExtra("index",time);
                startActivity(next);
            }
        });
    }
}
//                Intent directData = new Intent(DirectShow.this, CloudStore.class);
//                directData.putExtra("direct_sender", senderData);
//                directData.putExtra("direct_recipient", recipientData);
//                startActivity(directData);