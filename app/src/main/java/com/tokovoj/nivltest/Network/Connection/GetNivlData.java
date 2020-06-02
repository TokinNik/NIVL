package com.tokovoj.nivltest.Network.Connection;

import androidx.annotation.NonNull;

import com.tokovoj.nivltest.Data.NivlData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNivlData implements Callback<NivlData>
{
    private final GetNivlDataCallback callback;

    GetNivlData(GetNivlDataCallback callback)
    {
        this.callback = callback;
    }

    @Override
    public void onResponse(@NonNull Call<NivlData> call, @NonNull Response<NivlData> response)
    {
        if (response.isSuccessful())
        {
            if(callback != null)
            {
                if(response.body() != null)
                {
                    if (response.body().getCollection().getMetadata().getTotal_hits() > 0)
                    {
                        callback.onComplete(response.body());
                    }
                    else
                    {
                        callback.onCompleteError(204);
                    }
                }
                else
                {
                    callback.onCompleteError(response.code());
                }
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
    public void onFailure(@NonNull Call<NivlData> call, @NonNull Throwable t)
    {
        callback.onFailture();
    }
}
