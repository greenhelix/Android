package com.greenhelix.pear;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    // xml 버튼 할당
    private SignInButton signInButton;
    private Button signOutButton;
    private GoogleSignInClient client;
    private GoogleSignInAccount acc;
    private FirebaseAuth fAuth;
    private int SIGNINCODE = 1;
    private static final String LOG_TAG = "ik";
    private AuthCredential authCredential;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(LOG_TAG, "onCreate start");
        // 각 변수별 할당
        signInButton = findViewById(R.id.btn_google_login);
        //파이어베이스
        fAuth = FirebaseAuth.getInstance();

        // request에서 default_web_client_id 이부분 직접 실행해야 나옴.
        final GoogleSignInOptions googleOpt = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, googleOpt);

        // 버튼 클릭 활성화g
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 버튼 클릭시 활성화 메서드
                signIn();
            }
        });
        signOutButton = findViewById(R.id.btn_logout);

    } //onCreate END

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume start");
    }

    private void signIn(){
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, SIGNINCODE);
    }

    public void signOut(View v){
        //구글 로그인 계정 아예 제거하는 메서드임
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
                FirebaseGoogleAuth(acc);
            }
            Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
        }catch (ApiException e){
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
                        Toast.makeText(LoginActivity.this, "FirebaseGoogleAuth", Toast.LENGTH_SHORT).show();
                    } else {
                        updateUI(null);
                        Toast.makeText(LoginActivity.this, "실패 FirebaseGoogleAuth", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


/*   로그인 확정을 loginactivity에서 완료하고
     이쪽에서 intent에 원하는 정보를 따로 담아서 보내는 것으로 하여 화면이동을 원할히 한다.
     고객로그인의 경우 구분할 수 있는 통로역할*/
    private void updateUI(FirebaseUser fUser){
        Log.d(LOG_TAG, "4. updateUI start");
        if (fUser != null){
            final String personEmail = fUser.getEmail();
            Log.d(LOG_TAG, "현재 이메일 ::"+personEmail+"\n IdToken::"+ fUser.getUid());
            //로그인 된 계정이 운영자 컬렉션안의 문서에 해당되면 운영자 화면으로 이동한다.
            db.collection("manager").document("manager1")
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (personEmail != null) {
                        //관리자는 많이 없으니 이걸로 관리한다.
                        if(personEmail.equals(documentSnapshot.get("admin1"))){
                            Log.d(LOG_TAG, "이메일확인완료 관리자1입니다.");
                            Intent access = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(access);
                            finish();
                        }else if(personEmail.equals(documentSnapshot.get("admin2"))){
                            Log.d(LOG_TAG, "이메일확인완료 관리자2입니다.");
                            Intent access = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(access);
                            finish();
                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(LOG_TAG, "틀렸습니다. 로그인실패");
                }
            });

//            db.collection("customer").document("user1")
//                    .update("email", personEmail)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d(LOG_TAG, "이메일확인:: "+personEmail+ "\n 고객입니다.");
//                            Intent access = new Intent(LoginActivity.this, CustomerActivity.class);
//                            startActivity(access);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(LOG_TAG, "고객 등록 실패");
//                }
//            });
        }
    }

}

