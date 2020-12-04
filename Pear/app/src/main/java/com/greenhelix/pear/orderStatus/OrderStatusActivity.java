package com.greenhelix.pear.orderStatus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.greenhelix.pear.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class OrderStatusActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef = db.collection("pear_orders");
    private RecyclerView cycleOrderStatusView;

    //어댑터 생성했으면 여기에 추가
    private OrderStatusAdapter adapter;

    Button btnDelete, btnMain, btnExport;
    Button filter1, filter2, filter3, filter4;
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
        filter1 = findViewById(R.id.btn_filter_status1);
        setFilter(filter1);
        filter2 = findViewById(R.id.btn_filter_status2);
        setFilter(filter2);
        filter3 = findViewById(R.id.btn_filter_status3);
        setFilter(filter3);
        filter4 = findViewById(R.id.btn_filter_status4);
        setFilter(filter4);
        btnExport = findViewById(R.id.btn_status_export);
    }//onCreate END

    //초기 순환뷰 기능 여기 들감
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

        // 주문 카드를 선택했을때, 모션값을 인식하고 그에 따른 삭제 명령을 주었다. onSwiped을 통해서 삭제를 시킴. 다른 방향을 제어하거나 명령을 바꿀수 있다.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction){
                Log.d(LOG_TAG, "삭제명령");
                final int position = viewHolder.getAdapterPosition();
                if(direction == ItemTouchHelper.LEFT){
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderStatusActivity.this);
                    builder.setTitle("AlertDialog Title");
                    builder.setMessage("AlertDialog Content");

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteItem(position);
                            Log.d(LOG_TAG, "삭제되었습니다." + adapter.getSnapshots().getSnapshot(position).getString("user"));
                        }
                    }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "취소하였습니다.",Toast.LENGTH_SHORT).show();
                            cycleOrderStatusView.setAdapter(adapter);
                        }
                    }).show();
                }
            }
        }).attachToRecyclerView(cycleOrderStatusView); //마지막에는 순환뷰에 적용
    }

    //필터링 버튼 메서드  query를 생성하고 option에 반영한뒤, update를 해주면 된다.
    public void setFilter(final Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = btn.getText().toString();
                Log.d(LOG_TAG, "필터링 "+s +"이 클릭되었습니다.");
                Query query1 = orderRef.whereEqualTo("status", s);
                FirestoreRecyclerOptions<NowOrder> option1 = new FirestoreRecyclerOptions.Builder<NowOrder>()
                        .setQuery(query1, NowOrder.class)
                        .build();

                adapter.updateOptions(option1);

            }
        });
    }

    //유투브 베이스로 먼저 진행
    public void exportCSV(View view){
        String testData = "이게 데이터";

        try{
            //file저장하는 부분
            FileOutputStream exportStream = openFileOutput("파일명.csv", Context.MODE_PRIVATE);
            exportStream.write(testData.getBytes());
            exportStream.close();

            //export하는 부분
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "파일명.csv");
            Uri exportpath = FileProvider.getUriForFile(context, "com.greenhelix.pear.fileprovider", filelocation);
            Intent exportIntent = new Intent(Intent.ACTION_SEND); //인텐트 action_send의미 찾아볼것
            exportIntent.setType("text/csv");
            exportIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            exportIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            exportIntent.putExtra(Intent.EXTRA_STREAM, exportpath);
            startActivity(Intent.createChooser(exportIntent, "Send mail"));
            Log.d(LOG_TAG, "파일 내보내기 완료");

        }catch (FileUriExposedException | FileNotFoundException e ){
            Log.d(ERROR, "파일 이상 \n"+ e.toString());
        } catch (IOException f) {
            Log.d(ERROR, "파일안써짐 \n"+ f.toString());
        }
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
