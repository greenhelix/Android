package com.greenhelix.pear.orderStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;
import com.greenhelix.pear.SelectPearActivity;
import com.greenhelix.pear.listShow.OrderListActivity;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusPear extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference pearOrderRef = db.collection("pear_orders");

    Button m_pearKind1, m_pearKind2, m_pearKind3, m_pearKind4, m_pearKind5, m_pearKind6;
    Button m_pearAmount1 ,m_pearAmount2,m_pearAmount3,m_pearAmount4,m_pearAmount5,m_pearAmount6;
    EditText m_pearBox;
    Button m_pearBefore, m_pearAfter;
    String m_kind, m_amount, m_box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_pear);
        //선언부
        m_pearKind1 = findViewById(R.id.btn_m_pearKind1);
        m_pearKind2 = findViewById(R.id.btn_m_pearKind2);
        m_pearKind3 = findViewById(R.id.btn_m_pearKind3);
        m_pearKind4 = findViewById(R.id.btn_m_pearKind4);
        m_pearKind5 = findViewById(R.id.btn_m_pearKind5);
        m_pearKind6 = findViewById(R.id.btn_m_pearKind6);
        m_pearAmount1 = findViewById(R.id.btn_m_pearAmount1);
        m_pearAmount2 = findViewById(R.id.btn_m_pearAmount2);
        m_pearAmount3 = findViewById(R.id.btn_m_pearAmount3);
        m_pearAmount4 = findViewById(R.id.btn_m_pearAmount4);
        m_pearAmount5 = findViewById(R.id.btn_m_pearAmount5);
        m_pearAmount6 = findViewById(R.id.btn_m_pearAmount6);
        //배 종류 선택
        pickPearKind(m_pearKind1);
        pickPearKind(m_pearKind2);
        pickPearKind(m_pearKind3);
        pickPearKind(m_pearKind4);
        pickPearKind(m_pearKind5);
        pickPearKind(m_pearKind6);
        //배갯수 선택
        pickPearAmount(m_pearAmount1);
        pickPearAmount(m_pearAmount2);
        pickPearAmount(m_pearAmount3);
        pickPearAmount(m_pearAmount4);
        pickPearAmount(m_pearAmount5);
        pickPearAmount(m_pearAmount6);
        //배 상자 수 선택
        m_pearBox = findViewById(R.id.et_m_pearBox);
        //다음 버튼
        m_pearAfter = findViewById(R.id.btn_m_pearSelctAfter);

        m_pearBefore = findViewById(R.id.btn_m_pearSelctBefore);
        m_pearBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // 배의 종류를 가져오며, 버튼의 활성화 모습을 적용해준다.
    public void pickPearKind(final Button bt){
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "클릭!",Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "버튼 선택....");
                if(bt == m_pearKind1){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                    m_pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                }else if(bt == m_pearKind2){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                    m_pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                }else if(bt == m_pearKind3){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                    m_pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                }else if(bt == m_pearKind4){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                    m_pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                }else if(bt == m_pearKind5){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                    m_pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                }else if(bt == m_pearKind6) {
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color));
                    m_pearKind1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                    m_pearKind5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.pear_btn_color_off));
                }

                Log.d(LOG_TAG,bt.getText().toString()+" 종류를 선택하였습니다.");
                m_kind = bt.getText().toString();
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
                if(bt == m_pearAmount1){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    m_pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == m_pearAmount2){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    m_pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == m_pearAmount3){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    m_pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == m_pearAmount4){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    m_pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == m_pearAmount5){
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    m_pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount6.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }else if(bt == m_pearAmount6) {
                    bt.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color));
                    m_pearAmount1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount4.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                    m_pearAmount5.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.amount_btn_color_off));
                }
                Log.d(LOG_TAG,bt.getText().toString()+" 를 선택하였습니다.");
                m_amount = bt.getText().toString();
            }
        });
    }
    // 직접주문 배정보 DB 전송후 화면이동
    public void pearModifyUpdateAndPass(View v){
        String id = getIntent().getExtras().getString("id");
        Log.d(LOG_TAG, "ID: "+id+":: 정상적으로 문서 id를 가져왔습니다.");

        Map<String, Object> orderPear = new HashMap<>();
        orderPear.put("pear_kind", m_kind);
        orderPear.put("pear_amount", m_amount);
        m_box = m_pearBox.getText().toString();
        Log.d(LOG_TAG, "배송상품 정보는 "+m_kind+", "+m_amount+","+m_box+" 입니다.");
        orderPear.put("pear_box", m_box);
        orderPear.put("status", "준비 중");

        /*직접주문인 경우 *///카메라 인식인 경우 이 부분을 다르게 줘야 수정이 된다!!!
        String ORDER_DOC = "direct";

        pearOrderRef.document(id)
                .update(orderPear)
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
        Intent upCloud = new Intent(OrderStatusPear.this, OrderStatusActivity.class);
        startActivity(upCloud);

    }//pearUpdateAndPass END
}