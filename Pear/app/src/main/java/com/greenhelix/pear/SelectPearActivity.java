package com.greenhelix.pear;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SelectPearActivity extends AppCompatActivity {
    /*배송 상품 선택 화면*/
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button pearKind1, pearKind2, pearKind3, pearKind4, pearKind5, pearKind6;
    Button pearAmount1 ,pearAmount2,pearAmount3,pearAmount4,pearAmount5,pearAmount6;
    EditText pearBox;
    Button pearBefore, pearAfter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pear_select);

    }

}
