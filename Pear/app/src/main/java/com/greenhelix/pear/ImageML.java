package com.greenhelix.pear;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageML extends AppCompatActivity {
    private static final String BIT_IMAGE = "BitmapImage";
    private static final String LOG_TAG = "ik";

    Bitmap firebaseBitmapImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent getImage = getIntent();
        firebaseBitmapImage = (Bitmap)getImage.getParcelableExtra(BIT_IMAGE);
        Log.d(LOG_TAG, "비트맵이미지 수령 완료"+firebaseBitmapImage);


    }

}

