package com.greenhelix.pear.deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeliverOrderActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private final FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    private final CollectionReference deliverRef = firebaseDB.collection("pear_orders");
    private RecyclerView cycleOrderDeliverView;
    private DeliverOrderAdapter adapter;
    Button btnDeliverBefore, btnDeliverStart;
    private Boolean isClick = false;
    private List<String> deliver_addr;
    private TMapTapi tmaptapi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tmaptapi = new TMapTapi(this);
        tmaptapi.setSKTMapAuthentication ("l7xx4e573e36ddcc414290a5e9198525ed36");
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

        cycleOrderDeliverView = findViewById(R.id.deliver_order_list);
        cycleOrderDeliverView.setLayoutManager(new LinearLayoutManager(this));
        cycleOrderDeliverView.setHasFixedSize(true);
        cycleOrderDeliverView.setAdapter(adapter);

        // 주문 선택 이벤트 발생
        cycleOrderDeliverView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                isClick = true;

                final View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);
                Log.d(LOG_TAG, "위치는: "+ position);
                if(isClick) {
                    child.setBackgroundResource(R.drawable.deliver_true);
                    final List<String> test_addr = (List<String>) adapter.getSnapshots().getSnapshot(position).get("recipient_addr");
                    Log.d(LOG_TAG, "주소값을 가져왔습니다. \n" + test_addr);
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
                            deliver_addr = new ArrayList<String>();
                            Log.d(LOG_TAG, "주소값을 비웠다"+deliver_addr);
                            child.setBackgroundResource(R.drawable.deliver_false);
                            isClick = false;

                        }
                    }).show();
                }else{
                    child.setBackgroundResource(R.drawable.deliver_false);
                    isClick = false;
                }

                // true로 바꿔줘야 한번 누르면 한번 불러온다.
                return true;
            }

            //지우면 안됨.
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }
            //지우면 안됨.
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