package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventsListActivity extends AppCompatActivity {
    TempHistory md;
    EventsAdapter adapter;
    Context mContext;
    RestApi apiInterface;
    ListView lv;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        mContext=this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        md= (TempHistory) bundle.getSerializable("value");

        title = (TextView)findViewById(R.id.events_title);
        title.setText(String.format("Events - %s", md.city));

        lv = (ListView) findViewById(R.id.list_events);

        getEvents();

    }

    void getEvents () {
        apiInterface = APIClient.getClient().create(RestApi.class);

        String page = "";
        String page_size = "100";
        String fields = "id,title,slug,dates,place,age_restriction";

        Call<EventShortList> call = apiInterface.events("ru", md.city, md.date_from_ms,
                md.date_to_ms, page, page_size, fields);
        call.enqueue(new Callback<EventShortList>() {
            @Override
            public void onResponse(@NonNull Call<EventShortList> call, @NonNull Response<EventShortList> response) {
                if(!response.isSuccessful()) {
                    List<EventShort> results = new ArrayList<>();
                    results.add(new EventShort(0, response.toString(), "", "", "", ""));
                    adapter = new EventsAdapter(mContext, results);
                    lv.setAdapter(adapter);
                }
                try {
                    EventShortList resource = response.body();
                    Integer count = null;
                    List<EventShort> results = null;
                    if (resource != null) {
                        count = resource.count;
                        String next = resource.next;
                        String previous = resource.previous;
                        results = resource.results;
                        adapter = new EventsAdapter(mContext, results);
                        lv.setAdapter(adapter);
                    }
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

}