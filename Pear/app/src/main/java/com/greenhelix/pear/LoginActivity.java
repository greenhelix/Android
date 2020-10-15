package com.greenhelix.pear;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private SignInButton googleLoginBtn;
    private FirebaseAuth fbAuth;
    private GoogleSignInClient googleLogInClient;
    private int SIGN_IN_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleLoginBtn = findViewById(R.id.btn_google_login);
        fbAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleLoginOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)  //최초 구글로그인에 대한 옵션을 할당해준다. 요청값은 이메일아이디
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();                                                                                   // default_web_client_id 는 어떻게 해결?
        googleLogInClient = GoogleSignIn.getClient(this, googleLoginOption);                    //변수 client를 선언해준뒤,여기 activity에서 있던 client에게 옵션을 넣어준다.
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {                                      //로그인 버튼 클릭시 이벤트 선언
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void LogIn(){
        Intent logInIntent = googleLogInClient.getSignInIntent();  //인텐트에 정보를 잠시 담고, activity를 실행하여 intent의 정보를 내가 설정한 코드와 함께보낸다.
        startActivityForResult(logInIntent, SIGN_IN_CODE);          // 이방법은 다른 방해를 막고 더 빠르게 가동시켜준다. 이렇게 하면 , onactivityResult()메서드가 자동실행됨.
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data); //task에 intent정보를 꺼내서 넣어준다.
            //checkLogInResult(task);
        }
    }
    private void checkLogInResult(Task<GoogleSignInAccount> completeTask){
        try{
            GoogleSignInAccount googleAccount = completeTask.getResult(ApiException.class);
            //FirebaseG
            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT);

        }catch (){

        }
    }
    private void FbGoogleAuth(GoogleSignInAccount account){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);//파이어베이스에서 게정 신뢰를 제공하는 값을 가져옵니다.

    }
}
