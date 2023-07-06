package com.example.samsungfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<TempHistory> arrayMyTempHistory;

    public HistoryAdapter (Context ctx, ArrayList<TempHistory> arr) {
        context = ctx;
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }
    public ArrayList<TempHistory> getArrayMyData() {
        return arrayMyTempHistory;
    }

    public void setArrayMyData(ArrayList<TempHistory> arrayMyData) {
        this.arrayMyTempHistory = arrayMyData;
    }

    public int getCount () {
        return arrayMyTempHistory.size();
    }

    public Object getItem (int position) {

        return position;
    }

    public long getItemId (int position) {
        TempHistory md = arrayMyTempHistory.get(position);
        if (md != null) {
            return md.id;
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_item_history, null);

        TextView city= (TextView)convertView.findViewById(R.id.item_city);
        TextView date = (TextView)convertView.findViewById(R.id.item_date);

        TempHistory md = arrayMyTempHistory.get(position);
        HashMap<String, String> locations = new HashMap<>();
        locations.put("ekb", convertView.getResources().getString(R.string.ekb));
        locations.put("kzn", convertView.getResources().getString(R.string.kzn));
        locations.put("msk", convertView.getResources().getString(R.string.msk));
        locations.put("nnv", convertView.getResources().getString(R.string.nnv));
        locations.put("nsk", convertView.getResources().getString(R.string.nsk));
        locations.put("spb", convertView.getResources().getString(R.string.spb));
        city.setText(locations.get(md.city));
        date.setText(String.format("%s - %s", md.date_start, md.date_end));
        ImageButton bDetail = (ImageButton) convertView.findViewById(R.id.btn_del);
        bDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventsListActivity.class); //.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", md);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
