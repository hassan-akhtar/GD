package com.ets.gd.NetworkLayer.ResponseDTOs;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Routes extends RealmObject implements Serializable {

    private int ID;
    private String Code;
    private String Description;
    private String RouteType;
    private String RouteStatus;
    private String RouteCreation;
    private String RouteStart;
    private String RouteCompletion;
    private String NextInspectionDate;
    private String DueDate;
    private String ModifiedBy;
    private String ModifiedTime;
    private int  CustomerID;
    private boolean isCompleted;
    private RealmList<RouteInspection> RouteInspections;
    private RealmList<RouteEquipment> RouteEquipments;
    private RealmList<RouteLocation> RouteLocations;

    public Routes() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRouteType() {
        return RouteType;
    }

    public void setRouteType(String routeType) {
        RouteType = routeType;
    }

    public String getRouteStatus() {
        return RouteStatus;
    }

    public void setRouteStatus(String routeStatus) {
        RouteStatus = routeStatus;
    }

    public String getRouteCreation() {
        return RouteCreation;
    }

    public void setRouteCreation(String routeCreation) {
        RouteCreation = routeCreation;
    }

    public String getRouteStart() {
        return RouteStart;
    }

    public void setRouteStart(String routeStart) {
        RouteStart = routeStart;
    }

    public String getRouteCompletion() {
        return RouteCompletion;
    }

    public void setRouteCompletion(String routeCompletion) {
        RouteCompletion = routeCompletion;
    }

    public String getNextInspectionDate() {
        return NextInspectionDate;
    }

    public void setNextInspectionDate(String nextInspectionDate) {
        NextInspectionDate = nextInspectionDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getModifiedTime() {
        return ModifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        ModifiedTime = modifiedTime;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public RealmList<RouteInspection> getRouteInspections() {
        return RouteInspections;
    }

    public void setRouteInspections(RealmList<RouteInspection> routeInspections) {
        RouteInspections = routeInspections;
    }

    public RealmList<RouteEquipment> getRouteEquipments() {
        return RouteEquipments;
    }

    public void setRouteEquipments(RealmList<RouteEquipment> routeEquipments) {
        RouteEquipments = routeEquipments;
    }

    public RealmList<RouteLocation> getRouteLocations() {
        return RouteLocations;
    }

    public void setRouteLocations(RealmList<RouteLocation> routeLocations) {
        RouteLocations = routeLocations;
    }
}
