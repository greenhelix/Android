package com.greenhelix.pear.deliver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.base.Joiner;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;
import com.greenhelix.pear.listShow.Order;
import com.greenhelix.pear.orderStatus.OrderStatusAdapter;

import java.util.List;

public class DeliverOrderAdapter extends FirestoreRecyclerAdapter<Order, DeliverOrderAdapter.DeliverStatusHolder> {

    private static final String LOG_TAG = "ik";
    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    Boolean expandable = false;
    
    // 카드양식의 내용을 넣을때, onCreate와 같다.
    public class DeliverStatusHolder extends RecyclerView.ViewHolder{
        TextView deliverNum , senderName , recipientName, orderAddress;
        LinearLayout deliverLinear;

        public DeliverStatusHolder(View v){
            super(v);
            deliverNum = v.findViewById(R.id.tvDeliverOrderNum);
            senderName = v.findViewById(R.id.tvDeliverSenderName);
            recipientName = v.findViewById(R.id.tvDeliverRecipientName);
            orderAddress = v.findViewById(R.id.tvDeliverOrderAddress);
            deliverLinear = v.findViewById(R.id.cardDeliverLayout);
            
            //배달 주문 확인에서 해당 카드를 선택하면 정보를 네비게이션으로 보내는 부분
            deliverLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        } // DeliverStatusHolder END
    }

    @Override
    public void onBindViewHolder(@NonNull DeliverStatusHolder holder, int position, @NonNull Order model) {
        String id = getSnapshots().getSnapshot(position).getId();

         /*카드에 들어가는 정보의 모음*/
        holder.deliverNum.setText(id);
        holder.senderName.setText(model.getSender());
        holder.recipientName.setText(model.getRecipient());
        // 주소 값은 리스트로 형 변환한뒤, 띄어준다.
        List<String> tempAddress = model.getRecipient_addr();
        String address = Joiner.on("").join(tempAddress);
        holder.orderAddress.setText(address);
    }
    @NonNull
    @Override
    public DeliverStatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliver_card,parent,false);
        return new DeliverStatusHolder(v);
    }

    public DeliverStatusAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

}