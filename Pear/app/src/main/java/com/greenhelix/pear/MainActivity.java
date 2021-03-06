package com.greenhelix.pear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.deliver.DeliverOrderActivity;
import com.greenhelix.pear.orderStatus.OrderStatusActivity;
import com.greenhelix.pear.settingPear.SettingsActivity;
import com.greenhelix.pear.staticPear.StaticActivity;
import com.skt.Tmap.TMapTapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ik";
    Button btnMainOrderWrite;
    Button btnMainOrderStatus;
    Button btnMainOrderStatic;
    Button btnMainOrderDirect;
    Button btnMainOrderSetting;
    Button btnMainChangeUser;

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "메인종료 확인");
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG," 종료 합니다.");
                ActivityCompat.finishAffinity(MainActivity.this);
                System.exit(0);
            }
        }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG,"유지합니다.");

            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "Mainactivity oncreate 되었습니다.");
        Date nowTime = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("MM월 dd일 EE요일", Locale.KOREA).format(nowTime);
        Log.d(LOG_TAG, "포맷한 날짜 = "+date);
        TextView mainDate = (TextView) findViewById(R.id.tv_main_head2);
        mainDate.setText(String.format(getString(R.string.main_head_text02),date));


        //주문입력
        btnMainOrderWrite = findViewById(R.id.btn_main_order_write);
        btnMainOrderWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "직접주문 버튼이 정상적으로 눌렸습니다.");
                Intent account = new Intent(MainActivity.this, OrderKindActivity.class);
                startActivity(account);
                finish();
            }
        });

        //주문 현황
        btnMainOrderStatus = findViewById(R.id.btn_main_order_status);
        btnMainOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "주문현황 버튼이 정상적으로 눌렸습니다.");
                Intent statusGo = new Intent(MainActivity.this, OrderStatusActivity.class);
                startActivity(statusGo);
                finish();
            }
        });

        //직접배달
        btnMainOrderDirect = findViewById(R.id.btn_main_direct);
        btnMainOrderDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "직접배달 버튼이 정상적으로 눌렸습니다.");
                Intent checkOrder = new Intent(getApplication(), DeliverOrderActivity.class);
                startActivity(checkOrder);
                finish();
            }
        });

        //통계
        btnMainOrderStatic = findViewById(R.id.btn_main_static);
        btnMainOrderStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "통계 버튼이 정상적으로 눌렸습니다.");
                startActivity(new Intent(MainActivity.this, StaticActivity.class));
            }
        });

        // user change
        btnMainChangeUser =findViewById(R.id.btn_main_change_user);
        btnMainChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
                finish();
            }
        });

        // setting
        btnMainOrderSetting = findViewById(R.id.btn_main_setting);
        btnMainOrderSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "설정 버튼이 정상적으로 눌렸습니다.");
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }// onCreate END

    // come back main screen
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume start");
    }
}