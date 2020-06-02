package com.tokovoj.nivltest.Data;

import java.util.List;

public class Item
{
    private List<Data> data;
    private String href;
    private List<Link> links;


    public class Link
    {
        private String href;
        private String rel;
        private String render;

        public String getHref()
        {
            return href;
        }

        public String getRel()
        {
            return rel;
        }

        public String getRender()
        {
            return render;
        }

        public void setHref(String href)
        {
            this.href = href;
        }

        public void setRel(String rel)
        {
            this.rel = rel;
        }

        public void setRender(String render)
        {
            this.render = render;
        }
    }

    public List<Data> getData()
    {
        return data;
    }

    public String getHref()
    {
        return href;
    }

    public List<Link> getLinks()
    {
        return links;
    }

    public void setData(List<Data> data)
    {
        this.data = data;
    }

    public void setHref(String href)
    {
        this.href = href;
    }

    public void setLinks(List<Link> links)
    {
        this.links = links;
    }
}
