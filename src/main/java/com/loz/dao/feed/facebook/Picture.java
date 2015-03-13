package com.loz.dao.feed.facebook;

/**
 * Created by larcher on 13/03/2015.
 */
public class Picture {
    public class Data {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
