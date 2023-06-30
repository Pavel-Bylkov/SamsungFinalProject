package com.example.samsungfinal;

import java.io.Serializable;

public class TempHistory implements Serializable {
    long id;
    String city;
    String date_start;
    String date_end;

    public TempHistory(long id, String city, String date_start, String date_end) {
        this.id = id;
        this.city = city;
        this.date_start = date_start;
        this.date_end = date_end;
    }
}
