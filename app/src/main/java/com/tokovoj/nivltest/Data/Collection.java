package com.tokovoj.nivltest.Data;

import java.util.List;

public class Collection
{
    private String href;
    private List<Item> items;
    private List<Link> links;
    private Metadata metadata;
    private String version;


    public String getHref()
    {
        return href;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public List<Link> getLinks()
    {
        return links;
    }

    public Metadata getMetadata()
    {
        return metadata;
    }

    public String getVersion()
    {
        return version;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    public void setLinks(List<Link> links)
    {
        this.links = links;
    }

    public void setMetadata(Metadata metadata)
    {
        this.metadata = metadata;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
}
