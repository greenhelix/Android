package com.greenhelix.pear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.skt.Tmap.TMapTapi;

public class MainActivity extends AppCompatActivity {

    Button btnMainOrderWrite;
    Button btnMainOrderStatus;
    Button btnMainOrderStatic;
    Button btnMainOrderDirect;
    Button btnMainOrderSetting;
    Button btnMainLogout;
    private static final String LOG_TAG = "ik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Mainactivity oncreate 되었습니다.");

        final TMapTapi tmaptapi = new TMapTapi(this);
        tmaptapi.setSKTMapAuthentication ("l7xx67178473a0134850bb0610927c9ba539");

        //로그아웃
        btnMainLogout =findViewById(R.id.btn_main_logout);
        btnMainLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d(LOG_TAG,"로그아웃 버튼이 정상적으로 눌렸습니다.");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

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

        //직접배달
        btnMainOrderDirect = findViewById(R.id.btn_main_direct);
        btnMainOrderDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "직접배달 버튼이 정상적으로 눌렸습니다.");
                //티맵 패키지명을 가져와서, 인텐트를 통해 해당 앱을 켜준다.
                try{
                    Intent tmapOpen = getPackageManager().getLaunchIntentForPackage("com.skt.tmap.ku");
                    tmapOpen.setAction(Intent.ACTION_SEND);
                    tmapOpen.putExtra(Intent.EXTRA_TEXT, tmaptapi.invokeSearchPortal("혁규농원"));
                    tmapOpen.setType("text/*");
                    Intent shareIntent = Intent.createChooser(tmapOpen, null);
                    startActivity(shareIntent);


                }catch (Exception e){
                    String url = "market://details?id=com.skt.tmap.ku";
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
            }
        });



    }
}