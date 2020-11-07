package com.greenhelix.pear;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SelectPearActivity extends AppCompatActivity {
    /*배송 상품 선택 화면*/
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button pearKind1, pearKind2, pearKind3, pearKind4, pearKind5, pearKind6;
    Button pearAmount1 ,pearAmount2,pearAmount3,pearAmount4,pearAmount5,pearAmount6;
    NumberPicker pearBox;
    Button pearBefore, pearAfter;
    Boolean btnSelect = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pear_select);
        pearKind1 = findViewById(R.id.btn_pearKind1);
        pearKind2 = findViewById(R.id.btn_pearKind2);
        pearKind3 = findViewById(R.id.btn_pearKind3);
        pearKind4 = findViewById(R.id.btn_pearKind4);
        pearKind5 = findViewById(R.id.btn_pearKind5);
        pearKind6 = findViewById(R.id.btn_pearKind6);
        pearBox = findViewById(R.id.np_pearBox);
        pearBox.setMaxValue(100);
        pearBox.setMinValue(0);
        pearBox.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String box = String.valueOf(newVal);

            }
        });

    }

}
