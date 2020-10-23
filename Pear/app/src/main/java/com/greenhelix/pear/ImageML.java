package com.greenhelix.pear;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

//파이어베이스
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.Arrays;

public class ImageML extends AppCompatActivity implements ImageAnalysis.Analyzer {
    private static final String BIT_IMAGE = "BitmapImage";
    private static final String LOG_TAG = "ik";

    Bitmap firebaseBitmapImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent getImage = getIntent();
        firebaseBitmapImage = (Bitmap)getImage.getParcelableExtra(BIT_IMAGE);
        Log.d(LOG_TAG, "비트맵이미지 수령 완료"+firebaseBitmapImage);


        //가져온 비트맵 이미지를 파이어베이스 비전에 넣어준다.
        FirebaseVisionImage test = FirebaseVisionImage.fromBitmap(firebaseBitmapImage);
        //파이어베이스 텍스트인식 옵션을 주는 곳이다. 여기서 한글인식을 설정하는듯
        FirebaseVisionCloudTextRecognizerOptions txtOptions = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("ko","안녕"))
                .build();
        // 파이어베이스 인식기를 선언해준다.
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer(txtOptions);

        Log.d(LOG_TAG,"처리중 -------->"+test+"처리중...."+detector);
        // 비트맵 이미지를 프로세스 돌리고 잘 작동하는지 확인.
        Task<FirebaseVisionText> result = detector.processImage(test)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        Log.d(LOG_TAG,"텍스트인식기 정상가동");
                        Log.d(LOG_TAG,"전체 인식 글자 : "+ firebaseVisionText.getText());
                        for(FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                            //그래픽으로 묶어주는 모습보여준는것?
                            Rect boudingBox = block.getBoundingBox();
                            Point[] cornerPoints = block.getCornerPoints();
                            String text = block.getText();
                            Log.d(LOG_TAG, "문단 :  "+text);
                            for (FirebaseVisionText.Line line : block.getLines()) {
                                Log.d(LOG_TAG, "줄 :  "+line.getText());

                                for (FirebaseVisionText.Element element : line.getElements()) {
                                    Log.d(LOG_TAG, "단어 :  "+element.getText());
                                }//요소
                            }//라인
                        }//블록
                    }//성공시
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(LOG_TAG,"텍스트인식기 가동실패");
                            }
                        }
                );
        // 가이드에서는 바로 getText를 쓴다.
//        String resultText = result.getResult().getText();
//        for(FirebaseVisionText.TextBlock block: result.getResult().getTextBlocks())


    }
    private int degreesToFirebaseRotation(int degrees) {
        switch (degrees) {
            case 0:
                return FirebaseVisionImageMetadata.ROTATION_0;
            case 90:
                return FirebaseVisionImageMetadata.ROTATION_90;
            case 180:
                return FirebaseVisionImageMetadata.ROTATION_180;
            case 270:
                return FirebaseVisionImageMetadata.ROTATION_270;
            default:
                throw new IllegalArgumentException(
                        "Rotation must be 0, 90, 180, or 270.");
        }
    }
    @Override
    @androidx.camera.core.ExperimentalGetImage
    public void analyze(ImageProxy image, int rotationDegrees) {
        if (image == null || image.getImage() == null) {
            return;
        }
        Image mediaImage = image.getImage();
        int rotation = degreesToFirebaseRotation(rotationDegrees);
        FirebaseVisionImage fbimage =
                FirebaseVisionImage.fromMediaImage(mediaImage, rotation);
        // Pass image to an ML Vision API
        // ...
    }
}

