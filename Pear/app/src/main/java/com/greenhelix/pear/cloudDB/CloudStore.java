package com.greenhelix.pear.cloudDB;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.greenhelix.pear.R;

import java.util.ArrayList;
import java.util.List;

public class CloudStore extends AppCompatActivity {

    private static final String TEST = "ik_test";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    List<String> senderData = new ArrayList<>();
    List<String> recipientData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        senderData = getIntent().getExtras().getStringArrayList("direct_sender");
        recipientData = getIntent().getExtras().getStringArrayList("direct_recipient");

        directAddRow();

    }
    public void directAddRow(){
        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        TableRow row = new TableRow(this);

        TextView index = new TextView(this);
        index.setText(String.valueOf(1));
        index.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(index);

        TextView recipient = new TextView(this);
        recipient.setText(recipientData.get(0));
        recipient.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(recipient);

        TextView recipientAdr = new TextView(this);
        recipientAdr.setText(recipientData.get(5));
        recipientAdr.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(recipientAdr);

        TextView sender = new TextView(this);
        sender.setText(senderData.get(0));
        sender.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row.addView(sender);

        CheckBox check = new CheckBox(this);
        row.addView(check);
        table.addView(row);
    }
}
