package com.forhan.debitcredit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Button history, users, logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        history = findViewById(R.id.btn_history_id);
        users = findViewById(R.id.btn_users_id);
        logOut = findViewById(R.id.btn_log_out_admin_id);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), LaunchingActivity.class);
                startActivity(home);
                editor = sp.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });
    }
}