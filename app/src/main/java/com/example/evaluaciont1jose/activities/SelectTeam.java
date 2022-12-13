package com.example.evaluaciont1jose.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.evaluaciont1jose.R;

public class SelectTeam extends AppCompatActivity implements View.OnClickListener {
    Resources res;
    public static int RESULT_ACCEPTED = 0;
    boolean firstTime = true;
    Button btn;
    Button btnAccept;
    static int[] buttonIds = {R.id.btnArg, R.id.btnAus, R.id.btnBlg, R.id.btnBrz, R.id.btnCmr, R.id.btnCnd, R.id.btnCstRc, R.id.btnCrc,
    R.id.btnDnmk, R.id.btnEcd, R.id.btnEng, R.id.btnFr, R.id.btnGrm, R.id.btnGh, R.id.btnIr, R.id.btnJp,
    R.id.btnMx, R.id.btnMor, R.id.btnNth, R.id.btnPl, R.id.btnPrt, R.id.btnQt, R.id.btnSdar, R.id.btnSn,
    R.id.btnTn, R.id.btnEeuu, R.id.btnUrg, R.id.btnWal, R.id.btnSrb, R.id.btnStk, R.id.btnSp, R.id.btnSwz};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);
        initializeWidgets();
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn != null) {
                    if (getIntent().getStringExtra(RecordResult.KEY_COUNTRY_SELECTED).contains(btn.getText())){
                        Toast.makeText(SelectTeam.this, (btn.getText() + res.getString(R.string.selTmDupData)), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getIntent().putExtra(RecordResult.KEY_TEAM_SELECTED, getIntent().getStringExtra(RecordResult.KEY_TEAM_SELECTED));
                        getIntent().putExtra(RecordResult.KEY_COUNTRY_SELECTED, btn.getText());
                        setResult(RESULT_ACCEPTED, SelectTeam.this.getIntent());
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(SelectTeam.this, res.getString(R.string.selTmInvData), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (savedInstanceState != null) {
            btn = findViewById(savedInstanceState.getInt("btnId"));
            btn.setBackgroundColor(getResources().getColor(R.color.green));
            firstTime = false;
        }
    }

    private void initializeWidgets() {
        res = getResources();
        btnAccept = findViewById(R.id.btnAccept);
        for(int i=0;i< buttonIds.length;i++)
        {
            Button bt;
            bt = (Button) findViewById(buttonIds[i]);
            bt.setOnClickListener(this);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if (!firstTime) {
            btn.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else
        {
            firstTime = false;
        }
        btn = (Button) findViewById(v.getId());
        btn.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (btn != null)
        {
            outState.putInt("btnId", btn.getId());
        }
    }

}