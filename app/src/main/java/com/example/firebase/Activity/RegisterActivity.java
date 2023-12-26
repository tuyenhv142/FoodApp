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

public class RegisterActivity extends AppCompatActivity {

    EditText editEmail, editPassword, editRePassword;
    Button btDangky;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editRePassword = findViewById(R.id.editRePassword);
        btDangky = findViewById(R.id.btDangKy);

        auth = FirebaseAuth.getInstance();


        btDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StrEmail = editEmail.getText().toString();
                String StrPassword = editPassword.getText().toString();
                String StrRePassword = editRePassword.getText().toString();

                if(StrPassword.equals(StrRePassword)){
                    if(TextUtils.isEmpty(StrEmail)||TextUtils.isEmpty(StrPassword)){
                        Toast.makeText(RegisterActivity.this,"Data is null", Toast.LENGTH_SHORT).show();
                    }else if(StrPassword.length()<6){
                        Toast.makeText(RegisterActivity.this,"Password is too short", Toast.LENGTH_SHORT).show();
                    }else {
                        Register(StrEmail,StrPassword);
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"Confirm password is not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private  void  Register(String txtEmail, String txtPassword){
        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Register Successfuly",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this,"Register Faild!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void login(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}