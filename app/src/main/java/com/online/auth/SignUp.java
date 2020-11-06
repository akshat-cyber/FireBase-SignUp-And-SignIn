package com.online.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends Activity {
    private Button signUpButton;
    public static final String TAG = "Value";
    private FirebaseAuth mAuth;
    private Button signUpButtonGoogle;
    private EditText passwordS;
    private EditText emailS;
    private String Email;
    private String Pass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        init();
        mAuth = FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(v -> {
            if( TestForText() ){
                return;
            }
            Email = emailS.getText().toString();
            Pass = passwordS.getText().toString();
            SignUp(Email, Pass);
        });
        signUpButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init() {
        signUpButton = findViewById(R.id.signUp);
        signUpButtonGoogle = findViewById(R.id.signUpWithGoogle);
        passwordS= findViewById(R.id.PasswordS);
        emailS= findViewById(R.id.emailS);
    }
    private void SignUp(String User, String Pass){
        mAuth.createUserWithEmailAndPassword(User, Pass)
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(getApplicationContext(), "Authentication passed.",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                try {
                                    throw task.getException();
                                } catch(Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
    }
    private boolean TestForText() {
        if(TextUtils.isEmpty(emailS.getText().toString()) || TextUtils.isEmpty(passwordS.getText().toString())){
            if(TextUtils.isEmpty(emailS.getText().toString())){
                emailS.setError("Email Field Cannot be left empty");
            }if(TextUtils.isEmpty(passwordS.getText().toString())){
                passwordS.setError("Password Field Cannot be left empty");
            }
            return true;
        } return false;
    }

}
