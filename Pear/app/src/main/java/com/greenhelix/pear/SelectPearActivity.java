package com.greenhelix.pear;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.cloudDB.CloudStore;
import com.greenhelix.pear.listShow.OrderListActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPearActivity extends AppCompatActivity {
    /*배송 상품 선택 화면*/
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference pearOrderRef = db.collection("pear_orders");
//    private DocumentReference ordersRef = db.collection("pear_orders");
    private static final String ORDER_DOC = "direct";
    Button pearKind1, pearKind2, pearKind3, pearKind4, pearKind5, pearKind6;
    Button pearAmount1 ,pearAmount2,pearAmount3,pearAmount4,pearAmount5,pearAmount6;
    EditText pearBox;
    Button pearBefore, pearAfter;
    String kind, amount, box;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pear_select);
        //선언부
        pearKind1 = findViewById(R.id.btn_pearKind1);
        pearKind2 = findViewById(R.id.btn_pearKind2);
        pearKind3 = findViewById(R.id.btn_pearKind3);
        pearKind4 = findViewById(R.id.btn_pearKind4);
        pearKind5 = findViewById(R.id.btn_pearKind5);
        pearKind6 = findViewById(R.id.btn_pearKind6);
        pearAmount1 = findViewById(R.id.btn_pearAmount1);
        pearAmount2 = findViewById(R.id.btn_pearAmount2);
        pearAmount3 = findViewById(R.id.btn_pearAmount3);
        pearAmount4 = findViewById(R.id.btn_pearAmount4);
        pearAmount5 = findViewById(R.id.btn_pearAmount5);
        pearAmount6 = findViewById(R.id.btn_pearAmount6);

        //배 종류 선택
        pickPearKind(pearKind1);
        pickPearKind(pearKind2);
        pickPearKind(pearKind3);
        pickPearKind(pearKind4);
        pickPearKind(pearKind5);
        pickPearKind(pearKind6);
        //배갯수 선택
        pickPearAmount(pearAmount1);
        pickPearAmount(pearAmount2);
        pickPearAmount(pearAmount3);
        pickPearAmount(pearAmount4);
        pickPearAmount(pearAmount5);
        pickPearAmount(pearAmount6);

        //배 상자 수 선택
        pearBox = findViewById(R.id.et_pearBox);
        //이전 버튼
        pearBefore = findViewById(R.id.btn_pearSelctBefore);
        pearBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //다음 버튼
        pearAfter = findViewById(R.id.btn_pearSelctAfter);
    }// onCreate END

    // 배의 종류를 가져오며, 버튼의 활성화 모습을 적용해준다.
    public void pickPearKind(final Button bt){
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "클릭!",Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, "버튼 선택....");
                    if(bt == pearKind1){
                        bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                        pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    }else if(bt == pearKind2){
                        bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                        pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    }else if(bt == pearKind3){
                        bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                        pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    }else if(bt == pearKind4){
                        bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                        pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    }else if(bt == pearKind5){
                        bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                        pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    }else if(bt == pearKind6) {
                        bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                        pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                        pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    }

                    Log.d(LOG_TAG,bt.getText().toString()+" 종류를 선택하였습니다.");
                    kind = bt.getText().toString();
                }
            });
    }
    // 배의 갯수를 가져오며, 버튼의 활성화 모습을 적용해준다.
    public void pickPearAmount(final Button bt){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "클릭!",Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "버튼 선택....");
                if(bt == pearAmount1){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == pearAmount2){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == pearAmount3){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == pearAmount4){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == pearAmount5){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == pearAmount6) {
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }
                Log.d(LOG_TAG,bt.getText().toString()+" 를 선택하였습니다.");
                amount = bt.getText().toString();
            }
        });
    }

    // 직접주문 배정보 DB 전송후 화면이동
    public void pearUpdateAndPass(View v){
        String id = getIntent().getExtras().getString("index");
        Log.d(LOG_TAG, "ID: "+id+":: 정상적으로 문서 id를 가져왔습니다.");

        Map<String, Object> orderPear = new HashMap<>();
        orderPear.put("pear_kind", kind);
        orderPear.put("pear_amount", amount);
        box = pearBox.getText().toString();
        Log.d(LOG_TAG, "배송상품 정보는 "+kind+", "+amount+","+box+" 입니다.");
        orderPear.put("pear_box", box);
        orderPear.put("status", "준비중");
        pearOrderRef.document(ORDER_DOC+id).update(orderPear)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "저장성공!",Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "~~~~~~저장이 되었네요^^");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "실패실패!!!",Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "!!!!!!!!!저장이 안되었네요!!");
                        e.printStackTrace();
                    }
                });

        //upload 완료
        Log.d(LOG_TAG, "DB에 업로드 하였습니다. 화면을 넘어가겠습니다.");

        //화면이동
        Intent upCloud = new Intent(SelectPearActivity.this, OrderListActivity.class);
        startActivity(upCloud);

    }//pearUpdateAndPass END
}//END
