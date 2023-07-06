package com.example.samsungfinal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApi {
    // https://kudago.com/public-api/v1.4/events/?lang=ru&location=nsk&actual_since=1444385206&actual_until=144385405
    String BASE_URL = "https://kudago.com/public-api/v1.4/";
    @GET("events")
    Call<EventShortList> events(@Query("lang")String lang, @Query("location") String location,
                                @Query("actual_since") String actual_since,
                                @Query("actual_until") String actual_until,
                                @Query("page") String page, @Query("page_size") String page_size,
                                @Query(value = "fields", encoded=true) String fields);
    // events/60843/?lang=&fields=
    @GET("events/{id}")
    Call<EventShortList> event(@Path("id") int id, @Query("lang")String lang,
                               @Query(value = "fields", encoded=true) String fields,
                               @Query(value = "expand", encoded=true) String expand);
}
