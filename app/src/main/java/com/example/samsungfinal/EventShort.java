package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventShort implements Serializable {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("slug")
    String slug;
}
