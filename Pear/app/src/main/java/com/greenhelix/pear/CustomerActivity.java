package com.greenhelix.pear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.greenhelix.pear.orderStatus.OrderStatusActivity;
import com.greenhelix.pear.settingPear.SettingsActivity;

public class CustomerActivity extends AppCompatActivity {
    Button btnCustomerSetting, btnCustomerChangeUser, btnCustomerOrderWrite;
    boolean doubleBackToExitPressedOnce = false;
    private static final String LOG_TAG = "ik";
    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "주문현황 종료 확인");
        if(doubleBackToExitPressedOnce){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CustomerActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG," 종료 합니다.");
                    ActivityCompat.finishAffinity(CustomerActivity.this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);



        // user change
        btnCustomerChangeUser= findViewById(R.id.btn_customer_change_user);
        btnCustomerChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CustomerActivity.this , LoginActivity.class));
                finish();
            }
        });

        // setting
        btnCustomerSetting = findViewById(R.id.btn_customer_setting);
        btnCustomerSetting.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(LOG_TAG, "고객 설정 버튼이 정상적으로 눌렸습니다.");
            startActivity(new Intent(CustomerActivity.this, SettingsActivity.class));
        }
    });
    }// onCreate END

    //come back customer main screen
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume start");
    }
}
