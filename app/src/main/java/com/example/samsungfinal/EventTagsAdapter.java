package com.example.samsungfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventTagsAdapter extends BaseAdapter {
    private final LayoutInflater mLayoutInflater;
    private List<String> arrayTags;

    public EventTagsAdapter(Context ctx, List<String> arr) {
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }
    public List<String> getArrayMyData() {
        return arrayTags;
    }

    public void setArrayMyData(List<String> arrayMyData) {
        this.arrayTags = arrayMyData;
    }

    public int getCount () {
        return arrayTags.size();
    }

    public Object getItem (int position) {

        return arrayTags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_item_tags, null);

        TextView tag = (TextView)convertView.findViewById(R.id.event_item_tag);
        String md = arrayTags.get(position);

        tag.setText(md);

        return convertView;
    }
}
