package com.greenhelix.pear.orderStatus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

    public void exportCSV(View view){
        //파일경로를 먼저 설정
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS); //기본 루트에, 공식폴더형태로 해줄수 있음 이거는 document폴더 생성
        Log.d(LOG_TAG, "파일 경로는??? \n"+ storageDir.toString());
        //try catch문을 먼저 작성.
        try {
            /*파일만드기 파트*/
            File test = File.createTempFile("export", ".csv", storageDir); // 파일명, 형태, 파일경로 를 설정해준다.
            FileWriter testWrite = new FileWriter(test); // 파일에 데이터를 넣어줄 도구 끄낸다.(도구를 사용할 해당 파일 명시해야한다.)
            BufferedWriter writer = new BufferedWriter(testWrite); // 도구를 사용할 도화지 같은 공간을 만들어준다.

            for(int i = 0; i<5; i++){
                writer.write("\n"+String.valueOf(i)+","+String.valueOf(i*i));  // 원하는 데이터를 string형태로 ,을 포함하여 원하는 형식으로 넣어준다.
            }
            // 입력이 끝나면, 도구와 도화지를 그만 꺼준다.
            writer.close();
            testWrite.close();

            /* 파일생성완료되었으니, 공유파트*/
            Uri exportUri = FileProvider.getUriForFile(getApplicationContext(),
                    "com.greenhelix.pear.fileprovider", test); // 파일을 uri형태로 변환해준다. fileprovider 경로는 한곳으로 정해두면된다.(상단의 파일경로가 바꿔주는 거임)

            // intent를 통해서 데이터 파일을 보내준다. intent의 속성과 옵션을 설정해준다.
            Intent fileIntent = new Intent(Intent.ACTION_SEND); // 공유화면을 열어준다.(메일, 드라이버 등등)
            fileIntent.setType("text/csv"); //?/
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "혁규농원 주문목록입니다!"); // 메일 작성시 메일 제목
            fileIntent.putExtra(Intent.EXTRA_TEXT, "주문정보 파일을 확인하세요. ^^! "); // 메일 작성시 메일 내용
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // uri 허가권

            fileIntent.putExtra(Intent.EXTRA_STREAM, exportUri); // 파일 uri를 넣어준다.
            startActivity(Intent.createChooser(fileIntent, "주문정보를 내보냅니다.")); // intent를 실행해주면서, 공유창 상단에 제목을 보여준다.

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
