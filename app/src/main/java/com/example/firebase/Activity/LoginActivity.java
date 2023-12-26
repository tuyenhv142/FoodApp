package com.example.firebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btDangNhap;
    EditText editEmailLogin, editPwLogin;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btDangNhap = findViewById(R.id.btDangNhap);
        editEmailLogin = findViewById(R.id.editEmailLogin);
        editPwLogin = findViewById(R.id.editPwLogin);

        auth = FirebaseAuth.getInstance();

        btDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = editEmailLogin.getText().toString();
                String strPassword = editPwLogin.getText().toString();

                if(TextUtils.isEmpty(strEmail)||TextUtils.isEmpty(strPassword)){
                    Toast.makeText(LoginActivity.this,"Data is null", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(strEmail,strPassword);
                }

            }
        });
    }
    void loginUser(String Email, String Password){
        auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Sign in Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"Login Faild!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}