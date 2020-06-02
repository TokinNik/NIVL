package com.tokovoj.nivltest.Network.Connection;

public interface GetNivlAssetsCallback
{
    void onComplete(String[] hrefs, MediaType mediaType);

    void onCompleteError(int code);

    void onFailture();
}
