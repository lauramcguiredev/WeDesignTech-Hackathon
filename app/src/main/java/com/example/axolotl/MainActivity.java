package com.example.axolotl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference dataRef;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("users");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        welcomeMsg = findViewById(R.id.deviceHeader);

        final Button logout = findViewById(R.id.logoutButton);
        CardView register = findViewById(R.id.registerCard);
        CardView myDevices = findViewById(R.id.devicesCard);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddDevice.class));
            }
        });

        myDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DeviceListActivity.class));
            }
        });

        getName(dataRef.child(user.getUid()).child("name"));

        final ImageView home = findViewById(R.id.homeIcon);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void getName(DatabaseReference dataReference) {
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uName = dataSnapshot.getValue(String.class);
                welcomeMsg.setText("Welcome " + uName + "!");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        };
        dataReference.addValueEventListener(nameListener);
    }
}