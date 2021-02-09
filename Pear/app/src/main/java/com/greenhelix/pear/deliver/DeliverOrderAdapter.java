package com.greenhelix.pear.deliver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.base.Joiner;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;
import com.greenhelix.pear.listShow.Order;

import java.util.List;

public class DeliverOrderAdapter extends FirestoreRecyclerAdapter<Order, DeliverOrderAdapter.DeliverOrderHolder> {

    private static final String LOG_TAG = "ik";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Boolean isClick = true;
    public DeliverOrderAdapter(@NonNull FirestoreRecyclerOptions<Order> options){
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeliverOrderHolder holder, final int position, @NonNull final Order model) {
        /*카드에 들어가는 정보의 모음*/
        String id = getSnapshots().getSnapshot(position).getId();
        final List<String> tempAddress = model.getRecipient_addr();
        final String address = Joiner.on("").join(tempAddress);

        holder.deliverNum.setText(id);
        holder.senderName.setText(model.getSender());
        holder.recipientName.setText(model.getRecipient());
        holder.orderAddress.setText(address);
        holder.deliverLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //해당 주문의 배경을 바꿔서 클릭상태로 만들어준다.
                if(isClick){
                    holder.deliverLinear.setBackgroundColor(Color.parseColor("#72DAE8"));
                    Log.d(LOG_TAG, "정보가져오기 :"+ tempAddress);
                    Log.d(LOG_TAG, "위치 :"+ position);
                    isClick = false;
                }else{
                    holder.deliverLinear.setBackgroundColor(Color.parseColor("#ffffff"));
                    isClick = true;
                }
                Context context = v.getContext();
            }
        });
    }

    @NonNull
    @Override
    public DeliverOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliver_card,parent,false);
        return new DeliverOrderHolder(v);
    }

    // 카드양식의 내용을 넣을때, onCreate와 같다.
    public class DeliverOrderHolder extends RecyclerView.ViewHolder{
        TextView deliverNum , senderName , recipientName, orderAddress;
        LinearLayout deliverLinear;

        public DeliverOrderHolder(View v){
            super(v);

            deliverLinear = v.findViewById(R.id.cardDeliverLayout);
            deliverNum = v.findViewById(R.id.tvDeliverOrderNum);
            senderName = v.findViewById(R.id.tvDeliverSenderName);
            recipientName = v.findViewById(R.id.tvDeliverRecipientName);
            orderAddress = v.findViewById(R.id.tvDeliverOrderAddress);

            //배달 주문 확인에서 해당 카드를 선택하면 정보를 네비게이션으로 보내는 부분
//            deliverLinear.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Boolean isClick = true;
//                    //해당 주문의 배경을 바꿔서 클릭상태로 만들어준다.
//                    if(isClick){
//                        deliverLinear.setBackgroundColor(Color.parseColor("#72DAE8"));
//                        isClick = false;
//                    }
//
//                }
//            });
        } // DeliverOrderHolder END
    } // class DeliverOrderHolder END
}