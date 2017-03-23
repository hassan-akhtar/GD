package com.ets.gd.Models;

import java.io.Serializable;

/**
 * Created by hakhtar on 3/23/2017.
 * General Data
 */

public class Asset implements Serializable{

    private String name;
    private String code;
    private String tag;
    private String location;


    public Asset() {
    }

    public Asset(String name, String code, String tag, String location) {
        this.name = name;
        this.code = code;
        this.tag = tag;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
