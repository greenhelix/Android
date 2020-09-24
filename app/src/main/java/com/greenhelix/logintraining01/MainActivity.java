package com.greenhelix.logintraining01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    // xml 버튼 할당
    private SignInButton signInButton;
    private GoogleSignInClient client;
    private FirebaseAuth fAuth;
    private Button signOutButton;
    private int SIGNINCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 각 변수별 할당
        signInButton = findViewById(R.id.btn_signIn);
        signOutButton = findViewById(R.id.btn_signOut);
        fAuth = FirebaseAuth.getInstance();
        final GoogleSignInOptions googleOpt = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, googleOpt);

        // 버튼 클릭 활성화
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 버튼 클릭시 활성화 메서드
                signIn();
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃버튼 클릭시 활성화 메서드
                client.signOut();
                signOutButton.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this,
                        "로그아웃@@",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void signIn(){
        //인텐트를 통해서 client정보를 결과물로 전달한다.
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, SIGNINCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGNINCODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            FirebaseGoogleAuth(acc);
            Toast.makeText(MainActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
        }catch (ApiException e){
            FirebaseGoogleAuth(null);
            Toast.makeText(MainActivity.this,"로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = fAuth.getCurrentUser();
                    updateUI(user);
                    Toast.makeText(MainActivity.this,"성성공공 FirebaseGoogleAuth",Toast.LENGTH_SHORT).show();
                }else{
                    updateUI(null);
                    Toast.makeText(MainActivity.this,"실실패패 FirebaseGoogleAuth",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser fUser){
        //로그인 성공하면 해당 계정의 정보를 가져올 수 있게 해주는 메서드
        signOutButton.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null){
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String result = "이러한 결과를 가져올 수 있다";

            Toast.makeText(MainActivity.this
                    , "이메일주소 = "+personEmail + " 계정 이름은 = "+personName + " "+result
            ,Toast.LENGTH_SHORT).show();
        }

    }
}