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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.listShow.OrderListActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectPearActivity extends AppCompatActivity {
    /*배송 상품 선택 화면*/
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button pearKind1, pearKind2, pearKind3, pearKind4, pearKind5, pearKind6;
    Button pearAmount1 ,pearAmount2,pearAmount3,pearAmount4,pearAmount5,pearAmount6;
    EditText pearBox;
    Button pearBefore, pearAfter;

    /*Intent를 통해서 배의 종류와 갯수를 onCreate에서 담은뒤, 다음 버튼 클릭시 상자갯수도 담아서 보낸다.*/
    final Intent upCloud = new Intent(SelectPearActivity.this, OrderListActivity.class);

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
        pearBefore = findViewById(R.id.btn_pearSelctBefore);
        pearBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pearAfter = findViewById(R.id.btn_pearSelctAfter);
        pearAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent에 상자갯수 담기
                upCloud.putExtra("pearBoxes", pearBox.getText().toString());
                Toast.makeText(getApplicationContext(), "배종류와 갯수만큼 "+pearBox+"상자를 담았습니다.",Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "배 종류, 갯수, 상자수를 담았습니다.");
                startActivity(upCloud);
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
                    // Intent에 배 종류 정보 담기
                    upCloud.putExtra("pearKind",bt.getText().toString());
                    Log.d(LOG_TAG,bt.getText().toString()+" 종류를 선택하였습니다.");
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
                // Intent에 정보 배 갯수 담기
                upCloud.putExtra("pearAmound",bt.getText().toString());
                Log.d(LOG_TAG,bt.getText().toString()+" 를 선택하였습니다.");
            }
        });
    }
}
