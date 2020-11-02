package com.greenhelix.pear;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhelix.pear.test.DirectOrderActivity;

/*주문 형태 선택 화면 */
public class OrderKindActivity extends AppCompatActivity {

    Button btnOrderDirect;
    Button btnOrderCamera;
    Button btnOrderBefore;
    private static final String LOG_TAG = "ik";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_kind);
        Log.d(LOG_TAG, "주문형태 입력화면 정상 OnCreate 되었습니다.");
        btnOrderDirect = findViewById(R.id.btn_order_direct);
        btnOrderCamera = findViewById(R.id.btn_order_camera);
        btnOrderBefore = findViewById(R.id.btn_select_order_before);
        
        //직접 입력
        btnOrderDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move1 = new Intent(getApplicationContext(), DirectOrderActivity.class);
                startActivity(move1);
            }
        });
        // 카메라 입력
        btnOrderCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move2 = new Intent(OrderKindActivity.this, CameraOrderActivity.class);
                startActivity(move2);
            }
        });

        btnOrderBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
