package com.drumdie.barberdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.drumdie.barberdemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_layout);

        Toolbar toolbar = findViewById(R.id.user_activity_toolbar);  //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    user = FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("Users");
    userID = user.getUid();

    final TextView helloUserTextView = findViewById(R.id.hello_user);
    final TextView fullNameTextView = findViewById(R.id.fullname);
    final TextView emailAddressTextView = findViewById(R.id.email_address);

    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            User userProfile = snapshot.getValue(User.class);
            if(userProfile != null){
                String fullName = userProfile.fullName;
                String email = userProfile.email;

                helloUserTextView.setText("Hola, " + fullName + "!");
                fullNameTextView.setText(fullName);
                emailAddressTextView.setText(email);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(UserActivity.this, "Ha ocurrido un error!", Toast.LENGTH_LONG).show();
        }
    });

    }
}
