package com.ets.gd.Models;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Routes extends RealmObject implements Serializable {

    private int id;
    private String code;
    private String desc;
    private String routeType;
    private String customerID;
    private RealmList<RouteLocation> routeLocations;

    public Routes(int id, String code, String desc, String routeType, String customerID, RealmList<RouteLocation> routeLocations) {
        this.id = id;
        this.code = code;
        this.desc = desc;
        this.routeType = routeType;
        this.customerID = customerID;
        this.routeLocations = routeLocations;
    }

    public Routes() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<RouteLocation> getRouteLocations() {
        return routeLocations;
    }

    public void setRouteLocations(RealmList<RouteLocation> routeLocations) {
        this.routeLocations = routeLocations;
    }
}
