package com.example.nivltest.Mediator;

import android.util.Log;

import com.example.nivltest.AppModel;
import com.example.nivltest.Net.MediaType;
import com.example.nivltest.Net.NivlData;
import com.example.nivltest.Net.Net;

public class Mediator implements AppModel.Mediator
{
    public static final String TAG = "MEDIATOR";
    private AppModel.UI ui;
    private AppModel.Network net;
    private boolean connectionLost;

    public Mediator(AppModel.Network net)
    {
        this.net = net;
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
        net.getNivlAssets(new Net.GetNivlAssetsCallback() {
            @Override
            public void onComplete(String href) {
                Log.d(TAG, "onComplete: " + href);
                ui.setNivlAssets(href, mediaType);
            }

            @Override
            public void onCompleteError(int code) {

            }

            @Override
            public void onFailture() {

            }
        }, href, mediaType);
    }

    @Override
    public void searchNivlData(String q, int startPage, String mediaType)
    {
        net.searchNivlData(new Net.GetNivlDataCallback() {
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
