package com.tokovoj.nivltest.Mediator;

import com.tokovoj.nivltest.AppModel;
import com.tokovoj.nivltest.Network.Connection.GetNivlAssetsCallback;
import com.tokovoj.nivltest.Network.Connection.GetNivlDataCallback;
import com.tokovoj.nivltest.Network.Connection.MediaType;
import com.tokovoj.nivltest.Data.NivlData;

public class Mediator implements AppModel.Mediator
{
    public static final String TAG = "MEDIATOR";
    private static final String VIDEO_FORMAT_MEDIUM = "~medium.mp4";
    private static final String VIDEO_FORMAT_LARGE = "~large.mp4";
    private static final String VIDEO_FORMAT_ORIG = "~orig.mp4";
    private static final String IMAGE_FORMAT_MEDIUM = "~medium.jpg";
    private static final String IMAGE_FORMAT_LARGE = "~large.jpg";
    private static final String IMAGE_FORMAT_ORIG = "~orig.jpg";
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
        network.getNivlAssets(new GetNivlAssetsCallback()
        {
            @Override
            public void onComplete(String[] hrefs, MediaType mediaType1)
            {
                String asset = findAsset(hrefs, mediaType);
                if (!asset.equals("err"))
                {
                    ui.setNivlAssets(asset, mediaType);
                }
                else
                {
                    parseErrorCode(400);
                }
            }

            @Override
            public void onCompleteError(int code)
            {
                parseErrorCode(code);
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
        }, href, mediaType);
    }


    @Override
    public void getNivlData(String q, int startPage, String mediaType)
    {
        network.getNivlData(new GetNivlDataCallback()
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
                parseErrorCode(code);
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

    private String findAsset(String[] hrefs, MediaType mediaType)
    {
        switch (mediaType)
        {
            case IMAGE:
                for (String st: hrefs)
                {
                    if(st.substring(st.length()-IMAGE_FORMAT_MEDIUM.length()).equals(IMAGE_FORMAT_MEDIUM))
                    {
                        return st;
                    }
                }
                for (String st: hrefs)
                {
                    if(st.substring(st.length()-IMAGE_FORMAT_LARGE.length()).equals(IMAGE_FORMAT_LARGE))
                    {
                        return st;
                    }
                }
                for (String st: hrefs)
                {
                    if(st.substring(st.length()-IMAGE_FORMAT_ORIG.length()).equals(IMAGE_FORMAT_ORIG))
                    {
                        return st;
                    }
                }
                return "err";
            case VIDEO:
                for (String st: hrefs)
                {
                    if(st.substring(st.length()-VIDEO_FORMAT_MEDIUM.length()).equals(VIDEO_FORMAT_MEDIUM))
                    {
                        return st;
                    }
                }
                for (String st: hrefs)
                {
                    if(st.substring(st.length()-VIDEO_FORMAT_LARGE.length()).equals(VIDEO_FORMAT_LARGE))
                    {
                        return st;
                    }
                }
                for (String st: hrefs)
                {
                    if(st.substring(st.length()-VIDEO_FORMAT_ORIG.length()).equals(VIDEO_FORMAT_ORIG))
                    {
                        return st;
                    }
                }
                return "err";
            case AUDIO:
                return hrefs[0];
        }

        return "err";
    }

    private void parseErrorCode(int code)
    {
        switch (code)
        {
            case 204:
                ui.setNoResultErrorMessage();
                break;
            case 400:
                ui.setDownloadErrorMessage();
                break;
            default:
                break;
        }
    }
}
