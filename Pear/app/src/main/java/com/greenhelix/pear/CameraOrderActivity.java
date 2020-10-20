package com.greenhelix.pear;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraOrderActivity extends AppCompatActivity {

    ImageButton btnCameraOpen;
    ImageView ivCameraImage;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_order_camera);

        btnCameraOpen.findViewById(R.id.btn_order_camera);
        btnCameraOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });
    }


    /*찍은 사진을 파일로 만들어서 디바이스 저장소에 저장하는 메서드이다. */
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /*카메라 앱을 불러서 사진 촬영한 뒤, 인텐트에 넣어둔다.*/
    /*이제 이 이미지 파일을  intent를 통해 호출할 수 있게 한다.*/
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){

            File photoFile = null;

            try{
                photoFile = createImageFile();

            }catch (IOException ex) {
                Log.e("camera", "사진을 이미지로 넣는 과정 문제"+ex);
            }

            if(photoFile != null){
//              Uri형태로 사진을 만드는데, Fileprovider를 통해서 생성한다. 해당 사진 파일을 uri로 변환하는 과정
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.greenhelix.pear",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    /*Uri로 저장된 이미지를 bitmap으로 형변환하여 imageview에 띄어준다.*/
    /*imageview는 나타나게 되고, imagebutton은 사라지게 만든다. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Log.d("camera","이미지 비트맵: "+imageBitmap);
            Log.d("camera","정상처리되었습니다.");
            btnCameraOpen.setVisibility(View.INVISIBLE);
            ivCameraImage.setVisibility(View.VISIBLE);
            ivCameraImage.setImageBitmap(imageBitmap);
        }
    }

}
