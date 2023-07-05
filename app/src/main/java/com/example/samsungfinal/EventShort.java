package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

public class EventShort {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("slug")
    String slug;
}
