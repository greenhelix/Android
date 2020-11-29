package com.greenhelix.pear.orderStatus;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.base.Joiner;
import com.google.firebase.firestore.DocumentSnapshot;
import com.greenhelix.pear.R;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusAdapter extends FirestoreRecyclerAdapter<NowOrder, OrderStatusAdapter.OrdersStatusHolder> {
    private static final String LOG_TAG = "ik";
    private OnOrderClickListener listener;

    //가장 먼저, 화면에 넣을 값들을 설정해준다.
    public class OrdersStatusHolder extends RecyclerView.ViewHolder{
        TextView orderNum, senderName, recipientName, orderAddress, pearKinds, pearAmounts, pearBoxes, status;
        Boolean expandable = false;
        RelativeLayout expandableLayout;
        public OrdersStatusHolder(View v){
            super(v);
            orderNum = v.findViewById(R.id.tv_status_orderNum);
            senderName = v.findViewById(R.id.tv_status_senderName);
            recipientName = v.findViewById(R.id.tv_status_recipientName);
            orderAddress = v.findViewById(R.id.tv_status_orderAddress);

            status = v.findViewById(R.id.tv_status);
            expandableLayout = v.findViewById(R.id.cardExpandableLayout);
            expandableLayout.setVisibility(View.GONE);
            v.setOnClickListener(new View.OnClickListener() {
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
        }
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

    //이부분은 어댑터 사용 액티비티에서 끌어다 쓴다.
    public OrderStatusAdapter(@NonNull FirestoreRecyclerOptions<NowOrder> options) {
        super(options);
    }

    public interface OnOrderClickListener {
        //내가 원하는 것으로 클릭시 정보를 가져온다. 여기서는 문서와 해당 인덱스를 가져온다.
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    // 커스텀 마이징한것이다.
    public void setOnCardClickListener(OnOrderClickListener listener){
        this.listener = listener;
    }
}
