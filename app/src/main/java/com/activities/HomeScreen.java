package com.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.R;


public class HomeScreen extends AppCompatActivity {
    Button btnRecRs;
    Button btnChkRs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initButtons();
    }

    private void initButtons() {
        btnRecRs = findViewById(R.id.btnRecRes);
        btnChkRs = findViewById(R.id.btnChkRes);

        setListenerBtns();
    }

    private void setListenerBtns() {
        btnRecRs.setOnClickListener((v) -> {
            Intent intent = new Intent(HomeScreen.this, RecordResult.class);
            startActivity(intent);
        });
        btnChkRs.setOnClickListener((v) -> {
            Intent intent = new Intent(HomeScreen.this, CheckResult.class);
            startActivity(intent);
        });
    }
}