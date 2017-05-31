package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/31/2017.
 * General Data
 */

public class JobNumber {

    private String name;
    private String code;

    public JobNumber(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public JobNumber() {
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
}
