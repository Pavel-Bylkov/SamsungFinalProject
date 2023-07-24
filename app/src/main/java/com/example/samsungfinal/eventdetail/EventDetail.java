package com.example.samsungfinal.eventdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventDetail implements Serializable {
    @SerializedName("id")
    public int id; //- идентификатор
    @SerializedName("publication_date")
    public long publication_date; //- дата публикации
    @SerializedName("dates")
    public List<Date> dates; //- даты проведения
    @SerializedName("title")
    public String title; //- название
    @SerializedName("slug")
    public String slug; //- слаг
    @SerializedName("place")
    public Place place; //- место проведения
    @SerializedName("description")
    public String description; //- описание
    @SerializedName("location")
    public Location location; //- город проведения
    @SerializedName("categories")
    public List<String> categories; //- список категорий
    @SerializedName("age_restriction")
    public String age_restriction; //- возрастное ограничение
    @SerializedName("price")
    public String price; //- стоимость
    @SerializedName("images")
    public List<Image> images; //- картинки
    @SerializedName("site_url")
    public String site_url; //- адрес события на сайте kudago.com
    @SerializedName("tags")
    public List<String> tags; //- тэги события

    public static class Date implements Serializable {
        @SerializedName("start_date")
        public String start_date;
        @SerializedName("start_time")
        public String start_time;
        @SerializedName("start")
        public long start;
        @SerializedName("end_date")
        public String end_date;
        @SerializedName("end_time")
        public String end_time;
        @SerializedName("end")
        public long end;
    }

    public static class Place implements Serializable {
        @SerializedName("id")
        public int id; //- идентификатор
        @SerializedName("title")
        public String title;
        @SerializedName("address")
        public String address;
        @SerializedName("phone")
        public String phone;
        @SerializedName("site_url")
        public String site_url;
    }

    public static class Location implements Serializable {
        @SerializedName("slug")
        public String slug;
        @SerializedName("name")
        public String name;
    }

    public static class Image implements Serializable {
        @SerializedName("image")
        public String image;
    }
}

