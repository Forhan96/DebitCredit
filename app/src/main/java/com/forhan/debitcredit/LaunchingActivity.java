package com.forhan.debitcredit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LaunchingActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Button login;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        String role = sp.getString("role", null);

        if (sp.getBoolean("logged", false)) {
            if (role.equals("1")){
                Intent admin = new Intent(LaunchingActivity.this, AdminActivity.class);
                startActivity(admin);
            }
            else if (role.equals("2")){
                Intent user = new Intent(LaunchingActivity.this, UserActivity.class);
                startActivity(user);
            }

        }

        login = findViewById(R.id.btn_sign_in);
        signUp = findViewById(R.id.sign_up_tv);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpActivity = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpActivity);
            }
        });
    }
}