package com.example.nivltest.Net;

import java.util.List;

public class NivlData
{
    private Collection collection;
    public static class Collection
    {
        private String href;
        private List<Item> items;
        public static class Item
        {
            private List<Data> data;
            public static  class Data
            {
                private String center;
                private String date_created;
                private String description;
                private List<String> keywords;
                private String media_type;
                private String nasa_id;
                private String title;

                public String getCenter() {
                    return center;
                }

                public String getDate_created() {
                    return date_created;
                }

                public String getDescription() {
                    return description;
                }

                public List<String> getKeywords() {
                    return keywords;
                }

                public String getMedia_type() {
                    return media_type;
                }

                public String getNasa_id() {
                    return nasa_id;
                }

                public String getTitle() {
                    return title;
                }

                public void setCenter(String center) {
                    this.center = center;
                }

                public void setDate_created(String date_created) {
                    this.date_created = date_created;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setKeywords(List<String> keywords) {
                    this.keywords = keywords;
                }

                public void setMedia_type(String media_type) {
                    this.media_type = media_type;
                }

                public void setNasa_id(String nasa_id) {
                    this.nasa_id = nasa_id;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
            private String href;
            private List<Link> links;
            public static  class Link
            {
                private String href;
                private String rel;
                private String render;

                public String getHref() {
                    return href;
                }

                public String getRel() {
                    return rel;
                }

                public String getRender() {
                    return render;
                }

                public void setHref(String href) {
                    this.href = href;
                }

                public void setRel(String rel) {
                    this.rel = rel;
                }

                public void setRender(String render) {
                    this.render = render;
                }
            }

            public List<Data> getData() {
                return data;
            }

            public String getHref() {
                return href;
            }

            public List<Link> getLinks() {
                return links;
            }

            public void setData(List<Data> data) {
                this.data = data;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public void setLinks(List<Link> links) {
                this.links = links;
            }
        }
        private List<Link> links;
        public static  class Link
        {
            private String href;
            private String prompt;
            private String rel;

            public String getHref() {
                return href;
            }

            public String getPrompt() {
                return prompt;
            }

            public String getRel() {
                return rel;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public void setPrompt(String prompt) {
                this.prompt = prompt;
            }

            public void setRel(String rel) {
                this.rel = rel;
            }
        }
        private Metadata metadata;
        public static  class Metadata
        {
            private int total_hits;

            public int getTotal_hits() {
                return total_hits;
            }

            public void setTotal_hits(int total_hits) {
                this.total_hits = total_hits;
            }
        }
        private String version;

        public String getHref() {
            return href;
        }

        public List<Item> getItems() {
            return items;
        }

        public List<Link> getLinks() {
            return links;
        }

        public Metadata getMetadata() {
            return metadata;
        }

        public String getVersion() {
            return version;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public void setLinks(List<Link> links) {
            this.links = links;
        }

        public void setMetadata(Metadata metadata) {
            this.metadata = metadata;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return collection;
    }
}
