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

public class LoginActivity extends AppCompatActivity {

    // xml 버튼 할당
    private SignInButton signInButton;
    private Button signOutButton;
    private GoogleSignInClient client;
    private FirebaseAuth fAuth;
    private int SIGNINCODE = 1;
    private static final String LOG_TAG = "ik";
    private AuthCredential authCredential;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 각 변수별 할당
        signInButton = findViewById(R.id.btn_google_login);
        //signOutButton = findViewById(R.id.btn_google_logout);
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
        signOutButton = findViewById(R.id.btn_logout);
    } //onCreate END

    private void signIn(){
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, SIGNINCODE);
    }

    public void signOut(View v){
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(LOG_TAG, "찐짜 로그아웃??");
                    }
                });
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
        authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
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
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (fUser != null){
            final String personEmail = account.getEmail();
            Log.d(LOG_TAG, "현재 이메일 ::"+personEmail);
            //로그인 된 계정이 운영자 컬렉션안의 문서에 해당되면 운영자 화면으로 이동한다.
            db.collection("manager").document("manager1")
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.get("admin1").toString().equals(personEmail)){
                        Log.d(LOG_TAG, "이메일확인:: "+personEmail);
                        Intent access = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(access);
                        finish();
                    }else if(documentSnapshot.get("admin2").toString().equals(personEmail)){
                        Log.d(LOG_TAG, "이메일확인:: "+personEmail);
                        Intent access = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(access);
                        finish();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(LOG_TAG, "틀렸습니다. 로그인실패");
                }
            });

            db.collection("customer").document("user1")
                    .update("email", personEmail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(LOG_TAG, "이메일확인:: "+personEmail+ "\n 고객입니다.");
                            Intent access = new Intent(LoginActivity.this, CustomerActivity.class);
                            startActivity(access);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(LOG_TAG, "고객 등록 실패");
                }
            });
        }
    }

}

