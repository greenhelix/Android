package com.greenhelix.pear.deliver;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.common.base.Joiner;
import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;
import com.greenhelix.pear.listShow.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeliverOrderAdapter extends FirestoreRecyclerAdapter<Order, DeliverOrderAdapter.DeliverOrderHolder> {

    private static final String LOG_TAG = "ik";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        Boolean checker = false;
        List<String> deliver_addr = new ArrayList<>();
        public DeliverOrderHolder(View v){
            super(v);

            deliverLinear = v.findViewById(R.id.cardDeliverLayout);
            deliverNum = v.findViewById(R.id.tvDeliverOrderNum);
            senderName = v.findViewById(R.id.tvDeliverSenderName);
            recipientName = v.findViewById(R.id.tvDeliverRecipientName);
            orderAddress = v.findViewById(R.id.tvDeliverOrderAddress);


            deliverLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if(checker){

                        v.setBackgroundResource(R.drawable.deliver_true);
                        final ArrayList<String> test_addr =(ArrayList<String>) getSnapshots().getSnapshot(getAdapterPosition()).get("recipient_addr");
                        Log.d(LOG_TAG, "주소값을 가져왔습니다. \n" + test_addr);

                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("알림");
                        builder.setMessage("해당 주소가 맞습니까?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(v.getContext(),"배송지 좌표를 저장하였습니다.. \n 완료를 눌러주세요..",Toast.LENGTH_SHORT).show();
                                deliver_addr = test_addr;
                                Intent test = new Intent(v.getContext(), DeliverOrderActivity.class);
                                test.putExtra("navi_info", (Serializable) deliver_addr);
                                v.getContext().startActivity(test);
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(v.getContext(), "취소하였습니다.",Toast.LENGTH_SHORT).show();
                                deliver_addr = new ArrayList<String>();
                                Log.d(LOG_TAG, "주소값을 비웠다"+deliver_addr);
                                v.setBackgroundResource(R.drawable.deliver_false);
                            }
                        }).show();
                        checker = false;
                    }else{
                        v.setBackgroundResource(R.drawable.deliver_false);
                        checker = true;
                    }
                    Log.d(LOG_TAG, "카드가 어댑터에서 onclick 되었다.");
                }
            });
        } // DeliverOrderHolder END
    } // class DeliverOrderHolder END
}