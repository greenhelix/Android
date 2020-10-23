package com.greenhelix.pear;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraOrderActivity extends AppCompatActivity {

    ImageButton btnCameraOpen;
    ImageView ivCameraImage;
    Button btnCameraNext;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String LOG_TAG = "ik";
    private static final String BIT_IMAGE = "BitmapImage";
    Bitmap imageBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_camera);

        //선언부분이 이런형태야 된다. 이상한데 까먹지말자. (형변환?해줘야하는듯?)
        ivCameraImage = (ImageView) findViewById(R.id.iv_order_camera_image);
        btnCameraOpen = (ImageButton) findViewById(R.id.btn_camera);
        btnCameraNext = findViewById(R.id.btn_order_camera_next);

        btnCameraOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카메라 권한 허가 부분!! 꼭 잊어버리지말자.
                int permissionCheck = ContextCompat.checkSelfPermission
                        (CameraOrderActivity.this, Manifest.permission.CAMERA);
                if(permissionCheck == PackageManager.PERMISSION_DENIED){
                    //권한이 없다면!
                    ActivityCompat.requestPermissions
                            (CameraOrderActivity.this, new String[]{Manifest.permission.CAMERA},0);
                }else{
                    dispatchTakePictureIntent();
                }
            }
        });

        btnCameraNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent imageSend = new Intent(CameraOrderActivity.this, ImageML.class);
                imageSend.putExtra(BIT_IMAGE,imageBitmap);
                startActivity(imageSend);
            }
        });

    }

    //권한 여부 확인하고 이 오버라이드 메서드로 권한 부여 물어본다.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            if(grantResults[0] == 0){
                Toast.makeText(this, "카메라 권한 승인",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "카메라 권한 거절",Toast.LENGTH_SHORT).show();
            }
        }
    }

//카메라권한이 있는 경우 카메라를 불러서 사진을 찍는다. 그리고 인텐트를 통해서 사진정보를 onActivityResult으로 보낸다.
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

//    Uri로 저장된 이미지를 bitmap으로 형변환하여 imageview에 띄어준다.
//    imageview는 나타나게 되고, imagebutton은 사라지게 만든다.
//    위의 사진을 찍은 메서드가 완료되면 이쪽으로 사진 정보를 보내서 변환시킨다. 원하는 형태로 바꾸는 부분이다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            Log.d(LOG_TAG,"이미지 비트맵: "+imageBitmap.toString());
            Log.d(LOG_TAG,"정상처리되었습니다.");
            btnCameraOpen.setVisibility(View.INVISIBLE);
            ivCameraImage.setVisibility(View.VISIBLE);
            ivCameraImage.setImageBitmap(imageBitmap);
        }
    }

    /*찍은 사진을 파일로 만들어서 디바이스 저장소에 저장하는 메서드이다. */
//    static final int REQUEST_TAKE_PHOTO = 1;
//    String currentPhotoPath;
//    private File createImageFile() throws IOException{
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
//        String imageFileName = "JPEG_"+timeStamp+"_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,
//                ".jpg",
//                storageDir
//        );
//
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

}
