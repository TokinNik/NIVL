package com.tokovoj.nivltest.Network.Connection;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNivlAssets implements Callback<String[]>
{
    private final GetNivlAssetsCallback callback;
    private MediaType mediaType;

    GetNivlAssets(GetNivlAssetsCallback callback, MediaType mediaType)
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
                callback.onComplete(response.body(), mediaType);
            }
        }
        else
        {
            if(callback != null)
            {
                callback.onCompleteError(response.code());
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<String[]> call, @NonNull Throwable t)
    {
        callback.onFailture();
    }
}
