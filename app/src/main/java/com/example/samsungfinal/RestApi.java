package com.example.samsungfinal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {
    @GET("/events")
    Call<EventShortList> events(@Query("lang")String lang, @Query("location") String location,
                                @Query("actual_since") String actual_since,
                                @Query("actual_until") String actual_until);
}
