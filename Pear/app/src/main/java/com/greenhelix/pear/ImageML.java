package com.greenhelix.pear;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import java.io.IOException;
import java.util.Arrays;
//implements ImageAnalysis.Analyzer
public class ImageML extends AppCompatActivity  {
    private static final String BIT_IMAGE = "BitmapImage";
    private static final String URI_IMAGE = "URIImage";
    private static final String LOG_TAG = "ik";

//    Bitmap firebaseBitmapImage;
    Uri firebaseUriImage;
    TextView resultShow;
    FirebaseVisionImage image;
    String re;
    Button btnMLShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ml_kit_result);

        Log.d(LOG_TAG,"ImageML정상 가동되었습니다., OnCreate에 들어왔습니다.");
        resultShow = (TextView)findViewById(R.id.tv_mlResult);
        btnMLShow = (Button)findViewById(R.id.btn_show_ml_result);
        btnMLShow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "글자 잘 가져옴"+re);
                resultShow.setText(re);
            }
        });

//        Intent getImage = getIntent();
        firebaseUriImage = (Uri)getIntent().getParcelableExtra(URI_IMAGE);
        Log.d(LOG_TAG, "URI 이미지 수령 완료"+firebaseUriImage);


        //가져온 비트맵 이미지를 파이어베이스 비전에 넣어준다.
//        FirebaseVisionImage test = FirebaseVisionImage.fromFilePath(context,firebaseUriImage);

        try {
            image = FirebaseVisionImage.fromFilePath(this, firebaseUriImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //파이어베이스 텍스트인식 옵션을 주는 곳이다. 여기서 한글인식을 설정하는듯
        FirebaseVisionCloudTextRecognizerOptions txtOptions = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("ko","안녕"))
                .setModelType(2)
                .build();
        // 파이어베이스 인식기를 선언해준다.
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer(txtOptions);

        Log.d(LOG_TAG,"처리중 -------->"+image+"처리중...."+detector);
        // 비트맵 이미지를 프로세스 돌리고 잘 작동하는지 확인.
        Task<FirebaseVisionText> result = detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        Log.d(LOG_TAG,"텍스트인식기 정상가동");
                        for(FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                            //그래픽으로 묶어주는 모습보여준는것?
                            Rect boudingBox = block.getBoundingBox();
                            Point[] cornerPoints = block.getCornerPoints();
                            String text = block.getText();
                            Log.d(LOG_TAG, "문단 :  "+text);
                            re += text;
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

    }


//    private int degreesToFirebaseRotation(int degrees) {
//        switch (degrees) {
//            case 0:
//                return FirebaseVisionImageMetadata.ROTATION_0;
//            case 90:
//                return FirebaseVisionImageMetadata.ROTATION_90;
//            case 180:
//                return FirebaseVisionImageMetadata.ROTATION_180;
//            case 270:
//                return FirebaseVisionImageMetadata.ROTATION_270;
//            default:
//                throw new IllegalArgumentException(
//                        "Rotation must be 0, 90, 180, or 270.");
//        }
//    }
//    @Override
//    @androidx.camera.core.ExperimentalGetImage
//    public void analyze(ImageProxy image, int degrees) {
//        if (image == null || image.getImage() == null) {
//            return;
//        }
//        Image mediaImage = image.getImage();
//        int rotation = degreesToFirebaseRotation(degrees);
//        FirebaseVisionImage fbimage =
//                FirebaseVisionImage.fromMediaImage(mediaImage, rotation);
//    }
}

//for (FirebaseVisionText.Line line : block.getLines()) {
//                                Log.d(LOG_TAG, "줄 :  "+line.getText());
//
//                                for (FirebaseVisionText.Element element : line.getElements()) {
//                                    Log.d(LOG_TAG, "단어 :  "+element.getText());
//                                }//요소
//                            }//라인