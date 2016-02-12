package io.pivotal.racquetandroid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Logo implements Serializable {
    private StandardLogo standard = new StandardLogo();
    private String url = "";

    public StandardLogo getStandard() {
        return standard;
    }

    public void setStandard(StandardLogo standard) {
        this.standard = standard;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
