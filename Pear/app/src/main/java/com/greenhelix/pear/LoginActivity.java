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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    // xml 버튼 할당
    private SignInButton signInButton;
    private Button signOutButton;
    private GoogleSignInClient client;
    private FirebaseAuth fAuth;
    private int SIGNINCODE = 1;
    Intent logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 각 변수별 할당
        signInButton = findViewById(R.id.btn_google_login);
//        signOutButton = findViewById(R.id.btn_google_logout);
        fAuth = FirebaseAuth.getInstance();
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Log.d("Log", "로그인 아웃 정상처리 현재 사용자: "+user);
        }
        else{
            Log.d("Log", "로그인 되어있습니다.");
        }

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
            Toast.makeText(LoginActivity.this,"로그인 성공", Toast.LENGTH_SHORT).show();
        }catch (ApiException e){
            FirebaseGoogleAuth(null);
            Toast.makeText(LoginActivity.this,"로그인 실패", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginActivity.this,"성공 FirebaseGoogleAuth",Toast.LENGTH_SHORT).show();
                }else{
                    updateUI(null);
                    Toast.makeText(LoginActivity.this,"실패 FirebaseGoogleAuth",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


/*   로그인 확정을 loginactivity에서 완료하고
     이쪽에서 intent에 원하는 정보를 따로 담아서 보내는 것으로 하여 화면이동을 원할히 한다.
     고객로그인의 경우 구분할 수 있는 통로역할*/
    private void updateUI(FirebaseUser fUser){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null){
            String personEmail = account.getEmail();
            Intent access = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(access);
            Log.d("Log", "현재 로그인 이메일"+ personEmail);
            finish();
        }
    }
}
