package com.greenhelix.pear;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private GoogleSignInClient client;
    private GoogleSignInAccount acc;
    private FirebaseAuth fAuth;
    private int SIGNINCODE = 1;
    private static final String LOG_TAG = "ik";
    private AuthCredential authCredential;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "로그인화면 종료 확인");
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("알림");
        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG," 종료 합니다.");
                ActivityCompat.finishAffinity(LoginActivity.this);
                System.exit(0);
            }
        }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG,"유지합니다.");

            }
        }).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(LOG_TAG, "onCreate start");
        signInButton = findViewById(R.id.btn_google_login);
        fAuth = FirebaseAuth.getInstance();

        final GoogleSignInOptions googleOpt = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, googleOpt);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        Button signOutButton = findViewById(R.id.btn_logout);

    } //onCreate END

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop start");
    }

    private void signIn(){
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, SIGNINCODE);
    }

    public void signOut(View v){
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "로그인 정보 초기화 완료 \n 로그인 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
                        Log.d(LOG_TAG, "다른 계정으로 로그인가능.");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGNINCODE){
            Log.d(LOG_TAG, "1. signIn start");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        Log.d(LOG_TAG, "2. handleSignInResult start");
        try{
            acc = completedTask.getResult(ApiException.class);
            if (acc != null) {
                Log.d(LOG_TAG, "구글 로그인 pass");
                FirebaseGoogleAuth(acc);
            }
            Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
        }catch (ApiException e){
            Log.d(LOG_TAG, "구글 로그인 fail"+e.toString());
            FirebaseGoogleAuth(null);
            Toast.makeText(LoginActivity.this,"로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        Log.d(LOG_TAG, "3. FirebaseGoogleAuth start");
        if(acct == null){
            Log.d(LOG_TAG, "로그인정보가 없습니다.");
        }else {
            authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            fAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = fAuth.getCurrentUser();
                        updateUI(user);
                        Log.d(LOG_TAG, "파이어베이스 인증 성공");
                    } else {
                        updateUI(null);
                        Log.d(LOG_TAG, "파이어베이스 인증 실패");
                    }
                }
            });
        }
    }

    private void updateUI(FirebaseUser fUser){
        Log.d(LOG_TAG, "4. updateUI start");
        if (fUser != null){
            final String personEmail = fUser.getEmail();
            Log.d(LOG_TAG, "현재 이메일 ::"+personEmail+"\n IdToken::"+ fUser.getUid());
            //로그인 된 계정이 운영자 컬렉션안의 문서에 해당되면 운영자 화면으로 이동한다.
            db.collection("manager").document("admin")
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (personEmail != null) {
                        if(personEmail.equals(documentSnapshot.get("admin1"))){
                            Log.d(LOG_TAG, "이메일 확인완료 개발자 입니다.");
                            Toast.makeText(LoginActivity.this,"관리자", Toast.LENGTH_SHORT).show();
                            Intent access = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(access);
                            finish();
                        }else if(personEmail.equals(documentSnapshot.get("admin2"))){
                            Log.d(LOG_TAG, "이메일 확인완료 관리자1 입니다.");
                            Intent access = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(access);
                            finish();
                        }else if(personEmail.equals(documentSnapshot.get("admin3"))){
                            Log.d(LOG_TAG, "이메일 확인완료 관리자2 입니다.");
                            Intent access = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(access);
                            finish();
                        }else if(personEmail.equals(documentSnapshot.get("admin4"))){
                            Log.d(LOG_TAG, "이메일 확인완료 관리자3 입니다.");
                            Intent access = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(access);
                            finish();
                        }else if(personEmail.equals(documentSnapshot.get("admin5"))){
                            Log.d(LOG_TAG, "이메일 확인완료 관리자4 입니다.");
                            Intent access = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(access);
                            finish();
                        }else{
                            Log.d(LOG_TAG, "고객 입니다.");
                            Toast.makeText(LoginActivity.this,"고객", Toast.LENGTH_SHORT).show();
                            Intent access = new Intent(LoginActivity.this, CustomerActivity.class);
                            startActivity(access);
                            finish();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                //관리자 계정이 아니라면 고객이니, 고객화면으로 이동해주면된다.
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(LOG_TAG, "관리자 접근 취소");
                    Toast.makeText(getApplicationContext(), "구글 로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

