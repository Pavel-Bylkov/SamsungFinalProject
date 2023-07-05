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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        mContext=this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        md= (TempHistory) bundle.getSerializable("value");

        lv = (ListView) findViewById(R.id.list_events);

        apiInterface = APIClient.getClient().create(RestApi.class);

        Call<EventShortList> call = apiInterface.events("ru", md.city, md.date_from_ms, md.date_to_ms);;
        call.enqueue(new Callback<EventShortList>() {
            @Override
            public void onResponse(@NonNull Call<EventShortList> call, @NonNull Response<EventShortList> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Get " + response.toString(), Toast.LENGTH_SHORT).show();
                }
                try {
                    EventShortList resource = response.body();
                    Integer count = null;
                    List<EventShort> results = null;
                    if (resource != null) {
                        Toast.makeText(getApplicationContext(),"Get " + resource.toString(), Toast.LENGTH_SHORT).show();
                        count = resource.count;
                        String next = resource.next;
                        String previous = resource.previous;
                        results = resource.results;
                        Toast.makeText(getApplicationContext(), "Success" + count.toString(),
                                Toast.LENGTH_SHORT).show();
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
    protected void onStart(){
        super.onStart();
//        getEvents(md.city, md.date_from_ms, md.date_to_ms, "");
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
                .baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi service = retrofit.create(RestApi.class);
        Call<EventShortList> call = service.events("ru", city, date_from_ms, date_to_ms);
        call.enqueue(new Callback<EventShortList>() {
            @Override
            public void onResponse(Call<EventShortList> call, Response<EventShortList> response) {
                try {
                    Toast.makeText(getApplicationContext(), "Success" + response.body().count,
                            Toast.LENGTH_SHORT).show();
//                    EventShortList events = response.body();
//                    adapter = new EventsAdapter(mContext, events.results);
//                    lv.setAdapter(adapter);
//                    Toast.makeText(getApplicationContext(),"Get " + events.count, Toast.LENGTH_SHORT).show();
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