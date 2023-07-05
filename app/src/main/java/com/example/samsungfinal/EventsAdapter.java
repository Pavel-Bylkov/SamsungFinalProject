package com.example.samsungfinal;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class EventsAdapter extends ArrayAdapter<EventShort> {
    public EventsAdapter(@NonNull Context context, @NonNull EventShort[] objects) {
        super(context, R.layout.adapter_item_events, objects);
    }
}
