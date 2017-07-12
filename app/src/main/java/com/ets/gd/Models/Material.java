package com.ets.gd.Models;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class Material {

    private String name;
    private String quantity;
    private int locID;
    private int jobNumberID;
    private int equipmentID;
    private int inventoryID;

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

    public Material(String name, String quantity, int locID, int jobNumberID, int inventoryID) {
        this.name = name;
        this.quantity = quantity;
        this.inventoryID = inventoryID;
        this.locID = locID;
        this.jobNumberID = jobNumberID;
    }


    public Material(int equipmentID, String name, String quantity, int locID, int inventoryID) {
        this.name = name;
        this.quantity = quantity;
        this.inventoryID = inventoryID;
        this.locID = locID;
        this.equipmentID = equipmentID;
    }


    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public int getJobNumberID() {
        return jobNumberID;
    }

    public void setJobNumberID(int jobNumberID) {
        this.jobNumberID = jobNumberID;
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
