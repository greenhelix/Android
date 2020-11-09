package com.greenhelix.pear.listShow;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhelix.pear.R;

import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    /*레이아웃매니저를 통해서 카드들과 그 안에 있는 데이터들을 원하는 화면에 쫘악 띄어준다.*/

    private RecyclerView recyclerOrderView;
    private LinearLayoutManager layoutManager;
    private List<String> orderInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pear_list);
    }
}
