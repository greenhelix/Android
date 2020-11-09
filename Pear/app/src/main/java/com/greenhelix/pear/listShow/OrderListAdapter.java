package com.greenhelix.pear.listShow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.greenhelix.pear.R;
import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrdersHolder>{

    private Context context;
    private List<Integer> cards; //카드들을 담는 그릇
    private int index;

    public OrderListAdapter(Context context, ArrayList<Integer> cards, int index){
        this.context = context;
        this.cards = cards;
        this.index = index;
    }

    public class OrdersHolder extends RecyclerView.ViewHolder{
        TextView orderNum, senderName, recipientName, orderAddress,pearKinds, pearAmounts, pearBoxes;
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
    @NonNull
    @Override
    public OrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_direct_item, parent, false);
        OrdersHolder holder = new OrdersHolder(v);
        return holder;
    }

    @Override //카드안에 것들을 묶어준다.
    public void onBindViewHolder(@NonNull OrdersHolder holder, int position) {
        final Integer inCard = cards.get(position);
        holder.senderName.setText(inCard);
        holder.recipientName.setText(inCard);
        holder.orderAddress.setText(inCard);
        holder.pearKinds.setText(inCard);
        holder.pearAmounts.setText(inCard);
        holder.pearBoxes.setText(inCard);
    }

    @Override //카드들의 전체 사이즈를 구한다.
    public int getItemCount() {
        return this.cards.size();
    }
}// OrderListAdapter END

