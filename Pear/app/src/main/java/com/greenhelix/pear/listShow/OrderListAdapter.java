package com.greenhelix.pear.listShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.base.Joiner;
import com.greenhelix.pear.R;
import java.util.List;


/*
딱히, firestore 부르는 곳이 아니다. 여기는 카드에 실어줄 데이터를 정의해주고, xml과 order DAO와 연동을 해주는
다리와 같은 느낌의 코드 말그대로 어댑터이다. 통로라고 생각하자
*/

public class OrderListAdapter extends FirestoreRecyclerAdapter<Order, OrderListAdapter.OrdersHolder> {

    public OrderListAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    /*// 바구니를 여기서 쓴다. //이 부분이 DB DAO에서 정보를 담아서 넣어주는 부분이다.//원하는 형태가 있다면 여기서 변환!*/
    @Override
    protected void onBindViewHolder(@NonNull OrdersHolder holder, int position, @NonNull Order model) {
        holder.senderName.setText(model.getSender());
        holder.recipientName.setText(model.getRecipient());
        List<String> addr = model.getRecipient_addr();  //list타입이라 string으로 변환해서 넣어주었다.
        String address = Joiner.on("").join(addr);
        holder.orderAddress.setText(address);
        holder.pearKinds.setText(model.getPear_kind());
        holder.pearAmounts.setText(model.getPear_amount());
        holder.pearBoxes.setText(model.getPear_box());
    }
    @NonNull
    @Override //xml 을 가져와서 onCreate해준다.
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_final_item, parent, false);
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

