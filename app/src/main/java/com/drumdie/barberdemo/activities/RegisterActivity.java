package com.drumdie.barberdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;


    //private static final String TAG = "RegisterActivity";
    private EditText register_name_field, register_email_field, password_field;
    private ProgressBar progressBar;
    private Button register;

    private TextView linkLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.register_user_btn);
        register.setOnClickListener(this);

        linkLogin = findViewById(R.id.link_login);
        linkLogin.setOnClickListener(this);

        register_email_field = findViewById(R.id.register_email_field);
        register_name_field = findViewById(R.id.register_name_field);
        password_field = findViewById(R.id.password_field);

        progressBar = findViewById(R.id.progress_bar_register);

        Toolbar toolbar = findViewById(R.id.register_activity_toolbar);  //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_user_btn:
                registerUser();
           //     startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.link_login:
                onBackPressed();
                //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
        }
    }
    private void registerUser(){
        String fullName = register_name_field.getText().toString().trim();
        String email = register_email_field.getText().toString().trim();
        String password = password_field.getText().toString().trim();

        if(fullName.isEmpty()){
            register_name_field.setError("Nombre completo requerido");
            register_name_field.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            register_email_field.setError("e-mail requerido");
            register_email_field.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            register_email_field.setError("E-mail válido requerido");
            register_email_field.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            password_field.setError("password requerido");
            password_field.requestFocus();
            return;
        }
        if (password.length()<6){
            password_field.setError("Password requiere mínimo 6 caracteres!");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, " Usuario creado de manera exitosa!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        // redirect to login layout

                                    }else {
                                        Toast.makeText(RegisterActivity.this, " Fallo en el registro, prueba de nuevo!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }
                });

    }
}
