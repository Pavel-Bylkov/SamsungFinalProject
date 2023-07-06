package com.example.samsungfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    public final String TAG = "MainActivity";

    Button bSearch;
    DBHistory mDBConnector;
    Context mContext;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        mDBConnector=new DBHistory(this);

        bSearch = findViewById(R.id.btn_new_search);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                updateList();
            }
        });

        adapter = new HistoryAdapter(mContext,mDBConnector.selectAll());
        ListView lv = (ListView) findViewById(R.id.list_history);
        lv.setAdapter(adapter);
    }
    private void updateList () {
        adapter.setArrayMyData(mDBConnector.selectAll());
        adapter.notifyDataSetChanged();
    }

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


}