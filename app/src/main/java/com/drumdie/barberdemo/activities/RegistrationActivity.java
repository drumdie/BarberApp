package com.drumdie.barberdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.drumdie.barberdemo.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class RegistrationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    private static final int RC_SIGN_IN = 9001;
    //private static final String TAG = "MainActivity";
    private static final String TAG = "SignInActivity";
    //private FirebaseAuth mAuth;
    SignInButton signInButton;
    Button signOutButton;
    GoogleApiClient mgoogleApiClient;
    TextView statusTextView;
    //private GoogleSignInClient mgoogleSignInClient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        Toolbar toolbar = findViewById(R.id.activiy_registry_toolbar);  //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mgoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        statusTextView = findViewById(R.id.status_textView);
        signInButton = findViewById(R.id.signIn_button);
        signInButton.setOnClickListener(this);
        //mAuth = FirebaseAuth.getInstance();
        signOutButton = findViewById(R.id.signOut_button);
        signOutButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signIn_button:
                signIn();
                break;
            case R.id.signOut_button:
                signOut();
                break;
        }
    }
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mgoogleApiClient);
        //Intent signInIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG,"handleSignInResult:" + result.isSuccess() );
        if(result.isSuccess()){
            //Signed in Successfully, show authenticated UI
            GoogleSignInAccount acct = result.getSignInAccount();
            statusTextView.setText("Hola, "+ acct.getDisplayName());
        }
        else {
            Toast.makeText(this, "Registro no exitoso", Toast.LENGTH_SHORT).show();
        }
    }
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.d(TAG, "onConnectionFailed"+ connectionResult);
    }


    private void signOut(){
        Auth.GoogleSignInApi.signOut(mgoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                statusTextView.setText("Sign out");
            }
        });
    }

}
