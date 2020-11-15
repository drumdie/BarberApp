package com.drumdie.barberdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.drumdie.barberdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register, forgotPassword;
    private EditText emailEditText, passwordEditText;
    Button signInBtn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity_layout);
        Toolbar toolbar = findViewById(R.id.login_activity_toolbar);  //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        emailEditText = findViewById(R.id.user_email_editText);
        passwordEditText = findViewById(R.id.user_password_editText);

        signInBtn = findViewById(R.id.signIn_button);
        signInBtn.setOnClickListener(this);

        register = findViewById(R.id.register_textView_loginAct);
        register.setOnClickListener(this);

        forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(this);

        progressBar= findViewById(R.id.progress_bar_logIn);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signIn_button:
                userLogin();
                //startActivity(new Intent(LoginActivity.this, UserActivity.class));
                break;
            case R.id.register_textView_loginAct:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
                break;


        }
    }
    private void userLogin(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailEditText.setError("Email requerido!");
            emailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Por favor ingrese E-mail válido");
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            passwordEditText.setError("password requerido");
            passwordEditText.requestFocus();
            return;
        }
        if (password.length() <6) {
            passwordEditText.setError("Password requiere mínimo 6 caracteres!");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        //redirect to user profile
                        startActivity(new Intent(LoginActivity.this, UserActivity.class));

                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, " Revisa tu Email para verificar tu cuenta!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, " Fallo al acceder, verifica tus datos!", Toast.LENGTH_LONG).show();
                }
              }
        });

    }

}
