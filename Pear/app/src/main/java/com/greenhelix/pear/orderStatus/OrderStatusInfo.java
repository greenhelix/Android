package com.greenhelix.pear.orderStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;
import com.greenhelix.pear.directOrder.DirectOrderActivity;
import com.greenhelix.pear.server.AddressAPIkakao;

import java.util.ArrayList;
import java.util.List;

/*주문정보 수정화면 1*/
public class OrderStatusInfo extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    EditText m_sender_name, m_sender_tel1, m_sender_tel2, m_sender_tel3;
    EditText m_recipient_name,m_recipient_tel1, m_recipient_tel2, m_recipient_tel3;
    EditText m_address_num, m_address_detail1, m_address_detail2;

    Button modifyComplete, modifyBefore, modifyAddress;
    List<String> m_senderList = new ArrayList<>();
    List<String> m_recipientList= new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("pear_orders");
    String id = "";
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_info);
        m_sender_name = (EditText) findViewById(R.id.et_status_sender);
        m_sender_tel1 = (EditText) findViewById(R.id.et_status_sender_tel1);
        m_sender_tel2 = (EditText) findViewById(R.id.et_status_sender_tel2);
        m_sender_tel3 = (EditText) findViewById(R.id.et_status_sender_tel3);
        m_recipient_name = (EditText) findViewById(R.id.et_status_recipient);
        m_recipient_tel1 = (EditText) findViewById(R.id.et_status_recipient_tel1);
        m_recipient_tel2 = (EditText) findViewById(R.id.et_status_recipient_tel2);
        m_recipient_tel3 = (EditText) findViewById(R.id.et_status_recipient_tel3);
        m_address_num = (EditText) findViewById(R.id.et_status_address_num);
        m_address_detail1 = (EditText) findViewById(R.id.et_status_address_show);
        m_address_detail2 = (EditText) findViewById(R.id.et_status_address_detail);
        modifyDataShow(); //수정할 카드의 데이터를 표시해줍니다.

        modifyComplete = (Button) findViewById(R.id.btn_status_next);
        modifyComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDataUpdate();
                startActivity(new Intent(OrderStatusInfo.this, OrderStatusPear.class));
            }
        });
        modifyBefore = (Button) findViewById(R.id.btn_status_before);
        modifyBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        modifyAddress = findViewById(R.id.btn_status_address);
        if (modifyAddress != null) {
            modifyAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(OrderStatusInfo.this, AddressAPIkakao.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }

    }

    public void modifyDataShow(){
        final int position = getIntent().getExtras().getInt("position");
        id = getIntent().getExtras().getString("id");
        Log.d(LOG_TAG, "카드위치 : "+position + "\n카드아이디 : "+id);
        //문서 정보경로
        orderRef.document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() { //get 가져오기
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String sender = documentSnapshot.getString("sender");
                m_sender_name.setText(sender);
                String recipient = documentSnapshot.getString("recipient");
                m_recipient_name.setText(recipient);
                String sender_tel = documentSnapshot.getString("sender_tel");
                m_sender_tel1.setText(sender_tel.substring(0,4));
                m_sender_tel2.setText(sender_tel.substring(3,6));
                m_sender_tel3.setText(sender_tel.substring(6));
                String recipient_tel = documentSnapshot.getString("recipient_tel");
                m_recipient_tel1.setText(recipient_tel.substring(0,4));
                m_recipient_tel2.setText(recipient_tel.substring(3,6));
                m_recipient_tel3.setText(recipient_tel.substring(6));
                List<String> recipient_addr = (List<String>) documentSnapshot.get("recipient_addr");
                m_address_num.setText(recipient_addr.get(0));
                m_address_detail1.setText(recipient_addr.get(1));
                m_address_detail2.setText(recipient_addr.get(2));
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(ERROR, "불러오지 못했습니다.");
            }
        });
    }

    public void modifyDataUpdate(){
        m_senderList.add(m_sender_name.getText().toString());
        m_senderList.add(m_sender_tel1.getText().toString());
        m_senderList.add(m_sender_tel2.getText().toString());
        m_senderList.add(m_sender_tel3.getText().toString());
        m_recipientList.add(m_recipient_name.getText().toString());
        m_recipientList.add(m_recipient_tel1.getText().toString());
        m_recipientList.add(m_recipient_tel2.getText().toString());
        m_recipientList.add(m_recipient_tel3.getText().toString());
        m_recipientList.add(m_address_num.getText().toString());
        m_recipientList.add(m_address_detail1.getText().toString());
        m_recipientList.add(m_address_detail2.getText().toString());

        ArrayList<String> reAdr = new ArrayList<>();
        reAdr.add(m_recipientList.get(4));
        reAdr.add(m_recipientList.get(5));
        reAdr.add(m_recipientList.get(6));
        orderRef.document(id)
                .update("sender", m_senderList.get(0),
                        "sender_tel", m_senderList.get(1)+m_senderList.get(2)+m_senderList.get(3),
                        "recipient", m_recipientList.get(0),
                        "recipient_tel", m_recipientList.get(1)+m_recipientList.get(2)+m_recipientList.get(3),
                        "recipient_addr", reAdr
                );
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.d(LOG_TAG, "###  주소값 ### \n"+ data);
                        String[] address = data.split(",");
                        m_address_num.setText(address[0]);
                        m_address_detail1.setText(address[1]);
                    }
                }
                break;
        }
    }
}