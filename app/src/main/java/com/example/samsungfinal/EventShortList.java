package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventShortList implements Serializable {
    @SerializedName("count")
    public int count;
    @SerializedName("next")
    public String next;
    @SerializedName("previous")
    public String previous;
    @SerializedName("results")
    public List<EventShort> results= null;

    public EventShortList (int count, String next, String previous, List<EventShort> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }
}
