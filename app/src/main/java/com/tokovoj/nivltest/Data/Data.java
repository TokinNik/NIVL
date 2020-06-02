package com.tokovoj.nivltest.Data;

import java.util.List;

public class Data
{
    private String center;
    private String date_created;
    private String description;
    private List<String> keywords;
    private String media_type;
    private String nasa_id;
    private String title;


    public String getCenter()
    {
        return center;
    }

    public String getDate_created()
    {
        return date_created;
    }

    public String getDescription()
    {
        return description;
    }

    public List<String> getKeywords()
    {
        return keywords;
    }

    public String getMedia_type()
    {
        return media_type;
    }

    public String getNasa_id()
    {
        return nasa_id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setCenter(String center)
    {
        this.center = center;
    }

    public void setDate_created(String date_created)
    {
        this.date_created = date_created;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setKeywords(List<String> keywords)
    {
        this.keywords = keywords;
    }

    public void setMedia_type(String media_type)
    {
        this.media_type = media_type;
    }

    public void setNasa_id(String nasa_id)
    {
        this.nasa_id = nasa_id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
