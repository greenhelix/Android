package greenhelix.androidproject.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
// 시작화면시 화면에 로고를 띄어준뒤, 메인액티비티로 넘어가게 하는 클래스
public class IntroActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);  // 인트로 레이아웃을 불러준다.
        Handler start = new Handler();
        start.postDelayed(new Runnable() {  // 핸들러를 통해서 인텐트를 형성하여 postdelayed를 설정해준다.
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);  //넘어갈 화면은 메인화면이다.
                startActivity(intent);
                finish();
            }
        }, 1000);  // 2초동안 있다가 메인으로 넘어감
    }

}
