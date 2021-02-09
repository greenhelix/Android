package com.greenhelix.pear.deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;
import com.greenhelix.pear.listShow.Order;
import com.skt.Tmap.TMapTapi;

public class DeliverOrderActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private final FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    private final CollectionReference deliverRef = firebaseDB.collection("pear_orders");
    private RecyclerView cycleOrderDeliverView;
    private DeliverOrderAdapter adapter;
    Button btnDeliverBefore, btnDeliverStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_order);
        Log.d(LOG_TAG, "배달 주문확인화면  정상 OnCreate 되었습니다.");

        setDeliverOrderRecyclerView();

        btnDeliverBefore = findViewById(R.id.btn_deliver_before);
        btnDeliverBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backMain = new Intent(getApplication(), MainActivity.class);
                backMain.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(backMain);
                finish();
            }
        });

    }

    private void setDeliverOrderRecyclerView(){

        Query query = deliverRef.orderBy("sender", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Order> opt = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        adapter = new DeliverOrderAdapter(opt);

        cycleOrderDeliverView = findViewById(R.id.deliver_order_list);
        cycleOrderDeliverView.setLayoutManager(new LinearLayoutManager(this));
        cycleOrderDeliverView.setHasFixedSize(true);
        cycleOrderDeliverView.setAdapter(adapter);

//                Log.d(LOG_TAG, "배달주문 내역 확인.");
//                Log.d(LOG_TAG, "배달주문 주소:?");
//                try{
//                    Intent tmapOpen = getPackageManager().getLaunchIntentForPackage("com.skt.tmap.ku");
//                    tmapOpen.setAction(Intent.ACTION_SEND);
//                    tmapOpen.putExtra(Intent.EXTRA_TEXT, tmaptapi.invokeRoute("혁규농원",0,0));
//                    tmapOpen.setType("text/*");
//                    Intent shareIntent = Intent.createChooser(tmapOpen, null);
//                    startActivity(shareIntent);
//                }catch (Exception e){
//                    String url = "market://details?id=com.skt.tmap.ku";
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(i);
//                }
                // 주문정보를 확인해서, 선택시, 해당 주소를 가져와서 위도와 경도 구해오는 스니펫
                // 이것을 이용해서, tmap으로 해당 주소와 위도 경도를 보내면, 경로 탐색을 시작한다.
                    /*Geocoder g = new Geocoder(this);
                try{List<Address> adr = null;
                adr = g.getFromLocationName(recipientData.get(5),1);
                Log.d(LOG_TAG, "this : "+adr.toString());
                }catch (IOException e){
                Log.d(LOG_TAG, "except error IO");
                }*/
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "배달종료 확인");
        AlertDialog.Builder builder = new AlertDialog.Builder(DeliverOrderActivity.this);
        builder.setTitle("알림");
        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG," 종료 합니다.");
                ActivityCompat.finishAffinity(DeliverOrderActivity.this); // 해당 액티비티 종료
                System.exit(0); //앱 완전 종료
            }
        }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(LOG_TAG,"유지합니다.");
            }
        }).show();
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