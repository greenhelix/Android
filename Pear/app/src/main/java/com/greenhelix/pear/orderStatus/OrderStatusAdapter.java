package com.greenhelix.pear.orderStatus;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Joiner;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;

import java.util.List;

public class OrderStatusAdapter extends FirestoreRecyclerAdapter<NowOrder, OrderStatusAdapter.OrdersStatusHolder> {
    private static final String LOG_TAG = "ik";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //가장 먼저, 화면에 넣을 값들을 설정해준다.
    public class OrdersStatusHolder extends RecyclerView.ViewHolder{
        TextView orderNum, senderName, recipientName, orderAddress, pearKinds, pearAmounts, pearBoxes, status;
        Boolean expandable = false;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        Button status1;
        Button status2;
        Button status3;
        Button status4;

        public OrdersStatusHolder(View v){
            super(v);
            orderNum = v.findViewById(R.id.tv_status_orderNum);
            senderName = v.findViewById(R.id.tv_status_senderName);
            recipientName = v.findViewById(R.id.tv_status_recipientName);
            orderAddress = v.findViewById(R.id.tv_status_orderAddress);

            status = v.findViewById(R.id.tv_status);

            linearLayout = v.findViewById(R.id.cardBasicLayout);
            expandableLayout = v.findViewById(R.id.cardExpandableLayout);
            expandableLayout.setVisibility(View.GONE);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(expandable){
                        expandable = false;
                        expandableLayout.setVisibility(View.GONE);
                    }else{
                        expandable = true;
                        expandableLayout.setVisibility(View.VISIBLE);
                    }
                    Log.d(LOG_TAG, "카드가 어댑터에서 onclick 되었다.");

                    //if(position != RecyclerView.NO_POSITION && listener != null){
                        //이렇게 선택하면, 문서 정보와 해당 인덱스를 holder를 통해서 설정해준다.
                        //이후, activity에서 이 메서드를 활용하여 원하는 값을 한다. ( 그냥 recyclerview의 경우 이것을 여기서 해도 되는데,
                        //firestore recyclerview는 이렇게 문서정보를 가져다가 하면 되는듯.. 방법은 있으나 내가 모르는것 같다.
                        //listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    //}
                }
            });

            // 확장 레이아웃이 열리면, 배송상태의 버튼이 보인다.
            // 이것을 선택하면 해당 주문의 배송상태를 바꿔준다. 나열된 배송상태 리스트는 button이고, 이것을 클릭하면,
            // 현재 주문의 배송상태인 텍스트뷰가 색상과 글자가 바뀌면 된다.


            status1 = v.findViewById(R.id.btn_change_status1);
            status1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String orderID = getSnapshots().getSnapshot(getAdapterPosition()).getId();
                    final DocumentReference changeStat = db.collection("pear_orders").document(orderID);
                    changeStat.update("status", status1.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(LOG_TAG, "주문상태 변화\n 성공!");
                            status.setBackgroundResource(R.drawable.status1);
                            status.setText(status1.getText());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(LOG_TAG, "주문상태 변화\n 실패!");
                        }
                    });

                }
            });
            status2 = v.findViewById(R.id.btn_change_status2);
            status2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String orderID = getSnapshots().getSnapshot(getAdapterPosition()).getId();
                    final DocumentReference changeStat = db.collection("pear_orders").document(orderID);
                    changeStat.update("status", status2.getText().toString());
                    status.setBackgroundResource(R.drawable.status2);
                    status.setText(status2.getText());
                }
            });
            status3 = v.findViewById(R.id.btn_change_status3);
            status4 = v.findViewById(R.id.btn_change_status4);

        }
//        public void onClickStatus(final Button bt){
//            bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String nowStatus = "";
//                    String orderID = getSnapshots().getSnapshot(getAdapterPosition()).getId();
//                    DocumentReference changeStat = db.collection("pear_orders").document(orderID);
//                    if(bt == status1){
//                        Log.d(LOG_TAG, "주문상태 변경 버튼1 이 눌렸어요");
//                        nowStatus = status1.getText().toString();
//                        Log.d(LOG_TAG, "주문상태는 ::" + nowStatus+ "\n 주문번호:: "+ orderID);
//                        changeStat.update("status", nowStatus);
//                        status.setText(status1.getText());
//                        status.setBackgroundResource(R.drawable.status1);
//                    }
//                    else if(bt == status2){
//                        Log.d(LOG_TAG, "주문상태 변경 버튼2 이 눌렸어요");
//                        nowStatus = status2.getText().toString();
//                        Log.d(LOG_TAG, "주문상태는 ::" + nowStatus+ "\n 주문번호:: "+ orderID);
//                        changeStat.update("status", nowStatus);
//                        status.setText(status2.getText());
//                        status.setBackgroundResource(R.drawable.status2);
//                    }
//                    else if(bt == status3){
//                        Log.d(LOG_TAG, "주문상태 변경 버튼3 이 눌렸어요");
//                        nowStatus = status3.getText().toString();
//                        Log.d(LOG_TAG, "주문상태는 ::" + nowStatus+ "\n 주문번호:: "+ orderID);
//                        changeStat.update("status", nowStatus);
//                        status.setText(status3.getText());
//                        status.setBackgroundResource(R.drawable.status3);
//                    }
//                    else if(bt == status4){
//                        Log.d(LOG_TAG, "주문상태 변경 버튼4 이 눌렸어요");
//                        nowStatus = status4.getText().toString();
//                        Log.d(LOG_TAG, "주문상태는 ::" + nowStatus+ "\n 주문번호:: "+ orderID);
//                        changeStat.update("status", nowStatus);
//
//                    }
//                    status.setText(bt.getText());
//                    status.setBackgroundResource(R.drawable.bt);
//                }
//            });
//        }
    }

    //그 다음 데이터바구니에 있는 것들을 가져다 끌어온다.
    @Override
    protected void onBindViewHolder(@NonNull OrdersStatusHolder holder, int position, @NonNull NowOrder model) {

        /*해당 주문의 문서의 스냅샷 가져올 수 있다.!!*/
        String id = getSnapshots().getSnapshot(position).getId();
        holder.orderNum.setText(id);

        holder.senderName.setText(model.getSender());
        holder.recipientName.setText(model.getRecipient());
        holder.status.setText(model.getStatus());
        List<String> tempAddress = model.getRecipient_addr();
        String address = Joiner.on("").join(tempAddress);
        holder.orderAddress.setText(address);

    }

    //마지막으로 해당 카드 레이아웃들을 연결 시켜주고 어댑터를 마쳐준다.
    @NonNull
    @Override
    public OrdersStatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_status_item, parent, false);
        return new OrdersStatusHolder(v);
    }

    //삭제 메서드 생성! reference에서 업데이트 사용가능하다! delete로 삭제명령 함
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    //이부분은 어댑터 사용 액티비티에서 끌어다 쓴다.
    public OrderStatusAdapter(@NonNull FirestoreRecyclerOptions<NowOrder> options) {
        super(options);
    }
}
