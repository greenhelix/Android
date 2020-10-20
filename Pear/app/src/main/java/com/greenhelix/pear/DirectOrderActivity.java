package com.greenhelix.pear;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
/*주문 직접 입력 화면*/
public class DirectOrderActivity extends AppCompatActivity {

    EditText sender_name;
    Spinner sender_tel1;
    EditText sender_tel2;
    EditText sender_tel3;

    EditText recipient_name;
    Spinner recipient_tel1;
    EditText recipient_tel2;
    EditText recipient_tel3;

    TextView address_num;
    TextView address_detail1;
    TextView address_detail2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_order_direct_info);

        sender_name.findViewById(R.id.et_direct_sender);
        sender_tel1.findViewById(R.id.sp_direct_sender_tel1);
        sender_tel2.findViewById(R.id.et_direct_sender_tel2);
        sender_tel3.findViewById(R.id.et_direct_sender_tel3);
        recipient_name.findViewById(R.id.et_direct_recipient);
        recipient_tel1.findViewById(R.id.sp_direct_recipient_tel1);
        recipient_tel2.findViewById(R.id.et_direct_recipient_tel2);
        recipient_tel3.findViewById(R.id.et_direct_recipient_tel3);
        address_num.findViewById(R.id.tv_direct_address_num);
        address_detail1.findViewById(R.id.tv_direct_address_show);
        address_detail2.findViewById(R.id.et_direct_address_detail);








    }
}
