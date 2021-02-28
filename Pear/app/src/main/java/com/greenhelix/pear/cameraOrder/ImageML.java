package com.greenhelix.pear.cameraOrder;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

//파이어베이스
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.greenhelix.pear.R;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class ImageML extends AppCompatActivity  {
    private static final String URI_IMAGE = "URIImage";
    private static final String LOG_TAG = "ik";

    Uri firebaseUriImage;
    TextView resultShow;
    FirebaseVisionImage image;
    String re ="";
    Button btnMLShow;
    Button btnMLBefore;
    Button btnMLNext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_order_result);
        getIntent();
        Log.d(LOG_TAG,"ImageML정상 가동되었습니다., OnCreate에 들어왔습니다.");
        resultShow = (TextView)findViewById(R.id.tv_mlResult);
        btnMLShow = (Button)findViewById(R.id.btn_show_ml_result);
        btnMLBefore = (Button)findViewById(R.id.btn_ml_before);
        btnMLNext = (Button)findViewById(R.id.btn_ml_next);
        btnMLShow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resultShow.setText(re);
            }
        });

        btnMLBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnMLNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG,"다음 버튼 눌렸습니다.");
            }
        });

        // Intent getImage = getIntent();
        firebaseUriImage = (Uri)getIntent().getParcelableExtra(URI_IMAGE);
        Log.d(LOG_TAG, "URI 이미지 수령 완료"+firebaseUriImage);

        try {
            image = FirebaseVisionImage.fromFilePath(this, firebaseUriImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 파이어베이스 텍스트인식 옵션을 주는 곳이다. 여기서 한글인식을 설정하는듯
        FirebaseVisionCloudTextRecognizerOptions txtOptions = new FirebaseVisionCloudTextRecognizerOptions.Builder()
                .setLanguageHints(Arrays.asList("ko","안녕"))
                .setModelType(2)  //DENSE_MODEL 작고 밀집된 글자를 잘 인식하는 모드이다. 큰글자는 모드 1을 쓴다.
                .build();
        // 파이어베이스 인식기를 선언해준다.
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer(txtOptions);

        Log.d(LOG_TAG,"처리중 -------->"+image+"처리중...."+detector);
        // 비트맵 이미지를 프로세스 돌리고 잘 작동하는지 확인.
        final Task<FirebaseVisionText> result = detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        Log.d(LOG_TAG,"텍스트인식기 정상가동");
                        String resultText = firebaseVisionText.getText();
                        Log.d(LOG_TAG, "전체입니다.\n"+ resultText);
                        re = resultText;
                        for(FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
                            //그래픽으로 묶어주는 모습보여준는것?
                            String text = block.getText();

//                            for (FirebaseVisionText.Line line : block.getLines()) {
//                                Log.d(LOG_TAG, "줄 :  "+line.getText());
//
//                                for (FirebaseVisionText.Element element : line.getElements()) {
//                                    Log.d(LOG_TAG, "단어 :  "+element.getText());
//                                }//요소
//                            }//라인
                        }//블록

                    }//성공시
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(LOG_TAG,"텍스트인식기 가동실패");
                            }
                        });

    }
}