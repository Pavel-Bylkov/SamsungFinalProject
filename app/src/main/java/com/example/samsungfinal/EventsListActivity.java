package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsungfinal.eventslist.EventShort;
import com.example.samsungfinal.eventslist.EventShortList;
import com.example.samsungfinal.eventslist.EventsAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        HashMap<String, String> locations = new HashMap<>();
        locations.put("ekb", getResources().getString(R.string.ekb));
        locations.put("kzn", getResources().getString(R.string.kzn));
        locations.put("msk", getResources().getString(R.string.msk));
        locations.put("nnv", getResources().getString(R.string.nnv));
        locations.put("nsk", getResources().getString(R.string.nsk));
        locations.put("spb", getResources().getString(R.string.spb));
        title.setText(String.format("Events - %s", locations.get(md.city)));

        lv = (ListView) findViewById(R.id.list_events);
//      Добавил загрузку в отдельном потоке через AsyncTask
        new JsonDataTask().execute();
//      Второй вариант через Retrofit оставил для себя
//        getEvents();

    }

    private class JsonDataTask extends AsyncTask<Void, Void, JSONObject>
    {
        @Override
        protected JSONObject doInBackground(Void... params)
        {
            String BASE_URL = "https://kudago.com/public-api/v1.4/events/?";
            String page = "1";
            String page_size = "80";
            String fields = "id,title,slug,age_restriction";
            String str = String.format("%slang=%slocation=%sactual_since=%sactual_until=%spage=%spage_size=%sfields=%s",
                    BASE_URL, "ru", md.city, md.date_from_ms, md.date_to_ms, page, page_size, fields);
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "JsonDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response)
        {
            if(response != null)
            {
                EventShortList resource = new Gson().fromJson(String.valueOf(response), EventShortList.class);
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
            }
            else {
                List<EventShort> results = new ArrayList<>();
                results.add(new EventShort(0, "Error in connection", "", ""));
                adapter = new EventsAdapter(mContext, results);
                lv.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Get list events error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void getEvents () {
        apiInterface = APIClient.getClient().create(RestApi.class);

        String page = "1";
        String page_size = "80";
        String fields = "id,title,slug,age_restriction";

        Call<EventShortList> call = apiInterface.events("ru", md.city, md.date_from_ms,
                md.date_to_ms, page, page_size, fields);
        call.enqueue(new Callback<EventShortList>() {
            @Override
            public void onResponse(@NonNull Call<EventShortList> call, @NonNull Response<EventShortList> response) {
                if(!response.isSuccessful()) {
                    List<EventShort> results = new ArrayList<>();
                    results.add(new EventShort(0, response.toString(), "", ""));
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
//                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Get list events error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventShortList> call, @NonNull Throwable t) {
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