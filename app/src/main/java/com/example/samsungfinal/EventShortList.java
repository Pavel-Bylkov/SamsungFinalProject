package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventShortList {
    @SerializedName("count")
    int count;
    @SerializedName("next")
    String next;
    @SerializedName("previous")
    String previous;
    @SerializedName("results")
    public List<EventShort> results = new ArrayList<>();
}
