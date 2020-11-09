package com.greenhelix.pear.listShow;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.R;

public class OrderListActivity extends AppCompatActivity {
    /*레이아웃매니저를 통해서 카드들과 그 안에 있는 데이터들을 원하는 화면에 쫘악 띄어준다.*/
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("pear_orders");
    private OrderListAdapter adapter;
    private RecyclerView recyclerOrderView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pear_list);

        setOrdersRecycleView();

    }

    private void setOrdersRecycleView() {
        Query query  = orderRef.orderBy(FieldPath.documentId(), Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Order> opt = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        adapter = new OrderListAdapter(opt);

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

