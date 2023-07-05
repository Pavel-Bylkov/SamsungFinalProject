package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsListActivity extends AppCompatActivity {
    static final String HOST = "https://kudago.com/public-api/v1.4";
    TempHistory md;
    EventsAdapter adapter;
    Context mContext;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        mContext=this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        md= (TempHistory) bundle.getSerializable("value");
        Toast.makeText(getApplicationContext(),"Get " + md.city, Toast.LENGTH_SHORT).show();

        lv = (ListView) findViewById(R.id.list_events);

    }

    @Override
    protected void onStart(){
        super.onStart();
        getEvents(md.city, md.date_from_ms, md.date_to_ms, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String main = getResources().getString(R.string.home);
        menu.add(0,0,0,main);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            Intent intentMain = new Intent(EventsListActivity.this, MainActivity.class);
            startActivity(intentMain);
        }
        return super.onOptionsItemSelected(item);
    }

    void getEvents(String city, String date_from_ms, String date_to_ms, String page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi service = retrofit.create(RestApi.class);
        Call<EventShortList> call = service.events("ru", city, date_from_ms, date_to_ms, page);
        call.enqueue(new Callback<EventShortList>() {
            @Override
            public void onResponse(Call<EventShortList> call, Response<EventShortList> response) {
                try {
                    EventShortList events = response.body();
                    adapter = new EventsAdapter(mContext, events.results);
                    lv.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(),"Get " + events.count, Toast.LENGTH_SHORT).show();
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