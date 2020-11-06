package com.online.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static final String TAG = "Value";
    public static String CURRENT_USER_ID;
    private Button signInButton;
    private Button signInButtonGoogle;
    private TextView noAccount;
    private TextView forgotPass;
    private EditText password;
    private EditText email;
    private String Pass;
    private String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        init();
        signInBFunction();
        signInButtonGoogleFunction();
        noAccountFunction();
        forgotPassFunction();
    }

    private void forgotPassFunction() { forgotPass.setOnClickListener(v -> {
        Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
    }); }

    private void noAccountFunction() {   noAccount.setOnClickListener(v -> {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }); }

    private void signInButtonGoogleFunction() {  signInButtonGoogle.setOnClickListener(v -> {
        if( TestForText() ){
            Toast.makeText(this, "Empty Field", Toast.LENGTH_SHORT).show();
            return;
        }
        Email = email.getText().toString();
        Pass = password.getText().toString();
        Toast.makeText(this, "Under Construction", Toast.LENGTH_SHORT).show();
    }); }

    private void signInBFunction() {
        signInButton.setOnClickListener(v -> {
        if( TestForText() ){
            return;
        }
        Email = email.getText().toString();
        Pass = password.getText().toString();
        SignIn(Email, Pass);
    });
    }

    private boolean TestForText() {
        if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
            if(TextUtils.isEmpty(email.getText().toString())){
                email.setError("Email Field Cannot be left empty");
            }if(TextUtils.isEmpty(password.getText().toString())){
                password.setError("Password Field Cannot be left empty");
            }
            return true;
        } return false;
    }

    private void SignIn (String user, String Pass){
        mAuth.signInWithEmailAndPassword(user, Pass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signIn :success");
                Toast.makeText(getApplicationContext(), "Authentication passed.",
                        Toast.LENGTH_SHORT).show();
                 FirebaseUser user1 = mAuth.getCurrentUser();
                 logInStart(user1);

            } else {
                try {
                    throw task.getException();
                } catch(FirebaseAuthWeakPasswordException e) {
                    Toast.makeText(this, "Weak Password", Toast.LENGTH_SHORT).show();
                } catch(FirebaseAuthInvalidCredentialsException e) {
                    Toast.makeText(this, "No User Name Found ", Toast.LENGTH_SHORT).show();
                } catch(FirebaseAuthUserCollisionException e) {
                    Toast.makeText(this, "Clashing User Name ", Toast.LENGTH_SHORT).show();
                } catch(Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

    }

    private void logInStart(FirebaseUser user1) {
        Intent intent2 = new Intent(getApplicationContext(), loggedIn.class);
        CURRENT_USER_ID = user1.getEmail();
        intent2.putExtra(CURRENT_USER_ID, user1);
        startActivity(intent2);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void init(){
        signInButton = findViewById(R.id.signIn);
        signInButtonGoogle = findViewById(R.id.signInWithGoogle);

        password= findViewById(R.id.Password);
        email= findViewById(R.id.email);
        noAccount= findViewById(R.id.no);
        forgotPass= findViewById(R.id.forgotPass);
    }

}