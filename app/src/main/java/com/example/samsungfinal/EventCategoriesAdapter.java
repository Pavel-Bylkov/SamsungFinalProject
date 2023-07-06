package com.example.samsungfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EventCategoriesAdapter extends BaseAdapter {
    private final LayoutInflater mLayoutInflater;
    private List<String> arrayCategories;

    public EventCategoriesAdapter(Context ctx, List<String> arr) {
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }
    public List<String> getArrayMyData() {
        return arrayCategories;
    }

    public void setArrayMyData(List<String> arrayMyData) {
        this.arrayCategories = arrayMyData;
    }

    public int getCount () {
        return arrayCategories.size();
    }

    public Object getItem (int position) {

        return arrayCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.adapter_item_categories, null);

        TextView category = (TextView)convertView.findViewById(R.id.event_item_category);
        String md = arrayCategories.get(position);

        category.setText(md);

        return convertView;
    }
}
