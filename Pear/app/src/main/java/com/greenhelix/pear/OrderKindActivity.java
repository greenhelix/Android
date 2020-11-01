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
    Button btnOrderMain;
    private static final String LOG_TAG = "ik";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_kind);
        Log.d(LOG_TAG, "주문형태 입력화면 정상 OnCreate 되었습니다.");
        btnOrderDirect = findViewById(R.id.btn_order_direct);
        btnOrderCamera = findViewById(R.id.btn_order_camera);
        btnOrderMain = findViewById(R.id.btn_select_order_before);

        btnOrderDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(OrderKindActivity.this, DirectOrderActivity.class);
                startActivity(move);
                finish();
            }
        });

        btnOrderCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(OrderKindActivity.this, CameraOrderActivity.class);
                startActivity(move);
                finish();
            }
        });

    }
}
