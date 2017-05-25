package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 5/17/2017.
 * General Data
 */

public class RouteInspection extends RealmObject{

    private int ID;
    private String InspectionType;
    private int RouteID;
    private boolean IsHydro;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getInspectionType() {
        return InspectionType;
    }

    public void setInspectionType(String inspectionType) {
        InspectionType = inspectionType;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }

    public boolean isHydro() {
        return IsHydro;
    }

    public void setHydro(boolean hydro) {
        IsHydro = hydro;
    }
}
