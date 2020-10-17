package com.greenhelix.pear;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class OrderKindActivity extends AppCompatActivity {

    Button btnOrderDirect;
    Button btnOrderCamera;
    Button btnOrderMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_order_kind);

        btnOrderCamera = findViewById(R.id.btn_order_camera);
        btnOrderDirect = findViewById(R.id.btn_order_direct);
        btnOrderMain = findViewById(R.id.btn_select_order_before);




    }
}
