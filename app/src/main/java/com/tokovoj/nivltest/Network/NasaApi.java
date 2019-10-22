package com.tokovoj.nivltest.Network;


import com.tokovoj.nivltest.Data.NivlData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NasaApi
{
    @GET("search")
    Call<NivlData> getNivlData(@Query("q") String g, @Query("page") int page);
    @GET("search")
    Call<NivlData> getNivlData(@Query("q") String g,@Query("page") int page, @Query("media_type") String mediaType);
    @GET("search")
    Call<NivlData> getNivlData(@Query("q") String g,@Query("page") int page, @Query("media_type") String mediaType, @Query("year_start") String yearStart);
    @GET
    Call<String[]> getNivlAssets(@Url String href);
}
