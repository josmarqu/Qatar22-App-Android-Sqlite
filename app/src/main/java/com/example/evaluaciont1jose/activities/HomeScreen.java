package com.example.evaluaciont1jose.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.evaluaciont1jose.R;

public class HomeScreen extends AppCompatActivity {
    Button btnRecRs;
    Button btnChkRs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        btnRecRs = findViewById(R.id.btnRecRes);
        btnChkRs = findViewById(R.id.btnChkRes);

        btnRecRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, RecordResult.class);
                startActivity(intent);
            }
        });

        btnChkRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, CheckResult.class);
                startActivity(intent);
            }
        });
    }
}