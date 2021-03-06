package com.greenhelix.pear.cameraOrder;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraOrderActivity extends AppCompatActivity {

    ImageButton btnCameraOpen;
    ImageView ivCameraImage;
    Button btnCameraNext;
    Button btnCameraBefore;
    Uri photoURI;
    String currentPhotoPath; 
    static final int REQUEST_TAKE_PHOTO = 2;
    private static final String LOG_TAG = "ik";
    private static final String URI_IMAGE = "URIImage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_camera);

        //선언부분이 이런형태야 된다. 이상한데 까먹지말자. (형변환?해줘야하는듯?)
        ivCameraImage = (ImageView)findViewById(R.id.iv_order_camera_image);
        btnCameraOpen = (ImageButton)findViewById(R.id.btn_camera);
        btnCameraNext = (Button)findViewById(R.id.btn_order_camera_next);
        btnCameraBefore = (Button)findViewById(R.id.btn_order_camera_before);

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
                imageSend.putExtra(URI_IMAGE, photoURI);
                startActivity(imageSend);
            }
        });

        btnCameraBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

//    Uri로 저장된 이미지를 bitmap으로 형변환하여 imageview에 띄어준다.
//    imageview는 나타나게 되고, imagebutton은 사라지게 만든다.
//    위의 사진을 찍은 메서드가 완료되면 이쪽으로 사진 정보를 보내서 변환시킨다. 원하는 형태로 바꾸는 부분이다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            Log.d(LOG_TAG,"이미지 URI: "+photoURI.toString());
            Log.d(LOG_TAG,"정상처리되었습니다.");
            btnCameraOpen.setVisibility(View.INVISIBLE);
            ivCameraImage.setVisibility(View.VISIBLE);
            ivCameraImage.setImageURI(photoURI);
        }
    }

    // 카메라로 찍은 사진을 파일로 만든다.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String imageFileName = "텍스트인식" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,/* prefix */
                ".jpg",/* suffix */
                storageDir/* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // 카메라로 찎은 사진을 File메서드를 통해서 파일로 만든다.
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d(LOG_TAG,"dispatchTakePictureIntent에서 IOException 발생했습니다.");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.greenhelix.pear.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "카메라주문입력 화면 종료 확인");
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraOrderActivity.this);
        builder.setTitle("알림");
        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG," 종료 합니다.");
                ActivityCompat.finishAffinity(CameraOrderActivity.this);
            }
        }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG,"유지합니다.");

            }
        }).show();
    }
}