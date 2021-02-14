package com.greenhelix.pear.deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;
import com.greenhelix.pear.listShow.Order;
import com.skt.Tmap.TMapTapi;

import java.io.IOException;
import java.util.List;

public class DeliverOrderActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private final FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    private final CollectionReference deliverRef = firebaseDB.collection("pear_orders");
    private RecyclerView cycleOrderDeliverView;
    private DeliverOrderAdapter adapter;
    Button btnDeliverBefore, btnDeliverStart;
    private Boolean isClick = true;
    private List<String> deliver_addr;
    TMapTapi tmaptapi = new TMapTapi(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_order);
        Log.d(LOG_TAG, "배달 주문확인화면  정상 OnCreate 되었습니다.");
        tmaptapi.setSKTMapAuthentication("l7xx67178473a0134850bb0610927c9ba539");
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

        // 완료후 네비게이션 실행
        btnDeliverStart = findViewById(R.id.btn_deliver_complete);
        btnDeliverStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "완료버튼에 들어온 주소값. \n" + deliver_addr.get(1));
                List<Address> adr = null; // List<Address> 형태로 받아야함.
                Geocoder g = new Geocoder(getApplicationContext());
                try{
                    // getFromLocationName은 지명-> 좌표 
                    // getFromLocation 은 좌표 -> 지명
                    adr = g.getFromLocationName(deliver_addr.get(1),1);
                    Log.d(LOG_TAG, "좌표 확인... : "+adr.get(0).getLatitude()+", "+adr.get(0).getLongitude());
                }catch (IOException e){
                    Log.d(LOG_TAG, "except error IO");
                }

                float lat = (float) adr.get(0).getLatitude();
                float lon = (float) adr.get(0).getLongitude();

                try{


//                    Intent tmapOpen = getPackageManager().getLaunchIntentForPackage("com.skt.tmap.ku");
                    tmaptapi.invokeNavigate(deliver_addr.get(1),lat,lon,0,false);
//                    tmapOpen.setAction(Intent.ACTION_SEND);
//                    tmapOpen.putExtra(Intent.EXTRA_TEXT, );
//                    tmapOpen.setType("text/*");
//                    Intent shareIntent = Intent.createChooser(tmapOpen, null);
//                    startActivity(tmapOpen);
                }catch (Exception e){
                    String url = "market://details?id=com.skt.tmap.ku";
//                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    Log.d(LOG_TAG, "예외발생, 네비게이션 안켜짐");
//                    startActivity(i);
                }
                // 주문정보를 확인해서, 선택시, 해당 주소를 가져와서 위도와 경도 구해오는 스니펫
                // 이것을 이용해서, tmap으로 해당 주소와 위도 경도를 보내면, 경로 탐색을 시작한다.


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


        cycleOrderDeliverView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);
                Log.d(LOG_TAG, "위치는: "+ position);
                if(isClick) {
                    child.setBackgroundColor(Color.parseColor("#72DAE8"));
                    final List<String> test_addr = (List<String>) adapter.getSnapshots().getSnapshot(position).get("recipient_addr");
                    Log.d(LOG_TAG, "주소값을 가져왔습니다. \n" + test_addr);
                    isClick = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliverOrderActivity.this);
                    builder.setTitle("알림");
                    builder.setMessage("해당 주소가 맞습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "배송지 좌표를 저장하였습니다.. \n 완료를 눌러주세요..",Toast.LENGTH_SHORT).show();
                            deliver_addr = test_addr;
                        }
                    }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "취소하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }else{
                    child.setBackgroundColor(Color.parseColor("#ffffff"));
                    isClick = true;
                }

                // true로 바꿔줘야 한번 누르면 한번 불러온다.
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


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