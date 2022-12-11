package com.example.evaluaciont1jose;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class CheckResult extends AppCompatActivity {
    Resources res;
    String BTN_SELECT;
    Button btnSelect;
    FragmentResults fr;
    FrameLayout frameLayout1, frameLayout2, frameLayout3, frameLayout4, frameLayout5, frameLayout6, frameLayout7;
    Drawable image;
    String ctry;
    int strId;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData()!= null) {
                    emptyFields();
                    strId = getStringIdentifier(this, result.getData().getStringExtra(RecordResult.KEY_COUNTRY_SELECTED));
                    setImageBtn(strId);
                    btnSelect.setCompoundDrawables(image, null, null, null);
                    btnSelect.setTextSize(9);
                    btnSelect.setText(result.getData().getStringExtra(RecordResult.KEY_COUNTRY_SELECTED));
                    ctry = String.valueOf(btnSelect.getText());
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    fillFrameLayouts(ft);


                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

    private void fillFrameLayouts(FragmentTransaction ft) {
        for (int i = 0; i < ResultList.getResult(ctry).size(); i++) {
            fr = new FragmentResults().newInstance(ctry, i);
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
                    ft.add(R.id.frameLayout3, fr);
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


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_result);

        initializeWidgets();

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckResult.this, SelectTeam.class);
                intent.putExtra(RecordResult.KEY_COUNTRY_SELECTED, "TEAM");
                resultLauncher.launch(intent);
            }
        });
        if (savedInstanceState != null) {
            btnSelect.setText(savedInstanceState.getString("btnSelect"));
            if (!btnSelect.getText().equals(BTN_SELECT)) {
                strId = getStringIdentifier(this, String.valueOf(btnSelect.getText()));
                setImageBtn(strId);
                btnSelect.setCompoundDrawables(image, null, null, null);
                btnSelect.setTextSize(9);
            }
            /*
            ctry = String.valueOf(btnSelect.getText());
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            fillFrameLayouts(ft);
             */
        }

    }

    private void initializeWidgets() {
        res = getResources();
        BTN_SELECT = res.getString(R.string.btnSel);
        btnSelect = findViewById(R.id.btnSelect);
        frameLayout1 = findViewById(R.id.frameLayout1);
        frameLayout2 = findViewById(R.id.frameLayout2);
        frameLayout3 = findViewById(R.id.frameLayout3);
        frameLayout4 = findViewById(R.id.frameLayout3);
        frameLayout5 = findViewById(R.id.frameLayout5);
        frameLayout6 = findViewById(R.id.frameLayout6);
        frameLayout7 = findViewById(R.id.frameLayout7);
    }

    private void setImageBtn(int strId) {
        image = this.getResources().getDrawable((strId),null);
        int h = image.getIntrinsicHeight();
        int w = image.getIntrinsicWidth();
        image.setBounds( 0, 0, w - 20, h - 20);
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

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("btnSelect", String.valueOf(btnSelect.getText()));

    }
}