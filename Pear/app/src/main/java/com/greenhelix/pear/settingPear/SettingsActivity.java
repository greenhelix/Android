package com.greenhelix.pear.settingPear;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.greenhelix.pear.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    Button settingBefore;
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