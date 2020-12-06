package com.example.furniturestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Register extends AppCompatActivity {

    EditText email,password,cpass;
    FirebaseAuth auth;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email=(EditText)findViewById(R.id.Rusername);
        password=(EditText)findViewById(R.id.Rpass);
        cpass=(EditText)findViewById(R.id.Repass);
        auth = FirebaseAuth.getInstance();
        login=(TextView)findViewById(R.id.lsignin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"login",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


    }
    public void register(View v){
        String e=email.getText().toString();
        String p=password.getText().toString();
        Toast.makeText(getApplicationContext(),"login",Toast.LENGTH_SHORT).show();
        if(e.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            email.setError("please enter valid email");
            email.requestFocus();
            return;
        }
        if(p.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(p.length()<6){
            password.setError("Minimum length is greater than 6 character");
            password.requestFocus();
            return;
        }
        if (!p.equals(cpass.getText().toString())){
            cpass.setError("Mismatch Password");
            cpass.requestFocus();
            return;
        }
        auth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"user created successful",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),Navigation_furn_draw.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"you are already registered",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}