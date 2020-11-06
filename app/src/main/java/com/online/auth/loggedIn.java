package com.online.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

public class loggedIn extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in);
        TextView textView = findViewById(R.id.textView3);
        Intent intent = getIntent();
        FirebaseUser currentUser = intent.getParcelableExtra(MainActivity.CURRENT_USER_ID);
        textView.setText(currentUser.getEmail());
    }
}
