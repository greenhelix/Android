package com.greenhelix.pear.deliver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.R;
import com.greenhelix.pear.orderStatus.OrderStatusAdapter;

public class deliverOrderActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    private CollectionReference deliverRef = firebaseDB.collection("pear_orders");
    private RecyclerView cycleOrderDeliverView;
    Button btnDeliverBefore;
//    private OrderStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_order);
    }

    private void setDeliverOrderRecyclerView(){
        Query query = deliverRef.orderBy("sender", Query.Direction.DESCENDING);

    }
}