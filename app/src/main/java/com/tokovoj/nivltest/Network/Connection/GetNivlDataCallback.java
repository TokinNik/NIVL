package com.tokovoj.nivltest.Network.Connection;

import com.tokovoj.nivltest.Data.NivlData;

public interface GetNivlDataCallback
{
    void onComplete(NivlData nivlData);

    void onCompleteError(int code);

    void onFailture();
}
