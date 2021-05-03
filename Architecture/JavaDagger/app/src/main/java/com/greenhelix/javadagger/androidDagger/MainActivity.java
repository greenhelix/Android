package com.greenhelix.javadagger.androidDagger;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.greenhelix.javadagger.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    String activityName;

    MainActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        component = ((App)getApplication()).getAppComponent()
                .mainActivityComponentBuilder()
                .setModule(new MainActivityModule())
                .setActivity(this)
                .build();
        component.inject(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MainFragment())
                .commit();
    }
    public MainActivityComponent getComponent(){
        return component;
    }
}
