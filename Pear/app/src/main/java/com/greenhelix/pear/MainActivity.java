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
import com.greenhelix.pear.orderStatus.OrderStatusActivity;
import com.greenhelix.pear.settingPear.SettingsActivity;
import com.greenhelix.pear.staticPear.StaticActivity;
import com.skt.Tmap.TMapTapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnMainOrderWrite;
    Button btnMainOrderStatus;
    Button btnMainOrderStatic;
    Button btnMainOrderDirect;
    Button btnMainOrderSetting;
    Button btnMainChangeUser;
    private static final String LOG_TAG = "ik";

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
                ActivityCompat.finishAffinity(MainActivity.this); // 해당 액티비티 종료
                System.exit(0); //앱 완전 종료
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
        final TMapTapi tmaptapi = new TMapTapi(this);
        tmaptapi.setSKTMapAuthentication ("l7xx67178473a0134850bb0610927c9ba539");

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
                //티맵 패키지명을 가져와서, 인텐트를 통해 해당 앱을 켜준다.
                try{
                    Intent tmapOpen = getPackageManager().getLaunchIntentForPackage("com.skt.tmap.ku");
                    tmapOpen.setAction(Intent.ACTION_SEND);
                    tmapOpen.putExtra(Intent.EXTRA_TEXT, tmaptapi.invokeRoute("혁규농원",0,0));
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
        // 주문정보를 확인해서, 선택시, 해당 주소를 가져와서 위도와 경도 구해오는 스니펫
        // 이것을 이용해서, tmap으로 해당 주소와 위도 경도를 보내면, 경로 탐색을 시작한다.
        /*Geocoder g = new Geocoder(this);
            try{List<Address> adr = null;
            adr = g.getFromLocationName(recipientData.get(5),1);
            Log.d(LOG_TAG, "this : "+adr.toString());
            }catch (IOException e){
            Log.d(LOG_TAG, "except error IO");
            }*/

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