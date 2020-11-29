package com.greenhelix.pear.orderStatus;

import android.os.Bundle;
import android.util.Log;
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
import com.greenhelix.pear.R;

public class OrderStatusActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("pear_orders");
    private RecyclerView cycleOrderStatusView;

    //어댑터 생성했으면 여기에 추가
    private OrderStatusAdapter adapter;

    Button btnDelete, btnMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_current_status);
        Log.d(LOG_TAG, "주문현황 화면 정상 OnCreate 되었습니다.");

        setOrderStatusRecyclerView();

        btnMain = findViewById(R.id.btn_status_go_main);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDelete = findViewById(R.id.btn_status_complete_del);
    }//onCreate END

    private void setOrderStatusRecyclerView(){
        Query query = orderRef.orderBy("sender", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<NowOrder> option = new FirestoreRecyclerOptions.Builder<NowOrder>()
                .setQuery(query, NowOrder.class)
                .build();

        adapter = new OrderStatusAdapter(option);

        cycleOrderStatusView = findViewById(R.id.recycler_status_list);
        cycleOrderStatusView.setLayoutManager(new LinearLayoutManager(this));
        cycleOrderStatusView.setHasFixedSize(true);
        cycleOrderStatusView.setAdapter(adapter);

        //보통은 해당 클릭리스너 만들면, this로 implement시켜서 사용하지만,
        //커스텀을 한상태로 클릭 리스너를 만들었기 때문에 아래와 같이 사용한다.
//        adapter.setOnCardClickListener(new OrderStatusAdapter.OnOrderClickListener() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                NowOrder nowOrder = documentSnapshot.toObject(NowOrder.class);
//                String id = documentSnapshot.getId();
//                Log.d(LOG_TAG, "activity로 잘 넘어왔다.\n 현재 선택한 문서의 아이디는 " + id);
//            }
//        });
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
