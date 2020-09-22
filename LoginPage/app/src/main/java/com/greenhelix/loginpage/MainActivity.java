package com.greenhelix.loginpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private Button btnSignOut;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();
        btnSignOut = findViewById(R.id.sign_out_button);

        // 구글 계정 옵션을 사용할 수 있는 변수를 만들어준다. 요구사항은 requestEmail로 이메일을 받는다.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // 옵션 가용하게 한뒤, client라는 계정 변수를 선언하여 해당 옵션이 가능하도록 변수를 하나 만들어준다.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클라이언트 계정 자체에 로그아웃 함수가 있어서 사용하면된다.
                mGoogleSignInClient.signOut();
                Toast.makeText(MainActivity.this
                        , "당신은 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                // 해당 버튼이 로그인 안되어있을 경우에는 안보이게 해둔다.
                btnSignOut.setVisibility(View.INVISIBLE);
            }
        });
    }
    // 로그인 버튼을 눌렀을 경우 intent를 통해서 정보를 전달하고 전체적인 로그인 성공 여부 작업을 시작하는 부분이다.
    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        //https://developer.android.com/reference/android/app/Activity#startActivityForResult(android.content.Intent,%20int,%20android.os.Bundle)
    }

    //https://developer.android.com/reference/android/app/Activity#onActivityResult(int,%20int,%20android.content.Intent)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // 해당 task를 구글 계정 서비스에서 다루기 위해서 검증하는 곳이다. 예외처리를 담당하는 부분
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this
            , "로그인 성공", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }catch (ApiException e){
            Toast.makeText(MainActivity.this
            , "로그인 실패", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    // 파이어베이스 해당 이메일 로그인 서비스 데이터베이스에 정보를 업로드 한다.
    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this
                            , "성공", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }else {
                    Toast.makeText(MainActivity.this
                            , "실패", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }
    // 확인된 업데이트 계정 정보를 가져와서 UI상에 원하는 값을 띄울 수 있다.
    private void updateUI(FirebaseUser fUser){
        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Toast.makeText(MainActivity.this, personName+ personEmail + personFamilyName, Toast.LENGTH_SHORT).show();
        }
    }
}