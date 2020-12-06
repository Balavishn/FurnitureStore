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


public class Login extends AppCompatActivity {

    EditText email,password;
    FirebaseAuth auth;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.Lusername);
        password=(EditText)findViewById(R.id.Lpass);
        auth = FirebaseAuth.getInstance();
        register=(TextView)findViewById(R.id.lsignin);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        /*forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Reset_Password.class));
            }
        });*/
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Navigation_furn_draw.class));
            finish();
        }

    }
    public void login(View v){
        String e=email.getText().toString();
        String p=password.getText().toString();
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
        auth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successgul",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Navigation_furn_draw.class));

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });
    }
}