package com.example.samsungfinal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventDetail implements Serializable {
    @SerializedName("id")
    public int id; //- идентификатор
    @SerializedName("publication_date")
    public String publication_date; //- дата публикации
    @SerializedName("dates")
    public String dates; //- даты проведения
    @SerializedName("title")
    public String title; //- название
    @SerializedName("slug")
    public String slug; //- слаг
    @SerializedName("place")
    public String place; //- место проведения
    @SerializedName("description")
    public String description; //- описание
    @SerializedName("location")
    public String location; //- город проведения
    @SerializedName("categories")
    public List<String> categories; //- список категорий
    @SerializedName("age_restriction")
    public String age_restriction; //- возрастное ограничение
    @SerializedName("price")
    public String price; //- стоимость
    @SerializedName("images")
    public String images; //- картинки
    @SerializedName("site_url")
    public String site_url; //- адрес события на сайте kudago.com
    @SerializedName("tags")
    public List<String> tags; //- тэги события

}
