package com.example.samsungfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

public class HistoryAdapter extends ArrayAdapter<TempHistory> {
    public HistoryAdapter(Context context, TempHistory[] arr) {
        super(context, R.layout.adapter_item_history, arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final TempHistory history = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_item_history, null);
        }

        ((TextView) convertView.findViewById(R.id.item_city)).setText(history.city);
        ((TextView) convertView.findViewById(R.id.item_date)).setText(history.date_start + " - "+ history.date_end);

        ImageButton btn_del = (ImageButton) convertView.findViewById(R.id.btn_del);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo del this item from list view
                Toast.makeText(getContext(),"Func in develop",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
