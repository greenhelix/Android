package com.greenhelix.pear;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.greenhelix.pear.cameraOrder.CameraOrderActivity;
import com.greenhelix.pear.directOrder.DirectRecipientActivity;
import com.greenhelix.pear.directOrder.DirectSenderActivity;
import com.greenhelix.pear.orderStatus.OrderStatusActivity;

/*주문 형태 선택 화면 */
public class OrderKindActivity extends AppCompatActivity {

    Button btnOrderDirect;
    Button btnOrderCamera;
    Button btnOrderBefore;
    private static final String LOG_TAG = "ik";

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "주문형태 선택 종료 확인");
        if(doubleBackToExitPressedOnce){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OrderKindActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG," 종료 합니다.");
                    ActivityCompat.finishAffinity(OrderKindActivity.this);
                }
            }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG,"유지합니다.");

                }
            }).show();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "뒤로버튼을 한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

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
                Intent move1 = new Intent(getApplicationContext(), DirectSenderActivity.class);
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
        // 이전
        btnOrderBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMain = new Intent(getApplication(), MainActivity.class);
                goMain.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(goMain);
                finish();
            }
        });

    }
}
