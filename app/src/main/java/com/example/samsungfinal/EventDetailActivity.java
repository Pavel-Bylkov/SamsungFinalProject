package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class EventDetailActivity extends AppCompatActivity {
    EventShort md;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        md= (EventShort) bundle.getSerializable("value");
        Toast.makeText(getApplicationContext(),"Get " + md.title, Toast.LENGTH_SHORT).show();
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