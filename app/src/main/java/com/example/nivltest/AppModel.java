package com.example.nivltest;

import com.example.nivltest.Net.MediaType;
import com.example.nivltest.Net.NivlData;
import com.example.nivltest.Net.Net;

public interface AppModel
{
    interface UI
    {
        void onItemsUpdate(NivlData nivlData);

        void setErrorMessage(int code);

        void setConnectionLostMessage();

        void setNivlAssets(String href, MediaType mediaType);
    }

    interface Network
    {
        void searchNivlData(Net.GetNivlDataCallback callback, String q, int startPage, String mediaType);

        void getNivlAssets(Net.GetNivlAssetsCallback callback, String href, MediaType mediaType);
    }

    interface Mediator
    {
        void attachUI(AppModel.UI ui);

        void detachUI();

        void searchNivlData(String q, int startPage, String mediaType);

        void getNivlAssets(String href, MediaType media_type);
    }
}
