package com.tokovoj.nivltest.Mediator;

import android.util.Log;

import com.tokovoj.nivltest.AppModel;
import com.tokovoj.nivltest.Network.MediaType;
import com.tokovoj.nivltest.Data.NivlData;
import com.tokovoj.nivltest.Network.Network;

public class Mediator implements AppModel.Mediator
{
    public static final String TAG = "MEDIATOR";
    private AppModel.UI ui;
    private AppModel.Network network;
    private boolean connectionLost;

    public Mediator(AppModel.Network network)
    {
        this.network = network;
        connectionLost = false;
    }

    @Override
    public void attachUI(AppModel.UI ui)
    {
        this.ui = ui;
    }

    @Override
    public void detachUI()
    {
        this.ui = null;
    }

    @Override
    public void getNivlAssets(String href, final MediaType mediaType)
    {
        network.getNivlAssets(new Network.GetNivlAssetsCallback()
        {
            @Override
            public void onComplete(String href)
            {
                Log.d(TAG, "onComplete: " + href);
                ui.setNivlAssets(href, mediaType);
            }

            @Override
            public void onCompleteError(int code)
            {

            }

            @Override
            public void onFailture()
            {

            }
        }, href, mediaType);
    }

    @Override
    public void searchNivlData(String q, int startPage, String mediaType)
    {
        network.searchNivlData(new Network.GetNivlDataCallback()
        {
            @Override
            public void onComplete(NivlData nivlData)
            {
                ui.onItemsUpdate(nivlData);
                connectionLost = false;
            }

            @Override
            public void onCompleteError(int code)
            {
                ui.setErrorMessage(code);
            }

            @Override
            public void onFailture()
            {
                if (!connectionLost)
                {
                    ui.setConnectionLostMessage();
                    connectionLost = true;
                }

            }
        }, q, startPage, mediaType);

    }
}
