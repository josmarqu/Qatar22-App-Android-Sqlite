package com.example.evaluaciont1jose.activities;


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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.evaluaciont1jose.R;
import com.example.evaluaciont1jose.dbManager.DbManager;

import java.util.Calendar;

public class RecordResult extends AppCompatActivity {
    Resources res;
    public static String KEY_COUNTRY_SELECTED = "country";
    public static String KEY_TEAM_SELECTED = "team";
    static String VALUE_TEAMHM_SELECTED;
    static String VALUE_TEAMAW_SELECTED;
    static String[] phases;
    Button btnHomeTm;
    Button btnAwayTm;
    Button btnCldr;
    Button btnPhase;
    Button btnReset;
    Button btnSave;
    EditText txtDate;
    TextView txtPhase;
    NumberPicker nmbPickerHomeTm;
    NumberPicker nmbPickerAwayTm;
    Drawable image;
    int strId;
    int day;
    int month;
    int  year;
    int hour;
    int minute;
    Calendar c;

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == SelectTeam.RESULT_ACCEPTED){
                   if (result.getData()!= null) {
                       if (result.getData().getStringExtra(KEY_TEAM_SELECTED).equals(VALUE_TEAMHM_SELECTED)) {
                           strId = getStringIdentifier(this, result.getData().getStringExtra(KEY_COUNTRY_SELECTED));
                           setImageBtn(strId);
                           btnHomeTm.setCompoundDrawables(image, null, null, null);
                           btnHomeTm.setTextSize(9);
                           btnHomeTm.setText(result.getData().getStringExtra(KEY_COUNTRY_SELECTED));

                       } else if (result.getData().getStringExtra(KEY_TEAM_SELECTED).equals(VALUE_TEAMAW_SELECTED)) {
                           strId = getStringIdentifier(this, result.getData().getStringExtra(KEY_COUNTRY_SELECTED));
                           setImageBtn(strId);
                           btnAwayTm.setCompoundDrawables(image, null, null, null);
                           btnAwayTm.setTextSize(9);
                           btnAwayTm.setText(result.getData().getStringExtra(KEY_COUNTRY_SELECTED));
                       }
                   }
                }
            });

    private void setImageBtn(int strId) {
        image = this.getResources().getDrawable((strId), null);
        int h = image.getIntrinsicHeight();
        int w = image.getIntrinsicWidth();
        image.setBounds( 0, 0, w - 20, h - 20);
    }

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_result);
        initializeWidgets();

        btnHomeTm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordResult.this, SelectTeam.class);
                intent.putExtra(KEY_TEAM_SELECTED, VALUE_TEAMHM_SELECTED);
                intent.putExtra(KEY_COUNTRY_SELECTED, btnAwayTm.getText());
                resultLauncher.launch(intent);
            }
        });

        btnAwayTm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordResult.this, SelectTeam.class);
                intent.putExtra(KEY_TEAM_SELECTED, VALUE_TEAMAW_SELECTED);
                intent.putExtra(KEY_COUNTRY_SELECTED, btnHomeTm.getText());
                resultLauncher.launch(intent);
            }
        });

        btnCldr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePickerGenerator();
            }
        });

        btnPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogGenerator(phases);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyFields();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDate.getText().toString().isEmpty() || btnHomeTm.getText().toString().contains(VALUE_TEAMHM_SELECTED)
                        || btnAwayTm.getText().toString().contains(VALUE_TEAMAW_SELECTED)) {
                    Toast.makeText(RecordResult.this, R.string.recResInvData, Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO : implement data store
                    storeData();
                    Toast.makeText(RecordResult.this, R.string.recResValData, Toast.LENGTH_SHORT).show();
                    emptyFields();
                }
            }
        });

        if (savedInstanceState != null) {
            txtDate.setText(savedInstanceState.getString("txtDate"));
            txtPhase.setText(savedInstanceState.getString("txtPhase"));
            nmbPickerHomeTm.setValue(savedInstanceState.getInt("nmbPickerHomeTm"));
            nmbPickerAwayTm.setValue(savedInstanceState.getInt("nmbPickerAwayTm"));
            btnHomeTm.setText(savedInstanceState.getString("btnHomeTm"));
            btnAwayTm.setText(savedInstanceState.getString("btnAwayTm"));
            if (!btnHomeTm.getText().equals(VALUE_TEAMHM_SELECTED)) {
                strId = getStringIdentifier(this, String.valueOf(btnHomeTm.getText()));
                setImageBtn(strId);
                btnHomeTm.setCompoundDrawables(image, null, null, null);
                btnHomeTm.setTextSize(9);
            }
            if (!btnAwayTm.getText().equals(VALUE_TEAMAW_SELECTED)) {
                strId = getStringIdentifier(this, String.valueOf(btnAwayTm.getText()));
                setImageBtn(strId);
                btnAwayTm.setCompoundDrawables(image, null, null, null);
                btnAwayTm.setTextSize(9);
            }
        }
    }

    private void storeData() {
        DbManager dbManager = new DbManager(this);
        dbManager.connection();
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
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RecordResult.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        hour = c.get(Calendar.HOUR_OF_DAY);
                        minute = c.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(RecordResult.this,
                                new TimePickerDialog.OnTimeSetListener()  {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        setTxtDate(hourOfDay, minute, dayOfMonth, monthOfYear, year);
                                    }
                                }, hour, minute, true);
                        timePickerDialog.show();
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void setTxtDate(int hourOfDay, int minute, int dayOfMonth, int monthOfYear, int year) {
        txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year + " " + String.format("%02d:%02d", hourOfDay, minute));
    }

    private void initializeWidgets() {
        res = getResources();
        VALUE_TEAMHM_SELECTED = res.getString(R.string.btnHomeTm);
        VALUE_TEAMAW_SELECTED = res.getString(R.string.btnAwayTm);
        phases = res.getStringArray(R.array.phases);
        btnHomeTm = findViewById(R.id.btnHomeTm);
        btnAwayTm = findViewById(R.id.btnAwayTm);
        txtDate = findViewById(R.id.txtDate);
        txtPhase = findViewById(R.id.txtPhase);
        btnCldr = findViewById(R.id.btnCldr);
        btnPhase = findViewById(R.id.btnPhase);
        btnReset = findViewById(R.id.btnReset);
        btnSave = findViewById(R.id.btnSave);
        nmbPickerHomeTm = findViewById(R.id.nmbPickerHomeTm);
        nmbPickerAwayTm = findViewById(R.id.nmbPickerAwayTm);
        nmbPickerHomeTm.setMinValue(0);
        nmbPickerHomeTm.setMaxValue(100);
        nmbPickerAwayTm.setMinValue(0);
        nmbPickerAwayTm.setMaxValue(100);
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name + "flag", "drawable", context.getPackageName());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("txtDate", String.valueOf(txtDate.getText()));
        outState.putString("txtPhase", String.valueOf(txtPhase.getText()));
        outState.putInt("nmbPickerHomeTm", nmbPickerHomeTm.getValue());
        outState.putInt("nmbPickerAwayTm", nmbPickerAwayTm.getValue());
        outState.putString("btnHomeTm", String.valueOf(btnHomeTm.getText()));
        outState.putString("btnAwayTm", String.valueOf(btnAwayTm.getText()));
    }
}