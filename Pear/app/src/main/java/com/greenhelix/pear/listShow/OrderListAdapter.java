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

/*단순히 데이터를 가져오는 카드 양식 어댑터*/
public class OrderListAdapter extends FirestoreRecyclerAdapter<Order, OrderListAdapter.OrdersHolder> {

    public OrderListAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    /*// 바구니를 여기서 쓴다. //이 부분이 DB DAO에서 정보를 담아서 넣어주는 부분이다.//원하는 형태가 있다면 여기서 변환!*/
    @Override
    protected void onBindViewHolder(@NonNull OrdersHolder holder, int position, @NonNull Order model) {
        //대기하던 holder부분에 order 의 값들을 넣어준다.(데이터바구니에서 데이터를 꺼내 준비된 틀에 넣는 메서드)
        //Order.class에서 지정한 Get메서드를 통해 데이터의 값을 가져온다. model.get원하는것().
        /*아직 주문번호 문서 명으로 해야함아직 안함*/

        holder.senderName.setText(model.getSender());
        holder.recipientName.setText(model.getRecipient());
        holder.pearKinds.setText(model.getPear_kind());
        holder.pearAmounts.setText(model.getPear_amount());
        holder.pearBoxes.setText(String.format("%s 상자", model.getPear_box())); //string.format() 상자단위 붙여주기위해사용
        List<String> tempAddress = model.getRecipient_addr();  //list타입이라 string으로 변환해서 넣어주었다.
        String address = Joiner.on("").join(tempAddress);
        holder.orderAddress.setText(address);

    }

    @NonNull
    @Override //xml 틀을 가져와서 onCreate해준다.
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_final_item, parent, false);
        return new OrdersHolder(v);
    }

    public static class OrdersHolder extends RecyclerView.ViewHolder{
        //holder쪽에서 해당 xml의 값들을 연결해두고 기다린다.
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

