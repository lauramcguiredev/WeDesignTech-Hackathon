package com.example.axolotl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDevice extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference dataRef;
    private FirebaseUser user;
    TextView deviceHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        final Button addButton = findViewById(R.id.addDeviceButton);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("users").child(user.getUid()).child("devices").push();
        deviceHeader = findViewById(R.id.deviceHeader);

        EditText deviceKey = findViewById(R.id.codeField);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceK = deviceKey.getText().toString();

                dataRef.setValue(deviceK);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        final Button logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        final ImageView home = findViewById(R.id.homeIcon);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        getName(dataRef.child(user.getUid()).child("name"));
    }

    private void getName(DatabaseReference dataReference) {
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uName = dataSnapshot.getValue(String.class);
                deviceHeader.setText("Welcome " + uName + "!");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        };
        dataReference.addValueEventListener(nameListener);
    }
}