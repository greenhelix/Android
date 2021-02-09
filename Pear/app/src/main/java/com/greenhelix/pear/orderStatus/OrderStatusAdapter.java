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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;
import com.greenhelix.pear.listShow.Order;

import java.util.List;

/*주문 현황 - 어댑터 - 카드 양식 컨트롤*/
public class OrderStatusAdapter extends FirestoreRecyclerAdapter<Order, OrderStatusAdapter.OrdersStatusHolder> {

    private static final String LOG_TAG = "ik";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    //이부분은 어댑터 사용 액티비티에서 끌어다 쓴다.
    public OrderStatusAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    //그 다음 데이터바구니에 있는 것들을 가져다 끌어온다.
    @Override
    protected void onBindViewHolder(@NonNull OrdersStatusHolder holder, int position, @NonNull Order model) {

        /*해당 주문의 문서의 스냅샷 가져올 수 있다.!!*/
        String id = getSnapshots().getSnapshot(position).getId();
        List<String> tempAddress = model.getRecipient_addr();
        String address = Joiner.on("").join(tempAddress);

        holder.orderNum.setText(id);
        holder.senderName.setText(model.getSender());
        holder.recipientName.setText(model.getRecipient());
        holder.status.setText(model.getStatus());
        holder.orderAddress.setText(address);

        if(model.getStatus().equals("준비 중")) {
            holder.status.setBackgroundResource(R.drawable.status1);
        }
        else if(model.getStatus().equals("준비 완료")) {
            holder.status.setBackgroundResource(R.drawable.status2);
        }
        else if(model.getStatus().equals("배송 중")) {
            holder.status.setBackgroundResource(R.drawable.status3);
        }
        else if(model.getStatus().equals("완 료")) {
            holder.status.setBackgroundResource(R.drawable.status4);
        }
    }

    //마지막으로 해당 카드 레이아웃들을 연결 시켜주고 어댑터를 마쳐준다.
    @NonNull
    @Override
    public OrdersStatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_status_item, parent, false);
        return new OrdersStatusHolder(v);
    }

    //가장 먼저, 화면에 넣을 값들을 설정해준다.
    public class OrdersStatusHolder extends RecyclerView.ViewHolder{
        TextView orderNum, senderName, recipientName, orderAddress, status;
        Boolean expandable = false;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        Button status1, status2, status3, status4;

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

            // 카드 상단 터치시 카드 화면 확장 시키기
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 기본 카드 양식에서 원하는 부분을 보였다 안보였다 하는 기능이다.
                    if(expandable){
                        expandable = false;
                        expandableLayout.setVisibility(View.GONE);
                    }else{
                        expandable = true;
                        expandableLayout.setVisibility(View.VISIBLE);
                    }
                    Log.d(LOG_TAG, "카드가 어댑터에서 onclick 되었다.");
                }
            });

            status1 = v.findViewById(R.id.btn_change_status1);
            statusChange(status1);
            status2 = v.findViewById(R.id.btn_change_status2);
            statusChange(status2);
            status3 = v.findViewById(R.id.btn_change_status3);
            statusChange(status3);
            status4 = v.findViewById(R.id.btn_change_status4);
            statusChange(status4);

        }

        //button클릭 시 색상 및 DB 값 변경 메서드
        public void statusChange(final Button btnStatus){
            btnStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*이게 있어야 잘 바뀜*/
                    notifyItemChanged(getAdapterPosition());
                    String orderID = getSnapshots().getSnapshot(getAdapterPosition()).getId();
                    final DocumentReference changeStat = db.collection("pear_orders").document(orderID);
                    changeStat.update("status", btnStatus.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if(btnStatus == status1) {
                                        status.setBackgroundResource(R.drawable.status1);
                                    }
                                    else if(btnStatus == status2) {
                                        status.setBackgroundResource(R.drawable.status2);
                                    }
                                    else if(btnStatus == status3) {
                                        status.setBackgroundResource(R.drawable.status3);
                                    }
                                    else if(btnStatus == status4) {
                                        status.setBackgroundResource(R.drawable.status4);
                                    }
                                    status.setText(btnStatus.getText());
                                    Log.d(LOG_TAG, "주문상태 변화\n 성공!::"+status.getText());
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
        }
    }

    //삭제 메서드 생성! reference에서 업데이트 사용가능하다! delete로 삭제명령 함
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}
