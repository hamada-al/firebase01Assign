package com.example.firebase01assign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText  name;
    EditText  number;
    EditText  address;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListView listView;
    ArrayList<String> myList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name =findViewById(R.id.edit1);
        number =findViewById(R.id.edit2);
        address =findViewById(R.id.edit3);
        listView = findViewById(R.id.listView);
    }

    public void saveFirbase(View v){
       String userName =name.getText().toString();
       String userNumber =name.getText().toString();
       String userAddress =name.getText().toString();
        Map<String, Object> contacts = new HashMap<>();
        contacts.put("name", userName);
        contacts.put("number", userNumber);
        contacts.put("address", userAddress);
        db.collection("contacts")
                .add(contacts)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

    public void readFirbase(View v){
        db.collection("contacts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String data = document.getId() + " => " + document.getData().toString() ;
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                myList.add(data);
                            }
                            ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item, myList);
                            listView.setAdapter(arrayAdapter);
                        } else {
                            Log.d("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}