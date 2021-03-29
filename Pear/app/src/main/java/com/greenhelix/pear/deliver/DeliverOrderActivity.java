package com.greenhelix.pear.deliver;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;
import com.greenhelix.pear.listShow.Order;
import com.skt.Tmap.TMapTapi;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DeliverOrderActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private final FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    private final CollectionReference deliverRef = firebaseDB.collection("pear_orders");
    private DeliverOrderAdapter adapter;
    Button btnDeliverBefore, btnDeliverStart;
    private List<String> deliver_addr;
    private TMapTapi tmaptapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tmaptapi = new TMapTapi(this);
        tmaptapi.setSKTMapAuthentication (getString(R.string.tmap_api_key));
        setContentView(R.layout.activity_deliver_order);
        Log.d(LOG_TAG, "배달 주문확인화면  정상 OnCreate 되었습니다.");

        setDeliverOrderRecyclerView();

        //이전 버튼
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

        // 완료후 네비게이션 실행
        btnDeliverStart = findViewById(R.id.btn_deliver_complete);
        btnDeliverStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliver_addr = getIntent().getExtras().getStringArrayList("navi_info");
                Log.d(LOG_TAG, "완료버튼에 들어온 주소값.");
                Log.d(LOG_TAG,"목적지:"+ deliver_addr.get(1));
                List<Address> adr = null; // List<Address> 형태로 받아야함.
                //주소명으로 좌표찾기 위해서는 geocoder를 불러와야한다.
                Geocoder g = new Geocoder(getApplicationContext());

                // 주소 출발지, 도착지 다 적는다.  출발지는 혁규농원 고정
                HashMap<String, String> pathInfo = new HashMap<>();

                try{
                    adr = g.getFromLocationName(deliver_addr.get(1),1); // 도착지 주소로 좌표 가져오기
                    pathInfo.put("rGoName", deliver_addr.get(1)); //도착지
                    pathInfo.put("rGoY", String.valueOf(adr.get(0).getLatitude())); //Y부터 넣어준다.
                    pathInfo.put("rGoX", String.valueOf(adr.get(0).getLongitude())); //X값
                    // 출발지 정보 - 현재위치로 변경 가능하다. Geocoder를 활용하면된다.
                    pathInfo.put("rStName","혁규농원");
                    pathInfo.put("rStY", "37.69667672191368");
                    pathInfo.put("rStX", "127.12651783206553");

                    Log.d(LOG_TAG, "좌표 확인... : "+adr.get(0).getLatitude()+", "+adr.get(0).getLongitude()+"\n");
                    Log.d(LOG_TAG, "출발지: 혁규농원");
                    Log.d(LOG_TAG, "좌표 확인... : 37.69667672191368, 27.12651783206553");
                }catch (IOException e){
                    Log.d(LOG_TAG, "except error IO");
                }
                tmaptapi.invokeRoute(pathInfo);
                Log.d(LOG_TAG, "네이게이션 이동.. 길찾기 완료!");
            }
        });
    }

    private void setDeliverOrderRecyclerView(){

        Query query = deliverRef.orderBy("sender", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Order> opt = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        adapter = new DeliverOrderAdapter(opt);

        RecyclerView cycleOrderDeliverView = findViewById(R.id.deliver_order_list);
        cycleOrderDeliverView.setLayoutManager(new LinearLayoutManager(this));
        cycleOrderDeliverView.setHasFixedSize(true);
        cycleOrderDeliverView.setAdapter(adapter);


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