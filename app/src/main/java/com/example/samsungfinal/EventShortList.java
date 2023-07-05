package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventShortList implements Serializable {
    @SerializedName("count")
    int count;
    @SerializedName("next")
    String next;
    @SerializedName("previous")
    String previous;
    @SerializedName("results")
    public ArrayList<EventShort> results = new ArrayList<>();
}
