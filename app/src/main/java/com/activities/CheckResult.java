package com.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.R;
import com.dbManager.DbManager;
import com.entities.Result;

import java.util.ArrayList;

public class CheckResult extends AppCompatActivity {
    private static String txtBtnSelect;
    private Button btnSelect;
    private FrameLayout frameLayout1, frameLayout2, frameLayout3, frameLayout4, frameLayout5, frameLayout6, frameLayout7;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData()!= null) {
                    emptyFields();
                    int strId = getStringIdentifier(this, result.getData().getStringExtra(RecordResult.KEY_COUNTRY_SELECTED));
                    Drawable image = getImageBtn(strId);
                    btnSelect.setCompoundDrawables(image, null, null, null);
                    btnSelect.setTextSize(9);
                    btnSelect.setText(result.getData().getStringExtra(RecordResult.KEY_COUNTRY_SELECTED));
                    String country = String.valueOf(btnSelect.getText()).trim().toLowerCase();
                    DbManager db = new DbManager(this);
                    int nbMatchesFd = db.countResultsByCountry(country);
                    if (nbMatchesFd < 0) {
                        Toast.makeText(CheckResult.this, R.string.error + getString(R.string.searchError), Toast.LENGTH_LONG);
                    } else {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        fillFrameLayouts(ft, country, nbMatchesFd);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }
            });

    private void fillFrameLayouts(FragmentTransaction ft, String country, int nbMatchesFd) {
        for (int i = 0; i < nbMatchesFd; i++) {
            FragmentResults fr = new FragmentResults().newInstance(country, i);
            switch (i) {
                case 0:
                    ft.add(R.id.frameLayout1, fr);
                    break;
                case 1:
                    ft.add(R.id.frameLayout2, fr);
                    break;
                case 2:
                    ft.add(R.id.frameLayout3, fr);
                    break;
                case 3:
                    ft.add(R.id.frameLayout4, fr);
                    break;
                case 4:
                    ft.add(R.id.frameLayout5, fr);
                    break;
                case 5:
                    ft.add(R.id.frameLayout6, fr);
                    break;
                case 6:
                    ft.add(R.id.frameLayout7, fr);
                    break;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_result);
        initGlobal();
        initButtons();
        initFrLayouts();
    }

    private void initGlobal() {
        Resources res = getResources();
        txtBtnSelect = res.getString(R.string.btnSel);
    }

    private void initButtons() {
        btnSelect = findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener((v) -> {
            Intent intent = new Intent(CheckResult.this, SelectTeam.class);
            intent.putExtra(RecordResult.KEY_COUNTRY_SELECTED, "TEAM");
            resultLauncher.launch(intent);
        });
    }

    private void initFrLayouts() {
        frameLayout1 = findViewById(R.id.frameLayout1);
        frameLayout2 = findViewById(R.id.frameLayout2);
        frameLayout3 = findViewById(R.id.frameLayout3);
        frameLayout4 = findViewById(R.id.frameLayout3);
        frameLayout5 = findViewById(R.id.frameLayout5);
        frameLayout6 = findViewById(R.id.frameLayout6);
        frameLayout7 = findViewById(R.id.frameLayout7);
    }

    private Drawable getImageBtn(int strId) {
        Drawable image = this.getResources().getDrawable((strId),null);
        int h = image.getIntrinsicHeight();
        int w = image.getIntrinsicWidth();
        image.setBounds( 0, 0, w - 20, h - 20);
        return image;
    }

    private void emptyFields(){
        frameLayout1.removeAllViews();
        frameLayout2.removeAllViews();
        frameLayout3.removeAllViews();
        frameLayout4.removeAllViews();
        frameLayout5.removeAllViews();
        frameLayout6.removeAllViews();
        frameLayout7.removeAllViews();
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name + "flag", "drawable", context.getPackageName());
    }
}