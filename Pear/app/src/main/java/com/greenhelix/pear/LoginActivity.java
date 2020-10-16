//package com.greenhelix.pear;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//
//
//public class LoginActivity extends AppCompatActivity {
//
//    private SignInButton googleLoginBtn;
//    private FirebaseAuth fbAuth;
//    private GoogleSignInClient googleLogInClient;
//    private int SIGN_IN_CODE = 1;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        googleLoginBtn = findViewById(R.id.btn_google_login);
//        fbAuth = FirebaseAuth.getInstance();
//
//        GoogleSignInOptions googleLoginOption = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)  //최초 구글로그인에 대한 옵션을 할당해준다. 요청값은 이메일아이디
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();                                                                                   // default_web_client_id 는 어떻게 해결?
//        googleLogInClient = GoogleSignIn.getClient(this, googleLoginOption);                    //변수 client를 선언해준뒤,여기 activity에서 있던 client에게 옵션을 넣어준다.
//        //최종 로그인 버튼 클릭 이벤트가 활성화 되는 부분이다.
//        googleLoginBtn.setOnClickListener(new View.OnClickListener() {                                      //로그인 버튼 클릭시 이벤트 선언
//            @Override
//            public void onClick(View v) {
//                Log.d("LoginActivity", "Login Complete, go MainActivity 이동");
//                LogIn();
//            }
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = fbAuth.getCurrentUser();
//
//    }
//
//    // 로그인 처리가 google -> firebase -> intent 까지 처리하는 과정이 다 담겨있는 부분이다.
//    private void LogIn(){
//        Intent logInIntent = googleLogInClient.getSignInIntent();
//        //logInIntent.putExtra("loginInfo", googleLogInClient.getSignInIntent()); new Intent(LoginActivity.this, MainActivity.class);
//                /*//googleLogInClient.getSignInIntent();
//                //인텐트에 정보를 잠시 담고, activity를 실행하여 intent의 정보를 내가 설정한 코드와 함께보낸다.*/
//        // 이방법은 다른 방해를 막고 더 빠르게 가동시켜준다. 이렇게 하면 , onactivityResult()메서드가 자동실행됨.
//        startActivityForResult(logInIntent, SIGN_IN_CODE);
//        Log.d("LoginActivity", "LogIn() 정상처리, 확인 코드는 "+SIGN_IN_CODE+"입니다.");
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == SIGN_IN_CODE){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data); //task에 intent정보를 꺼내서 넣어준다.
//            checkLogInResult(task);
//        }
//    }
//    //구글계정으로 로그인이 되는지 확인 하는 메서드
//    private void checkLogInResult(Task<GoogleSignInAccount> completeTask){
//        try{
//            GoogleSignInAccount googleAccount = completeTask.getResult(ApiException.class);
//            Log.d("FirebaseAuth", "파이어베이스 정상처리 되었습니다.");
//            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT);
//            FbGoogleAuth(googleAccount);
//        }catch (ApiException e){
//            Log.d("FirebaseAuth", "파이어베이스 비정상처리 되었습니다.");
//            Toast.makeText(LoginActivity.this,
//                    "로그인 실패!! LoginActivity.class onActivityResult 확인", Toast.LENGTH_SHORT).show();
//            FbGoogleAuth(null);
//        }
//    }
//
//    // 구글 계정 로그인 정보를, firebase에 넣어준다.
//    //Log.d("loadJSON", "loadJSON 정상가동>>>>> "+data); 로그 띄우는 방법
//    private void FbGoogleAuth(GoogleSignInAccount account){
//        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);//파이어베이스에서 게정 신뢰를 제공하는 값을 가져옵니다.
//        fbAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                // 해당 구글 계정으로 파이어베이스에서 로그인이 성공시 , 원하는 로직
//                if(task.isSuccessful()){
//                    FirebaseUser user = fbAuth.getCurrentUser();
//                    //firebaseuser 의 의미를 모르겠다. 그냥, googlesigninaccount에서 가져오는게 나은건가.
//                    GoogleSignInAccount myInfo = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
//                    Uri photoMe = myInfo.getPhotoUrl();
//                    String name = myInfo.getDisplayName();
//                    Log.d("FirebaseAuth", "파이어베이스 API 로그인 정상가동");
//                    Toast.makeText(LoginActivity.this,
//                            name+"//"+photoMe+"성공 넘어갑니다~", Toast.LENGTH_SHORT).show();
//                }else{
//                    Log.d("FirebaseAuth", "파이어베이스 API 로그인 실패");
//                    Toast.makeText(LoginActivity.this,
//                            "로그인 실패 -LoginActivity.class onComplete 확인필요 ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//
//
//}
