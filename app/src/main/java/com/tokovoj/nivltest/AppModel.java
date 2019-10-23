package com.tokovoj.nivltest;

import com.tokovoj.nivltest.Network.Connection.GetNivlAssetsCallback;
import com.tokovoj.nivltest.Network.Connection.GetNivlDataCallback;
import com.tokovoj.nivltest.Network.Connection.MediaType;
import com.tokovoj.nivltest.Data.NivlData;

public interface AppModel
{
    interface UI
    {
        void onItemsUpdate(NivlData nivlData);

        void setNoResultErrorMessage();

        void setDownloadErrorMessage();

        void setConnectionLostMessage();

        void setNivlAssets(String href, MediaType mediaType);
    }

    interface Network
    {
        void getNivlData(GetNivlDataCallback callback, String q, int startPage, String mediaType);

        void getNivlAssets(GetNivlAssetsCallback callback, String href, MediaType mediaType);
    }

    interface Mediator
    {
        void attachUI(AppModel.UI ui);

        void detachUI();

        void getNivlData(String q, int startPage, String mediaType);

        void getNivlAssets(String href, MediaType media_type);
    }
}
