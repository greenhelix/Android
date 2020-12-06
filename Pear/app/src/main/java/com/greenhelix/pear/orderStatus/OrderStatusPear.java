package com.greenhelix.pear.orderStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greenhelix.pear.R;

public class OrderStatusPear extends AppCompatActivity {
    Button btnStatusPearBefore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_pear);

        btnStatusPearBefore = findViewById(R.id.btn_pearSelctBefore);
        btnStatusPearBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}