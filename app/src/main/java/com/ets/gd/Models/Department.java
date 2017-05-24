package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/24/2017.
 * General Data
 */

public class Department {

    private String title;
    private String desc;

    public Department(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public Department() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
