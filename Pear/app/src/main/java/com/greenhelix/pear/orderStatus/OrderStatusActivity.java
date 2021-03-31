package com.greenhelix.pear.orderStatus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.greenhelix.pear.MainActivity;
import com.greenhelix.pear.R;
import com.greenhelix.pear.directOrder.DirectRecipientActivity;
import com.greenhelix.pear.listShow.Order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusActivity extends AppCompatActivity {
    private static final String LOG_TAG = "ik", ERROR = "ikerror";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference orderRef = db.collection("pear_orders");
    private RecyclerView cycleOrderStatusView;
    private OrderStatusAdapter adapter;
    Button btnDelete, btnMain, btnExport;
    Button filter1, filter2, filter3, filter4;

    boolean doubleBackToExitPressedOnce = false;

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
                Intent goMain = new Intent(getApplication(), MainActivity.class);
                goMain.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(goMain);
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

    }// onCreate END

    // 초기 순환뷰 기능 여기 들감
    private void setOrderStatusRecyclerView() {
        Query query = orderRef.orderBy("sender", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Order> option = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class).build();

        adapter = new OrderStatusAdapter(option);

        cycleOrderStatusView = findViewById(R.id.recycler_status_list);
        cycleOrderStatusView.setLayoutManager(new LinearLayoutManager(this));
        cycleOrderStatusView.setHasFixedSize(true);
        cycleOrderStatusView.setAdapter(adapter);

        // 주문 카드를 선택했을때, 모션값을 인식하고 그에 따른 삭제 명령을 주었다. onSwiped을 통해서 삭제를 시킴. 다른 방향을 제어하거나
        // 명령을 바꿀수 있다.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d(LOG_TAG, "스와이프하였습니다.");
                // 선택한 카드의 위치 저장해두고
                final int position = viewHolder.getAdapterPosition();
                // 왼쪽 스와이프를 한 경우
                if (direction == ItemTouchHelper.LEFT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderStatusActivity.this);
                    builder.setTitle("알림");
                    builder.setMessage("해당 주문을 삭제하시겠습니까? \n 삭제하면 주문을 복구할 수 없습니다.");

                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.deleteItem(position);
                            Log.d(LOG_TAG, "삭제되었습니다." + adapter.getSnapshots().getSnapshot(position).getString("user"));
                            Toast.makeText(getApplicationContext(), "삭제완료", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "삭제를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                            cycleOrderStatusView.setAdapter(adapter);
                        }
                    }).show();
                }
                // 오른쪽 스와이프를 한 경우
                else if (direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderStatusActivity.this);
                    builder.setTitle("알림");
                    builder.setMessage("주문 수정화면으로 이동하시겠습니까?");

                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(LOG_TAG, "수정화면으로 이동합니다.");
                            Intent modifyOrderIntent = new Intent(OrderStatusActivity.this, OrderStatusInfo.class);

                            String id = adapter.getSnapshots().getSnapshot(position).getId();
                            modifyOrderIntent.putExtra("position", position); // 해당 주문 인덱스를 전달해준다.
                            modifyOrderIntent.putExtra("id", id); // 해당 주문 아이디를 전달해준다.
                            Log.d(LOG_TAG, "보내는 position:: " + position + "\n보내는 아이디 :: " + id);
                            startActivity(modifyOrderIntent);
                        }
                    }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(LOG_TAG, "수정취소하였습니다.");
                            Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            cycleOrderStatusView.setAdapter(adapter);
                        }
                    }).show();
                }
            }
        }).attachToRecyclerView(cycleOrderStatusView); // 마지막에는 순환뷰에 적용
    }

    // 필터링 버튼 메서드 query를 생성하고 option에 반영한뒤, update를 해주면 된다.
    public void setFilter(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = btn.getText().toString();
                Log.d(LOG_TAG, "필터링 " + s + "이 클릭되었습니다.");
                Query query1 = orderRef.whereEqualTo("status", s);
                FirestoreRecyclerOptions<Order> option1 = new FirestoreRecyclerOptions.Builder<Order>()
                        .setQuery(query1, Order.class).build();

                adapter.updateOptions(option1);

            }
        });
    }

    public void exportCSV(View view) {
        // 파일경로를 먼저 설정
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS); // 기본 루트에, 공식폴더형태로 해줄수 있음 이거는 document폴더
                                                                                // 생성
        // Log.d(LOG_TAG, "파일 경로 :: \n"+ storageDir.toString());
        // try catch문을 먼저 작성.
        try {
            /* 파일만드기 파트 */
            File test = File.createTempFile("export", ".csv", storageDir); // 파일명, 형태, 파일경로 를 설정해준다.
            final FileWriter testWrite = new FileWriter(test); // 파일에 데이터를 넣어줄 도구 끄낸다.(도구를 사용할 해당 파일 명시해야한다.)
            final BufferedWriter writer = new BufferedWriter(testWrite); // 도구를 사용할 도화지 같은 공간을 만들어준다.
            Log.d(LOG_TAG, "파일 생성 ");
            final List<String> data = new ArrayList<String>();
            /* 완전 중요한 부분, Cloud FireStore 에서 데이터를 가져다 어떻게 내보내는지 잘 봐야함. */
            orderRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            List<String> s_addr = (List<String>) document.get("sender_addr");
                            List<String> addr = (List<String>) document.get("recipient_addr");
                            // Log.d(LOG_TAG, document.getId() + "->" + document.getData());
                            // 주문자
                            data.add((String) document.get("sender")); // 0
                            data.add((String) document.get("sender_tel")); // 1
                            data.add(s_addr.get(0)); // 2
                            data.add(s_addr.get(1)); // 3
                            data.add(s_addr.get(2)); // 4
                            // 받는사람
                            data.add((String) document.get("recipient")); // 5
                            data.add((String) document.get("recipient_tel")); // 6
                            data.add(addr.get(0)); // 7
                            data.add(addr.get(1)); // 8
                            data.add(addr.get(2)); // 9
                            // 배송상품
                            data.add((String) document.get("pear_kind")); // 10
                            data.add((String) document.get("pear_amount")); // 11
                            data.add((String) document.get("pear_box")); // 12
                            // Log.d(LOG_TAG, "data : "+ data );
                            try {
                                // 보낸사람,받는사람,받는이 주소,배송 상품 작성
                                /* 예외처리 중요! 안하면 안됨. */
                                writer.write(data.get(0) + "," + data.get(1) + "," + data.get(2) + "," + data.get(3)
                                        + "," + data.get(4) + "," + data.get(5) + "," + data.get(6) + "," + data.get(7)
                                        + "," + data.get(8) + "," + data.get(9) + "," + data.get(10) + ","
                                        + data.get(11) + "," + data.get(12) + "\n");// 한줄띄어야 표로 완성
                            } catch (IOException | NullPointerException e) {
                                Toast.makeText(OrderStatusActivity.this, "정보가 없습니다.", Toast.LENGTH_SHORT).show();
                                Log.d(LOG_TAG, "Error:: " + e.toString());
                            }
                            data.clear(); // 데이터 클리어!
                        }
                        // wirter꺼주는 양식!
                        if (writer != null) {
                            try {
                                writer.close();
                                testWrite.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.d(LOG_TAG, "error", task.getException());
                    }
                }
            });// 중요한부분끝!

            /* 파일생성완료되었으니, 공유파트 */
            Uri exportUri = FileProvider.getUriForFile(getApplicationContext(), "com.greenhelix.pear.fileprovider",
                    test); // 파일을 uri형태로 변환해준다. fileprovider 경로는 한곳으로 정해두면된다.(상단의 파일경로가 바꿔주는 거임)

            // intent를 통해서 데이터 파일을 보내준다. intent의 속성과 옵션을 설정해준다.
            Intent fileIntent = new Intent(Intent.ACTION_SEND); // 공유화면을 열어준다.(메일, 드라이버 등등)
            fileIntent.setType("text/csv"); // ?/
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "혁규 농원 주문현황 입니다!"); // 메일 작성시 메일 제목
            fileIntent.putExtra(Intent.EXTRA_TEXT, "주문정보 파일을 확인하세요. ^^! "); // 메일 작성시 메일 내용
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // uri 허가권

            fileIntent.putExtra(Intent.EXTRA_STREAM, exportUri); // 파일 uri를 넣어준다.
            startActivity(Intent.createChooser(fileIntent, "주문정보를 내보냅니다.")); // intent를 실행해주면서, 공유창 상단에 제목을 보여준다.

        } catch (FileUriExposedException | FileNotFoundException e) {
            Log.d(ERROR, "파일 이상 \n" + e.toString());
        } catch (IOException f) {
            Log.d(ERROR, "파일안써짐 \n" + f.toString());
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(LOG_TAG, "주문현황 종료 확인");
        if (doubleBackToExitPressedOnce) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(
                    OrderStatusActivity.this);
            builder.setTitle("알림");
            builder.setMessage("정말 종료하시겠습니까?");
            builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG, " 종료 합니다.");
                    ActivityCompat.finishAffinity(OrderStatusActivity.this);
                }
            }).setNegativeButton("아니요.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(LOG_TAG, "유지합니다.");

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

    /* 이 부분 빼먹으면 절대 안나옴 무조건 들어가 있어야 한다. */
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

    @Override
    protected void onResume() {
        Toast.makeText(getApplicationContext(),"완료", Toast.LENGTH_SHORT).show();
        super.onResume();
    }
    /* ------------------------------------------------- */
}
