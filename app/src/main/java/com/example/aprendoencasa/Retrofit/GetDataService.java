package com.example.aprendoencasa.Retrofit;

import com.example.aprendoencasa.Model.VideoDetalle;
import com.example.aprendoencasa.Model.videostats.VideoStats;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("search")
    Call<VideoDetalle> getVideoData(
            @Query("part") String part,
            @Query("maxResults") Integer maxResults,
            @Query("channelId") String channelId,
            @Query("key") String key,
            @Query("order") String order
            );

    @GET("videos")
    Call<VideoStats> getVideStats(
            @Query("part") String part,
            @Query("key") String key,
            @Query("id") String id
    );

}
