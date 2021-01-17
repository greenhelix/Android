package com.greenhelix.pear.staticPear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.greenhelix.pear.R;
import com.greenhelix.pear.orderStatus.OrderStatusActivity;

public class StaticActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    Button staticBefore;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "통계화면 종료 확인");
        if(doubleBackToExitPressedOnce){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(StaticActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG," 종료 합니다.");
                    ActivityCompat.finishAffinity(StaticActivity.this);
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
        setContentView(R.layout.activity_static);

        staticBefore = findViewById(R.id.btn_staticBefore);
        staticBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}