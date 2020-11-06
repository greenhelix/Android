package com.greenhelix.pear.cloudDB;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;

import java.util.ArrayList;
import java.util.List;

public class CloudStore extends AppCompatActivity {

    private static final String TEST = "ik_test";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<String> senderData = new ArrayList<>();
    List<String> recipientData = new ArrayList<>();
    Boolean isItCamera = false;
    Boolean isItDirect = false;
    Button goSelectPear;
    Button goBefore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        senderData = getIntent().getExtras().getStringArrayList("direct_sender");
        recipientData = getIntent().getExtras().getStringArrayList("direct_recipient");
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        if(senderData!= null) isItDirect = true;

        if(isItDirect){
            directAddRow(table);
        }
        if(isItCamera){
            cameraAddRow();
        }
        //배송물품 선택 버튼
        /*화면이동과 클라우드DB 에 해당 정보를 저장한다.*/
        goSelectPear = (Button) findViewById(R.id.btn_cloud_next);

        //이전버튼
        goBefore = (Button) findViewById(R.id.btn_cloud_befpre);
        goBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    //직접주문입력 화면, 주문 테이블 생성
    public void directAddRow(TableLayout t){
        // 체크박수 중앙 위치 방법 까다로워서 그냥 다 왼쪽정렬시킴.
        TableRow row = new TableRow(this);

        TextView index = new TextView(this);
        index.setText(String.valueOf(1));
        index.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        row.addView(index);

        TextView recipient = new TextView(this);
        recipient.setText(recipientData.get(0));
        recipient.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        row.addView(recipient);

        TextView sender = new TextView(this);
        sender.setText(senderData.get(0));
        sender.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        row.addView(sender);

        addCheck(row);


        t.addView(row);
    }
    //체크박스 추가
    public void addCheck(TableRow row){
        CheckBox check = new CheckBox(this);
        row.addView(check);
    }
    //카메라 입력시 로직 공간
    public void cameraAddRow(){

    }


}
