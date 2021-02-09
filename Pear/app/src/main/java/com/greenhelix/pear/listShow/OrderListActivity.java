package com.greenhelix.pear.listShow;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;
import com.greenhelix.pear.directOrder.DirectSenderActivity;

/*카드가 띄어지는 리스트 공간*/
public class OrderListActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    //해당 콜렉션참고를 위해 원할한 경로를 설정을 참고로 만들어줘야함.
    private final CollectionReference orderRef = db.collection("pear_orders");
    private OrderListAdapter adapter;
    Button btnFinalUpload, btnFinalBefore;
    private static final String LOG_TAG = "ik";
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "주문리스트 확인화면 종료 확인");
        if(doubleBackToExitPressedOnce){
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(OrderListActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG," 종료 합니다.");
                    ActivityCompat.finishAffinity(OrderListActivity.this);
                }
            }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG,"유지합니다.");

                }
            }).show();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "뒤로버튼을 한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
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
        RecyclerView recyclerOrderView = findViewById(R.id.recycler_order_list);
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

