package com.tokovoj.nivltest.Network.Connection;

import com.tokovoj.nivltest.AppModel;
import com.tokovoj.nivltest.Data.NivlData;
import com.tokovoj.nivltest.Network.NasaApi.DataApi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network implements AppModel.Network
{
    public static final String TAG = "NETWORK";

    private Retrofit retrofit;
    private DataApi nasaApi;

    public Network()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nasaApi = retrofit.create(DataApi.class);
    }

    @Override
    public void getNivlData(GetNivlDataCallback callback, String q, int startPage, String mediaType)
    {
        Call<NivlData> nivlData;
        nivlData = nasaApi.getNivlData(q, startPage, mediaType);
        nivlData.enqueue(new GetNivlData(callback));
    }

    @Override
    public void getNivlAssets(GetNivlAssetsCallback callback, String href, MediaType mediaType)
    {
        Call<String[]> nivlVideo;
        nivlVideo = nasaApi.getNivlAssets(href);
        nivlVideo.enqueue(new GetNivlAssets(callback, mediaType));
    }
}
