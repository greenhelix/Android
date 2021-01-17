package com.greenhelix.pear.settingPear;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.greenhelix.pear.R;
import com.greenhelix.pear.orderStatus.OrderStatusActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    Button settingBefore;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "설정화면 종료 확인");
        if(doubleBackToExitPressedOnce){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(SettingsActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG," 종료 합니다.");
                    ActivityCompat.finishAffinity(SettingsActivity.this);
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
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        settingBefore = findViewById(R.id.btn_settingBefore);
        settingBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        private FirebaseAuth fAuth;

        //Preference = 환경 설정
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Log.d(LOG_TAG, "onCreatePreferences");
            fAuth = FirebaseAuth.getInstance();
            FirebaseUser fUser = fAuth.getCurrentUser();
            Preference loginPreference =findPreference("signature");
            if(loginPreference != null){
                loginPreference.setSummary(fUser.getEmail());
            }
            Preference rankPreference = findPreference("rank");
            if(rankPreference != null && fUser.getEmail().equals("dlrghks4444@gmail.com")){
                rankPreference.setSummary("개발자");
            }else if(rankPreference != null && fUser.getEmail().equals("ckck7658@gmail.com")){
                rankPreference.setSummary("관리자(우창^^)");
            }else if(rankPreference != null && fUser.getEmail().equals("limlim7300@gmail.com")){
                rankPreference.setSummary("관리자(유림^^)");
            }

        }
    }
}