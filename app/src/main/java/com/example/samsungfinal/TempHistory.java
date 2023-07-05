package com.example.samsungfinal;

import java.io.Serializable;

public class TempHistory implements Serializable {
    long id;
    String city;
    String date_start;
    String date_from_ms;
    String date_to_ms;
    String date_end;

    public TempHistory(long id, String city, String date_start, String date_from_ms,
                       String date_end, String date_to_ms ) {
        this.id = id;
        this.city = city;
        this.date_start = date_start;
        this.date_from_ms = date_from_ms;
        this.date_end = date_end;
        this.date_to_ms = date_to_ms;
    }
}
