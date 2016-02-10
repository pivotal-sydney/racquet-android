package io.pivotal.racquetandroid.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Club implements Serializable {
    private String name;
    private Logo logo;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }
}
