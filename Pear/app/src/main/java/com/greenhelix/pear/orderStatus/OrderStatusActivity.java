package com.greenhelix.pear.orderStatus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;

public class OrderStatusActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("pear_orders");
    private RecyclerView recyclerOrderView;

    Button btnDelete, btnMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_current_status);
        Log.d(LOG_TAG, "주문현황 화면 정상 OnCreate 되었습니다.");




        btnMain = findViewById(R.id.btn_status_go_main);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDelete = findViewById(R.id.btn_status_complete_del);
    }

}
