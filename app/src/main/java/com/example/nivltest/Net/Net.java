package com.example.nivltest.Net;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nivltest.AppModel;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Net implements AppModel.Network
{
    public static final String TAG = "NET";
    public static final String VIDEO_FORMAT_MEDIUM = "~medium.mp4";
    public static final String VIDEO_FORMAT_LARGE = "~large.mp4";
    public static final String VIDEO_FORMAT_ORIG = "~orig.mp4";
    public static final String IMAGE_FORMAT_MEDIUM = "~medium.jpg";
    public static final String IMAGE_FORMAT_LARGE = "~large.jpg";
    public static final String IMAGE_FORMAT_ORIG = "~orig.jpg";

    private  Retrofit retrofit;
    private NasaApi nasaApi;

    public Net()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nasaApi = retrofit.create(NasaApi.class);
    }

    public interface GetNivlDataCallback
    {
        void onComplete(NivlData nivlData);

        void onCompleteError(int code);

        void onFailture();
    }

    public interface GetNivlAssetsCallback
    {
        void onComplete(String href);

        void onCompleteError(int code);

        void onFailture();
    }

    @Override
    public void searchNivlData(GetNivlDataCallback callback, String q, int startPage, String mediaType)
    {
        Call<NivlData> nivlData;
        nivlData = nasaApi.getNivlData(q, startPage, mediaType);
        nivlData.enqueue(new NivlCallback(callback));
    }

    @Override
    public void getNivlAssets(GetNivlAssetsCallback callback, String href, MediaType mediaType)
    {
        Call<String[]> nivlVideo;
        nivlVideo = nasaApi.getNivlAssets(href);
        nivlVideo.enqueue(new NivlAssetsCallback(callback, mediaType));
    }

    private class NivlCallback implements Callback<NivlData>
    {
        private final GetNivlDataCallback callback;

        NivlCallback(GetNivlDataCallback callback)
        {
            this.callback = callback;
        }

        @Override
        public void onResponse(@NonNull Call<NivlData> call, @NonNull Response<NivlData> response) {
            if (response.isSuccessful())
            {
                Log.d(TAG, "onResponse: ");
                if(callback != null)
                {
                    if(response.body() != null&& response.body().getCollection().getMetadata().getTotal_hits() > 0)
                    {
                        callback.onComplete(response.body());
                    }
                    else
                    {
                        callback.onCompleteError(204);
                    }
                }
            } else
            {
                Log.d(TAG, "onResponse: err " + response.code() + " " + response.message());
                if(callback != null)
                {
                    callback.onCompleteError(response.code());
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<NivlData> call, @NonNull Throwable t) {
            Log.d(TAG, "onFailure: " + t + " " + retrofit.baseUrl());
            callback.onFailture();
        }
    }

    private class NivlAssetsCallback implements Callback<String[]>
    {
        private final GetNivlAssetsCallback callback;
        private MediaType mediaType;

        NivlAssetsCallback(GetNivlAssetsCallback callback, MediaType mediaType)
        {
            this.callback = callback;
            this.mediaType = mediaType;
        }

        @Override
        public void onResponse(@NonNull Call<String[]> call, @NonNull Response<String[]> response)
        {
            if (response.isSuccessful())
            {
                if(callback != null && response.body() != null)
                {
                    boolean failFlag;
                    switch (mediaType)
                    {
                        case IMAGE:
                            Log.d(TAG, "onResponse: IMAGE " + Arrays.toString(response.body()));
                            failFlag = true;
                            for (String st: response.body())
                            {
                                if(st.substring(st.length()-IMAGE_FORMAT_MEDIUM.length()).equals(IMAGE_FORMAT_MEDIUM))
                                {
                                    Log.d(TAG, "onResponse: IMAGE " + st);
                                    callback.onComplete(st);
                                    failFlag = false;
                                    break;
                                }
                            }
                            if (failFlag)
                            {
                                for (String st: response.body())
                                {
                                    if(st.substring(st.length()-IMAGE_FORMAT_LARGE.length()).equals(IMAGE_FORMAT_LARGE))
                                    {
                                        Log.d(TAG, "onResponse: IMAGE " + st);
                                        callback.onComplete(st);
                                        failFlag = true;
                                        break;
                                    }
                                }
                            }
                            if(failFlag)
                            {
                                for (String st: response.body())
                                {
                                    if(st.substring(st.length()-IMAGE_FORMAT_ORIG.length()).equals(IMAGE_FORMAT_ORIG))
                                    {
                                        Log.d(TAG, "onResponse: IMAGE " + st);
                                        callback.onComplete(st);
                                        break;
                                    }
                                }
                            }
                            callback.onCompleteError(400);
                            break;
                        case VIDEO:
                            Log.d(TAG, "onResponse: VIDEO " + Arrays.toString(response.body()));
                            failFlag = true;
                            for (String st: response.body())
                            {
                                if(st.substring(st.length()-VIDEO_FORMAT_MEDIUM.length()).equals(VIDEO_FORMAT_MEDIUM))
                                {
                                    Log.d(TAG, "onResponse: VIDEO " + st);
                                    callback.onComplete(st);
                                    failFlag = false;
                                    break;
                                }
                            }
                            if (failFlag)
                            {
                                for (String st: response.body())
                                {
                                    if(st.substring(st.length()-VIDEO_FORMAT_LARGE.length()).equals(VIDEO_FORMAT_LARGE))
                                    {
                                        Log.d(TAG, "onResponse: VIDEO " + st);
                                        callback.onComplete(st);
                                        failFlag = true;
                                        break;
                                    }
                                }
                            }
                            if(failFlag)
                            {
                                for (String st: response.body())
                                {
                                    if(st.substring(st.length()-VIDEO_FORMAT_ORIG.length()).equals(VIDEO_FORMAT_ORIG))
                                    {
                                        Log.d(TAG, "onResponse: VIDEO " + st);
                                        callback.onComplete(st);
                                        break;
                                    }
                                }
                            }
                            callback.onCompleteError(400);
                            break;
                        case AUDIO:
                            Log.d(TAG, "onResponse: AUDIO " + response.body()[0]);
                            callback.onComplete(response.body()[0]);
                            break;
                    }
                }
            } else
            {
                Log.d(TAG, "onResponse: err " + response.code() + " " + response.message());
                if(callback != null)
                {
                    callback.onCompleteError(response.code());
                }
            }
        }

        @Override
        public void onFailure(@NonNull Call<String[]> call, @NonNull Throwable t) {
            Log.d(TAG, "onFailure: " + t + " " + retrofit.baseUrl());
            callback.onFailture();
        }
    }
}
