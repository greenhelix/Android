package com.google.field_ingresshelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    // DataShow.java와 같은 형식으로 선언해준다. 추가로 context를 선언.
    public ArrayList<String> portalNames;
    public ArrayList<String> portalPositions;
    public Context context;

    /* class명과 같은 메서드를 선언하고 파라미터를 담아주는 배열리스트 형식으로 넣어준다. */
    public CustomAdapter(Context context, ArrayList<String> portalNames, ArrayList<String> portalPositions){
        this.context = context;
        this.portalNames = portalNames;
        this.portalPositions = portalPositions;
    }

    @Override //cardView를 통해 보여줄 레이아웃을 불러오는 부분.
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //layoutInflator를 통해서 view에 띄어준다.
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout_data, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        holder.portal_name.setText(portalNames.get(position));
        holder.portal_position.setText(portalPositions.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, portalNames.get(position), Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, (position+1)+"번째 포털", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return portalNames.size();
    }



/* cardView에 있는 텍스트뷰들의 속성값에 넣어줄 것들을 결정해주는 class이다.
    * 1. 종류를 추가하고 싶다면, 일단 cardView가 있는 곳에 textView를 추가한다.
    * 2. 그리고 여기에 선언을 해준다.
    * 3. onBindViewHolder에 포함시켜서 arrayList에 들어있는 값들을 불러와서 넣어주게 선언해준다. */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView portal_name, portal_position;
        public MyViewHolder(View itemView){
            super(itemView);

            //get the reference of item view's
            portal_name = (TextView) itemView.findViewById(R.id.tv_portalName);
            portal_position = (TextView) itemView.findViewById(R.id.tv_portalPosition);
        }
    }


} //end of class Adapter
