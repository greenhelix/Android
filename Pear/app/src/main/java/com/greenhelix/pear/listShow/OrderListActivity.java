package com.greenhelix.pear.listShow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;

public class OrderListActivity extends AppCompatActivity {
    /*레이아웃매니저를 통해서 카드들과 그 안에 있는 데이터들을 원하는 화면에 쫘악 띄어준다.*/
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //해당 콜렉션참고를 위해 원할한 경로를 설정을 참고로 만들어줘야함.
    private CollectionReference orderRef = db.collection("pear_orders");
    private OrderListAdapter adapter;
    private RecyclerView recyclerOrderView;

    Button btnFinalUpload, btnFinalBefore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //핵심 카드뷰 설정 메서드
        setOrdersRecycleView();

        btnFinalBefore = findViewById(R.id.btn_final_before);
        btnFinalBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnFinalUpload = findViewById(R.id.btn_final_upload);
        btnFinalUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(new Intent(OrderListActivity.this, MainActivity.class));
            }
        });

    }

    //Cloud DB에 쿼리를 설정해준다. 정렬방향, 옵션 등 다양한 설정을 여기서 한다.
    private void setOrdersRecycleView() {
        Query query  = orderRef.orderBy("sender", Query.Direction.DESCENDING);
        //데이터바구니, 데이터, 데이터가 들어갈xml틀을 정의하고 있는 adapter에 생명을 주는 과정
        //어댑터에는 firestore 순환 뷰 옵션이 있어야한다.  이 옵션에 order.class가 들어가서
        //위의 쿼리 조건과 함께 알맞는 정보를 가져온다.
        FirestoreRecyclerOptions<Order> opt = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();
        //최종 어댑터에 생명을 준다.
        adapter = new OrderListAdapter(opt);

        //순환 뷰에 xml틀을 박아주고, 매니저를 튼후, 어댑터의 생명을 넣어주면 끝이다.
        recyclerOrderView = findViewById(R.id.recycler_order_list);
        recyclerOrderView.setLayoutManager(new LinearLayoutManager(this));
        recyclerOrderView.setHasFixedSize(true);
        recyclerOrderView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}

