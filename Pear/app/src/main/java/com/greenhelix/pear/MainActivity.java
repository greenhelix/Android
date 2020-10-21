package com.greenhelix.pear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnMainOrderWrite;
    Button btnMainOrderStatus;
    Button btnMainOrderStatic;
    Button btnMainOrderDirect;
    Button btnMainOrderSetting;
    Button btnMainLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main", "Mainactivity정상 oncreate되었습니다.");

        //로그아웃
        btnMainLogout =findViewById(R.id.btn_main_logout);
        btnMainLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d("LogOut","정상로그아웃 FirebaseAuth");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        //주문입력
        btnMainOrderDirect = findViewById(R.id.btn_main_order_write);
        btnMainOrderDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Order", "버튼이 정상적으로 눌렸습니다.");
                Intent account = new Intent(MainActivity.this, OrderKindActivity.class);
                startActivity(account);
                finish();
            }
        });


    }
}