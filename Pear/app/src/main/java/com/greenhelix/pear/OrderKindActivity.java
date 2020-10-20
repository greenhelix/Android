package com.greenhelix.pear;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
/*주문 형태 선택 화면 */
public class OrderKindActivity extends AppCompatActivity {

    Button btnOrderDirect;
    Button btnOrderCamera;
    Button btnOrderMain;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_order_kind);

        btnOrderDirect = findViewById(R.id.btn_order_direct);
        btnOrderCamera = findViewById(R.id.btn_order_camera);
        btnOrderMain = findViewById(R.id.btn_select_order_before);

        btnOrderDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplicationContext(), DirectOrderActivity.class);
                startActivity(move);
                finish();
            }
        });

        btnOrderCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplicationContext(), CameraOrderActivity.class);
                startActivity(move);
                finish();
            }
        });

    }
}
