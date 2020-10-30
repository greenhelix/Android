package com.greenhelix.pear;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;
import javax.annotation.Nonnull;
import com.google.android.gms.tasks.*;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class Cloud_test01 {
    private static final String TEST = "ik_test";
    @Test
    FirebaseFirestore dbTest = FirebaseFirestore.getInstance();
    Map<String, Object> test = new HashMap<>();
    test.put("t1","hey");
    test.put("t2","show");
    test.put("t3","wow");

    dbTest.collection("tests").add(test)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TEST,"show ID: "+documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TEST, "Error !!", e);
                }
            });
}




//데이터 불러오기 테스
//        FirebaseFirestore dbTest = FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(true)
//                .build();
//        dbTest.setFirestoreSettings(settings);
//
//
//        DocumentReference showData=dbTest.document("pear_orders/order1");
//        Log.d(TEST, "문서가져옴"+showData.toString());
//
//        showData.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    e1 = documentSnapshot.getString("sender");
//                    e2 = documentSnapshot.getString("recipient");
//                }
//            }
//        });
//        Log.d(TEST,"show: "+e1+" ::"+e2);
//

