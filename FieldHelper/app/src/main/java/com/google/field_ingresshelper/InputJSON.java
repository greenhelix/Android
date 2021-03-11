package com.google.field_ingresshelper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InputJSON extends AppCompatActivity {
public Intent parseIntent ;
public String bookMark;
public String drawing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_json);

        final EditText bmkCopy = findViewById(R.id.tv_bmk);
        final EditText drawCopy = findViewById(R.id.tv_draw);

        // 플로팅 버튼에 이벤트 부여
        FloatingActionButton fab3 = findViewById(R.id.btn_parser);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 에디트 텍스트 문자를 가져와서 dataShow.class에 인텐트를 통해 보내준다.

                //처음은 배송지를 적는 느낌으로 택배상자를 만든다.
                parseIntent = new Intent(InputJSON.this, DataShow.class);

                    // 원하는 정보를 해당 택배 상자에 넣어준다.
                    bookMark= bmkCopy.getText().toString();
                    parseIntent.putExtra("bookmark", bookMark);

                    drawing = drawCopy.getText().toString();
                    parseIntent.putExtra("draw", drawing);

                    //자연스럽게 인텐트를 실행하면 해당 정보를 싣고 도착 액티비티로 가는데,
                    // 해당 액티비티가 실행되면서 화면 layout연결된 것과 함께 실행되면서 화면전환이 일어난다.
                startActivity(parseIntent); // 액티비티를 실행시킨다. data.show에 해당하는 곳으로
                finish(); // 해당 activity 는 종료 시켜 줘야 성능에서 문제가 없어진다.

            }
        });
    }
}
