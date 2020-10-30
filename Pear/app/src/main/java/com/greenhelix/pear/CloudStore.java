package com.greenhelix.pear;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CloudStore extends AppCompatActivity {

    private static final String TEST = "ik_test";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    //test
    DocumentReference showOrder = db.collection("pear_orders").document("order1");






}
