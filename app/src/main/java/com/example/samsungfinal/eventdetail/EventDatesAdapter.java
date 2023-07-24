package com.example.samsungfinal.eventdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.samsungfinal.R;
import com.example.samsungfinal.eventdetail.EventDetail;

import java.util.List;

public class EventDatesAdapter extends BaseAdapter {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
    Context context;
    private final LayoutInflater mLayoutInflater;
    private List<EventDetail.Date> arrayDates;

    public EventDatesAdapter (Context ctx, List<EventDetail.Date> arr) {
        context = ctx;
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }
    public List<EventDetail.Date> getArrayMyData() {
        return arrayDates;
    }

    public void setArrayMyData(List<EventDetail.Date> arrayMyData) {
        this.arrayDates = arrayMyData;
    }

    public int getCount () {
        return arrayDates.size();
    }

    public Object getItem (int position) {

        return arrayDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_item_dates, null);

        TextView date_start= (TextView)convertView.findViewById(R.id.event_item_date_start);
        TextView time_start= (TextView)convertView.findViewById(R.id.event_item_time_start);
        TextView date_end= (TextView)convertView.findViewById(R.id.event_item_date_end);
        TextView time_end= (TextView)convertView.findViewById(R.id.event_item_time_end);
        EventDetail.Date md = arrayDates.get(position);
        String str_date_start = "";
        if (md.start_date != null)
            str_date_start = md.start_date;
        else
            str_date_start = sdf.format(md.start);
        date_start.setText(str_date_start);

        String str_time_start = "";
        if (md.start_time != null)
            str_time_start = md.start_time;
        else
            str_time_start = sdf2.format(md.start);
        time_start.setText(str_time_start);

        String str_date_end = "";
        if (md.end_date != null)
            str_date_end = md.end_date;
        else
            str_date_end = sdf.format(md.end);
        date_end.setText(str_date_end);

        String str_time_end = "";
        if (md.end_time != null)
            str_time_end = md.end_time;
        else
            str_time_end = sdf2.format(md.end);
        time_end.setText(str_time_end);

        return convertView;
    }
}