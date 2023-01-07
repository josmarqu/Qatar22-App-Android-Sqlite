package com.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.R;
import com.dbManager.DbManager;


public class HomeScreen extends AppCompatActivity {
    Button btnRecRs;
    Button btnChkRs;
    ImageButton btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initButtons();
    }

    private void initButtons() {
        btnRecRs = findViewById(R.id.btnRecRes);
        btnChkRs = findViewById(R.id.btnChkRes);
        btnDelete = findViewById(R.id.btnDelete);

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
        btnDelete.setOnClickListener((v) -> {
            new AlertDialog.Builder(HomeScreen.this)
                    .setTitle(R.string.confirm)
                    .setMessage(R.string.restore)
                    .setPositiveButton(R.string.affirmative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restoreData();
                        }
                    })
                    .setNegativeButton(R.string.negative, null)
                    .show();
        });
    }

    private void restoreData() {
        DbManager dbManager = new DbManager(this);
        dbManager.restoreData();
        Toast.makeText(this, getString(R.string.restorescfy),Toast.LENGTH_LONG).show();
    }
}