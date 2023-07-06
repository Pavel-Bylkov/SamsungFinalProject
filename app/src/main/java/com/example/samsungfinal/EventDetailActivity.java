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
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    EventShort md;
    EventDatesAdapter date_adapter;
    EventImagesAdapter image_adapter;

    EventCategoriesAdapter category_adapter;
    Context mContext;
    RestApi apiInterface;
    ListView lv_dates, lv_images, lv_categories, lv_tags;
    TextView title, age, place_title, place_address, place_phone, place_site_url;
    WebView webView;
    TextView city, site_url, price, publication_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        mContext=this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        md= (EventShort) bundle.getSerializable("value");
        Toast.makeText(getApplicationContext(),"Get " + md.title, Toast.LENGTH_SHORT).show();

        // title; //- название
        title = (TextView) findViewById(R.id.event_title);
            // List<Date> dates; //- даты проведения
        // {start_date;start_time;start;end_date;end_time;end; }
        lv_dates = (ListView)findViewById(R.id.event_list_dates);
            // age_restriction; //- возрастное ограничение
        age = (TextView) findViewById(R.id.event_age_restriction);
            // List<Image> images; //- картинки {  image; }
        lv_images = (ListView)findViewById(R.id.event_list_images);
            //TextArea description; //- описание
        webView = (WebView) findViewById(R.id.event_description);
            // Location location; //- город проведения{ name;}
        city = (TextView) findViewById(R.id.event_city_name);
            // Place place; //- место проведения {title;address;phone;site_url;}
        place_title = (TextView) findViewById(R.id.event_place_title);
        place_address = (TextView) findViewById(R.id.event_place_address);
        place_phone = (TextView) findViewById(R.id.event_place_phone);
        place_site_url = (TextView) findViewById(R.id.event_place_site_url);
            // site_url; //- адрес события на сайте kudago.com
        site_url = (TextView) findViewById(R.id.event_site_url);
            // List<String> categories; //- список категорий
        lv_categories = (ListView)findViewById(R.id.event_list_categories);
            // List<String> tags; //- тэги события
        lv_tags = (ListView)findViewById(R.id.event_list_tags);
            // price; //- стоимость
        price = (TextView) findViewById(R.id.event_price);
            // publication_date; //- дата публикации
        publication_date = (TextView) findViewById(R.id.event_publication_date);

        getEvent();
    }

    void getEvent () {
        apiInterface = APIClient.getClient().create(RestApi.class);

        String fields = "id,publication_date,dates,title,slug,place,description,location,categories,age_restriction,price,images,site_url,tags";
        String expand = "dates,place,location,images";

        Call<EventDetail> call = apiInterface.event(md.id, "ru", fields, expand);
        call.enqueue(new Callback<EventDetail>() {
            @Override
            public void onResponse(@NonNull Call<EventDetail> call, @NonNull Response<EventDetail> response) {
                if(!response.isSuccessful()) {
                    title.setText(response.toString());
                }
                try {
                    EventDetail event = response.body();

                    if (event != null) {
                        title.setText(event.title); //- название

                        city.setText(event.location.name); //- город проведения
                        //- место проведения
                        place_title.setText(event.place.title);
                        place_address.setText(event.place.address);
                        place_phone.setText(event.place.phone);
                        place_site_url.setText(event.place.site_url);
                        age.setText(event.age_restriction); //- возрастное ограничение
                        price.setText(event.price); //- стоимость
                        site_url.setText(event.site_url); //- адрес события на сайте kudago.com
                        publication_date.setText(sdf.format(event.publication_date)); //- дата публикации

                        webView.loadDataWithBaseURL(null, event.description, "text/html", "utf-8", null);
//                        event.tags;
                        date_adapter = new EventDatesAdapter(mContext, event.dates);
                        lv_dates.setAdapter(date_adapter);
                        image_adapter = new EventImagesAdapter(mContext, event.images);
                        lv_images.setAdapter(image_adapter);
                        category_adapter = new EventCategoriesAdapter(mContext, event.categories);
                        lv_categories.setAdapter(category_adapter);
//                        lv_tags.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventDetail> call, @NonNull Throwable t) {
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
            Intent intentMain = new Intent(EventDetailActivity.this, MainActivity.class);
            startActivity(intentMain);
        }
        return super.onOptionsItemSelected(item);
    }
}