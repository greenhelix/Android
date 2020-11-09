package com.greenhelix.pear.listShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.greenhelix.pear.R;

/*
딱히, firestore 부르는 곳이 아니다. 여기는 카드에 실어줄 데이터를 정의해주고, xml과 order DAO와 연동을 해주는
다리와 같은 느낌의 코드 말그대로 어댑터이다. 통로라고 생각하자
*/

public class OrderListAdapter extends FirestoreRecyclerAdapter<Order, OrderListAdapter.OrdersHolder> {

    public OrderListAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    @Override  // 바구니를 여기서 쓴다.
    protected void onBindViewHolder(@NonNull OrdersHolder holder, int position, @NonNull Order model) {
        holder.senderName.setText(model.getSenderName());
        holder.recipientName.setText(model.getRecipientName());
        holder.orderAddress.setText(model.getAddress());
        holder.pearKinds.setText(model.getPearKind());
        holder.pearAmounts.setText(model.getPearAmount());
        holder.pearBoxes.setText(model.getPearBox());
    }
    @NonNull
    @Override //xml 을 가져와서 onCreate해준다.
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_direct_item, parent, false);
        return new OrdersHolder(v);
    }

    public class OrdersHolder extends RecyclerView.ViewHolder{
        TextView orderNum, senderName, recipientName, orderAddress, pearKinds, pearAmounts, pearBoxes;
        public OrdersHolder(View v){
            super(v);
            orderNum = v.findViewById(R.id.tv_list_orderNum);
            senderName = v.findViewById(R.id.tv_list_senderName);
            recipientName = v.findViewById(R.id.tv_list_recipientName);
            orderAddress = v.findViewById(R.id.tv_list_orderAddress);
            pearKinds = v.findViewById(R.id.tv_list_pearKinds);
            pearAmounts = v.findViewById(R.id.tv_list_pearAmounts);
            pearBoxes = v.findViewById(R.id.tv_list_pearBoxes);
        }
    }//OrdersHolder END
}// OrderListAdapter END

