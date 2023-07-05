package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    String city;
    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    EditText date_start;
    EditText date_end;
    Button btn_find;
    DBHistory mDBConnector;
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mContext=this;
        mDBConnector=new DBHistory(this);

        Locale locale = new Locale("ru");
        Locale.setDefault(locale);

        // Выпадающий список городов
        setSpinnerView();

        // установка текущей даты
        date_start = (EditText)findViewById(R.id.date_start);
        date_end = (EditText)findViewById(R.id.date_end);
        setInitialDateTime();

        btn_find = findViewById(R.id.find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Date date_from = sdf.parse(date_start.getText().toString());
                    Date date_to = sdf.parse(date_end.getText().toString());
                    String date_from_ms = Long.toString(date_from.getTime());
                    String date_to_ms = Long.toString(date_to.getTime());
                    mDBConnector.insert(city, date_start.getText().toString(), date_from_ms,
                            date_end.getText().toString(), date_to_ms);
                    Intent intentMain = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(intentMain);
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(),"Date format error", Toast.LENGTH_SHORT).show();
                } catch (RuntimeException e) {
                    Toast.makeText(getApplicationContext(),"Add to base error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    // отображаем диалоговое окно для выбора даты
    public void setDateStart(View v) {
        new DatePickerDialog(SearchActivity.this, d_start,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальной даты
    private void setInitialDateTime() {

        date_start.setText(sdf.format(calendar.getTime()));
        date_end.setText(date_start.getText());
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d_start = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date_start.setText(sdf.format(calendar.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener d_end = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date_end.setText(sdf.format(calendar.getTime()));
        }
    };

    public void setDateEnd(View v) {
        new DatePickerDialog(SearchActivity.this, d_end,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setSpinnerView() {
        HashMap<String, String> locations = new HashMap<>();
        locations.put(getResources().getString(R.string.ekb), "ekb");
        locations.put(getResources().getString(R.string.kzn), "kzn");
        locations.put(getResources().getString(R.string.msk), "msk");
        locations.put(getResources().getString(R.string.nnv), "nnv");
        locations.put(getResources().getString(R.string.nsk), "nsk");
        locations.put(getResources().getString(R.string.spb), "spb");
        List<String> cities = new ArrayList<String>(locations.keySet());
        Spinner spinner = findViewById(R.id.spinner_city);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный город
                String item = (String) parent.getItemAtPosition(position);
                city = locations.get(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String main = getResources().getString(R.string.home);
        menu.add(0, 0, 0, main);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            Intent intentMain = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intentMain);
        }
        return super.onOptionsItemSelected(item);
    }


}