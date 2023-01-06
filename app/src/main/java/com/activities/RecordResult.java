package com.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.R;
import com.dbManager.DbManager;
import com.entities.Result;


import org.w3c.dom.Text;

import java.util.Calendar;

public class RecordResult extends AppCompatActivity {
    public static String KEY_COUNTRY_SELECTED = "country";
    public static String KEY_TEAM_SELECTED = "team";
    private static String VALUE_TEAMHM_SELECTED;
    private static String VALUE_TEAMAW_SELECTED;
    private static String[] phases;
    private Button btnHomeTm;
    private Button btnAwayTm;
    private Button btnCldr;
    private Button btnPhase;
    private Button btnReset;
    private Button btnSave;
    private TextView txtDate;
    private TextView txtPhase;
    private NumberPicker nmbPickerHomeTm;
    private NumberPicker nmbPickerAwayTm;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                int strId;
                Drawable image;
                if (result.getData()!= null) {
                    if (result.getData().getStringExtra(KEY_TEAM_SELECTED).equals(VALUE_TEAMHM_SELECTED)) {
                        strId = getStringIdentifier(this, result.getData().getStringExtra(KEY_COUNTRY_SELECTED));
                        image = getImageBtn(strId);
                        btnHomeTm.setCompoundDrawables(image, null, null, null);
                        btnHomeTm.setTextSize(9);
                        btnHomeTm.setText(result.getData().getStringExtra(KEY_COUNTRY_SELECTED));
                    } else if (result.getData().getStringExtra(KEY_TEAM_SELECTED).equals(VALUE_TEAMAW_SELECTED)) {
                        strId = getStringIdentifier(this, result.getData().getStringExtra(KEY_COUNTRY_SELECTED));
                        image = getImageBtn(strId);
                        btnAwayTm.setCompoundDrawables(image, null, null, null);
                        btnAwayTm.setTextSize(9);
                        btnAwayTm.setText(result.getData().getStringExtra(KEY_COUNTRY_SELECTED));
                    }
                }
            });

    private Drawable getImageBtn(int strId) {
        Drawable image = this.getResources().getDrawable((strId), null);
        int h = image.getIntrinsicHeight();
        int w = image.getIntrinsicWidth();
        image.setBounds( 0, 0, w - 20, h - 20);
        return image;
    }

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_result);
        initWidgets();
        initGlobals();
    }

    private void initWidgets() {
        initTextFields();
        initButtons();
        initNmbPickers();
    }

    private void initTextFields() {
        txtDate = findViewById(R.id.txtDate);
        txtPhase = findViewById(R.id.txtPhase);
    }

    private void initNmbPickers() {
        nmbPickerHomeTm = findViewById(R.id.nmbPickerHomeTm);
        nmbPickerAwayTm = findViewById(R.id.nmbPickerAwayTm);
        nmbPickerHomeTm.setMinValue(0);
        nmbPickerHomeTm.setMaxValue(100);
        nmbPickerAwayTm.setMinValue(0);
        nmbPickerAwayTm.setMaxValue(100);
    }

    private void initButtons() {
        btnHomeTm = findViewById(R.id.btnHomeTm);
        btnAwayTm = findViewById(R.id.btnAwayTm);
        btnCldr = findViewById(R.id.btnCldr);
        btnPhase = findViewById(R.id.btnPhase);
        btnReset = findViewById(R.id.btnReset);
        btnSave = findViewById(R.id.btnSave);
        setListenerBtns();
    }

    private void setListenerBtns() {
        btnHomeTm.setOnClickListener(v -> {
            Intent intent = new Intent(RecordResult.this, SelectTeam.class);
            intent.putExtra(KEY_TEAM_SELECTED, VALUE_TEAMHM_SELECTED);
            intent.putExtra(KEY_COUNTRY_SELECTED, btnAwayTm.getText());
            resultLauncher.launch(intent);
        });
        btnAwayTm.setOnClickListener(v -> {
            Intent intent = new Intent(RecordResult.this, SelectTeam.class);
            intent.putExtra(KEY_TEAM_SELECTED, VALUE_TEAMAW_SELECTED);
            intent.putExtra(KEY_COUNTRY_SELECTED, btnHomeTm.getText());
            resultLauncher.launch(intent);
        });
        btnCldr.setOnClickListener(v -> DateTimePickerGenerator());
        btnPhase.setOnClickListener(v -> alertDialogGenerator(phases));
        btnReset.setOnClickListener(v -> emptyFields());
        btnSave.setOnClickListener(v -> {
            if (txtDate.getText().toString().isEmpty() || btnHomeTm.getText().toString().contains(VALUE_TEAMHM_SELECTED)
                    || btnAwayTm.getText().toString().contains(VALUE_TEAMAW_SELECTED)) {
                Toast.makeText(RecordResult.this, R.string.recResInvData, Toast.LENGTH_SHORT).show();
            }
            else {
                storeData();
                emptyFields();
            }
        });
    }

    private void initGlobals() {
        Resources res = getResources();
        VALUE_TEAMHM_SELECTED = res.getString(R.string.btnHomeTm);
        VALUE_TEAMAW_SELECTED = res.getString(R.string.btnAwayTm);
        phases = res.getStringArray(R.array.phases);
    }

    private void storeData() {
        Result result = new Result(String.valueOf(txtPhase.getText()), String.valueOf(txtDate.getText()),
                String.valueOf(btnHomeTm.getText()), nmbPickerHomeTm.getValue(), String.valueOf(btnAwayTm.getText()),
                nmbPickerAwayTm.getValue());
        DbManager db = new DbManager(this);
        try {
            db.insertResult(result);
            Toast.makeText(RecordResult.this, R.string.recResValData, Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(RecordResult.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void emptyFields() {
        txtDate.setText("");
        txtPhase.setText("");
        nmbPickerHomeTm.setValue(0);
        nmbPickerAwayTm.setValue(0);
        btnHomeTm.setText(R.string.btnHomeTm);
        btnAwayTm.setText(R.string.btnAwayTm);
        btnHomeTm.setTextSize((float) 14.2);
        btnAwayTm.setTextSize((float) 14.2);
        btnHomeTm.setCompoundDrawables(null, null, null, null);
        btnAwayTm.setCompoundDrawables(null, null, null, null);
    }

    private void alertDialogGenerator(String[] options) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordResult.this);
        builder.setTitle(R.string.selectPhase);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setTxtPhase(which);
            }
        });
        builder.show();
    }

    private void setTxtPhase(int which) {
        txtPhase.setText(phases[which]);
    }

    private void DateTimePickerGenerator() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(RecordResult.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(RecordResult.this,
                            (view1, hourOfDay, minute1) -> setTxtDate(hourOfDay, minute1, dayOfMonth, monthOfYear, year1),
                            hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void setTxtDate(int hourOfDay, int minute, int dayOfMonth, int monthOfYear, int year) {
        txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year + " " + String.format("%02d:%02d", hourOfDay, minute));
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name + "flag", "drawable", context.getPackageName());
    }
}