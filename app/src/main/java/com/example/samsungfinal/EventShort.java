package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventShort implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("slug")
    public String slug;

    @SerializedName("age_restriction")
    public String age_restriction;

    public EventShort(int id, String title, String slug, String age_restriction) {
        this.id = id;
        this.title = title.substring(0, 1).toUpperCase() + title.substring(1);
        this.slug = slug;
        this.age_restriction = age_restriction;
    }

}
