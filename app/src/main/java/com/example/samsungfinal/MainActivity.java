package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MainActivity";
    static final String HOST = "https://kudago.com/public-api/v1.4";
    Button bSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bSearch = findViewById(R.id.btn_new_search);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
            }
        });

        HistoryAdapter adapter = new HistoryAdapter(this, getHistory());
        ListView lv = (ListView) findViewById(R.id.list_history);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Click to item ", Toast.LENGTH_SHORT).show();
                long id_history =  ((TempHistory)parent.getItemAtPosition(position)).id;
                Intent intentResult = new Intent(MainActivity.this, EventsListActivity.class);
                intentResult.putExtra("id_history", id_history);
                startActivity(intentResult);
            }
        });
    }

//    private TempHistory[] getHistory() {
//        TempHistory[] arr = new TempHistory[3];
//
//        String[] cities = {"Novosibirsk", "Omsk", "Tomsk"};
//        String[] date = {"20 june 2023", "21 june 2023", "22 june 2023"};
//        int[] idArr = {1, 2, 3};
//
//        for (int i = 0; i < arr.length; i++) {
//            TempHistory history = new TempHistory(idArr[i], cities[i], date[i], date[i]);
//            arr[i] = history;
//        }
//        return arr;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String search = getResources().getString(R.string.new_search);
        menu.add(0,0,0,search);
        String clear = getResources().getString(R.string.clear_history);
        menu.add(0,1,1,clear);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                break;
            case 1:
                clearHistory();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearHistory() {

    }

    void getEvents(String city, String date_from_ms, String date_to_ms) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi service = retrofit.create(RestApi.class);
        Call<EventShortList> call = service.events("ru", city, date_from_ms, date_to_ms);
        call.enqueue(new Callback<EventShortList>() {
            @Override
            public void onResponse(Call<EventShortList> call, Response<EventShortList> response) {
                try {
                    EventShortList events = response.body();

                    textView.setText("Предиктор : " + textWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<EventShortList> call, Throwable t) {
                call.cancel();
            }
        });
    }
}