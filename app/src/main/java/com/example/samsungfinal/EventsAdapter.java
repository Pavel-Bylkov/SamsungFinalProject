package com.example.samsungfinal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends BaseAdapter {
    Context context;
    private final LayoutInflater mLayoutInflater;
    private List<EventShort> arrayMyTempHistory;

    public EventsAdapter (Context ctx, List<EventShort> arr) {
        context = ctx;
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }
    public List<EventShort> getArrayMyData() {
        return arrayMyTempHistory;
    }

    public void setArrayMyData(List<EventShort> arrayMyData) {
        this.arrayMyTempHistory = arrayMyData;
    }

    public int getCount () {
        return arrayMyTempHistory.size();
    }

    public Object getItem (int position) {

        return position;
    }

    public long getItemId (int position) {
        EventShort md = arrayMyTempHistory.get(position);
        if (md != null) {
            return md.id;
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_item_events, null);

        TextView title= (TextView)convertView.findViewById(R.id.item_title);

        EventShort md = arrayMyTempHistory.get(position);
        title.setText(md.title);
        ImageButton bDetail = (ImageButton) convertView.findViewById(R.id.btn_detail);
        bDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", md);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

