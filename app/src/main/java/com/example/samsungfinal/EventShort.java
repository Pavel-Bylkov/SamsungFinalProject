package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventShort implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("slug")
    public String slug;

    public EventShort(int id, String title, String slug) {
        this.id = id;
        this.title = title;
        this.slug = slug;
    }
}
