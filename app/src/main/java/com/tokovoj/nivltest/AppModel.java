package com.tokovoj.nivltest;

import com.tokovoj.nivltest.Network.MediaType;
import com.tokovoj.nivltest.Data.NivlData;

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
        void searchNivlData(com.tokovoj.nivltest.Network.Network.GetNivlDataCallback callback, String q, int startPage, String mediaType);

        void getNivlAssets(com.tokovoj.nivltest.Network.Network.GetNivlAssetsCallback callback, String href, MediaType mediaType);
    }

    interface Mediator
    {
        void attachUI(AppModel.UI ui);

        void detachUI();

        void searchNivlData(String q, int startPage, String mediaType);

        void getNivlAssets(String href, MediaType media_type);
    }
}
