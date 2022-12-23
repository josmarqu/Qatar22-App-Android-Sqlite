package com.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.R;

import java.util.Arrays;

public class SelectTeam extends AppCompatActivity implements View.OnClickListener {
    private boolean firstButtonClick = true;
    private Button btn;
    private Button btnAccept;
    private static int[] buttonIds = {R.id.btnArg, R.id.btnAus, R.id.btnBlg, R.id.btnBrz, R.id.btnCmr, R.id.btnCnd, R.id.btnCstRc, R.id.btnCrc,
            R.id.btnDnmk, R.id.btnEcd, R.id.btnEng, R.id.btnFr, R.id.btnGrm, R.id.btnGh, R.id.btnIr, R.id.btnJp,
            R.id.btnMx, R.id.btnMor, R.id.btnNth, R.id.btnPl, R.id.btnPrt, R.id.btnQt, R.id.btnSdar, R.id.btnSn,
            R.id.btnTn, R.id.btnEeuu, R.id.btnUrg, R.id.btnWal, R.id.btnSrb, R.id.btnStk, R.id.btnSp, R.id.btnSwz};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);
        initButtons();
        savedInstate(savedInstanceState);
    }

    private void savedInstate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            btn = findViewById(savedInstanceState.getInt("btnId"));
            btn.setBackgroundColor(getResources().getColor(R.color.green));
            firstButtonClick = false;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (btn != null)
        {
            outState.putInt("btnId", btn.getId());
        }
    }

    private void initButtons() {
        Resources res = getResources();
        btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(v-> {
            if (btn != null) {
                if (getIntent().getStringExtra(RecordResult.KEY_COUNTRY_SELECTED).contains(btn.getText())){
                    Toast.makeText(SelectTeam.this, (btn.getText() + res.getString(R.string.selTmDupData)), Toast.LENGTH_SHORT).show();
                }
                else {
                    getIntent().putExtra(RecordResult.KEY_TEAM_SELECTED, getIntent().getStringExtra(RecordResult.KEY_TEAM_SELECTED));
                    getIntent().putExtra(RecordResult.KEY_COUNTRY_SELECTED, btn.getText());
                    setResult(RESULT_OK, SelectTeam.this.getIntent());
                    finish();
                }
            }
            else
            {
                Toast.makeText(SelectTeam.this, res.getString(R.string.selTmInvData), Toast.LENGTH_SHORT).show();
            }
        });
        Arrays.stream(buttonIds).mapToObj(id -> (Button) findViewById(id)).forEach(button -> button.setOnClickListener(this));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        Button btn = (Button) findViewById(v.getId());
        if (firstButtonClick) {
            firstButtonClick = false;
        } else {
            this.btn.setBackgroundColor(getResources().getColor(R.color.red));
        }
        btn.setBackgroundColor(getResources().getColor(R.color.green));
        this.btn = btn;
    }
}