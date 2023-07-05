package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.format.DateUtils;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    String city;
    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    EditText date_start;
    EditText date_end;
    Button btn_find;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
                    Toast.makeText(getApplicationContext(),date_from_ms, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    Toast.makeText(getApplicationContext(),"Date format error", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        https://kudago.com/public-api/v1.2/events/?location=spb&actual_since=1384252440&actual_until=1384292440&is_free=1&categories=exhibition,concert
        // https://kudago.com/public-api/v1.4/events/?lang=ru&fields=&expand=&order_by=&text_format=&ids=&location=nsk&actual_since=1444385206&actual_until=1444385405&is_free=&categories=&lon=&lat=&radius=
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
        locations.put("Екатеринбург", "ekb");
        locations.put("Казань", "kzn");
        locations.put("Москва", "msk");
        locations.put("Нижний Новгород", "nnv");
        locations.put("Новосибирск", "nsk");
        locations.put("Санкт-Петербург", "spb");
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