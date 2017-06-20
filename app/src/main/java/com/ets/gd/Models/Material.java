package com.ets.gd.Models;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class Material {

    private String name;
    private String quantity;
    private int locID;

    public Material() {
    }

    public Material(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Material(String name, String quantity, int locID) {
        this.name = name;
        this.quantity = quantity;
        this.locID = locID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getLocID() {
        return locID;
    }

    public void setLocID(int locID) {
        this.locID = locID;
    }
}
