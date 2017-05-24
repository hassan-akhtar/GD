package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

public class MaintenanceDue extends RealmObject {

    private int TotalOverDue;
    private int TotalCheckOut;
    private int MaintenanceOverDue;
    private int MaintenanceDue;


    public int getTotalOverDue() {
        return TotalOverDue;
    }

    public void setTotalOverDue(int totalOverDue) {
        TotalOverDue = totalOverDue;
    }

    public int getTotalCheckOut() {
        return TotalCheckOut;
    }

    public void setTotalCheckOut(int totalCheckOut) {
        TotalCheckOut = totalCheckOut;
    }

    public int getMaintenanceOverDue() {
        return MaintenanceOverDue;
    }

    public void setMaintenanceOverDue(int maintenanceOverDue) {
        MaintenanceOverDue = maintenanceOverDue;
    }

    public int getMaintenanceDue() {
        return MaintenanceDue;
    }

    public void setMaintenanceDue(int maintenanceDue) {
        MaintenanceDue = maintenanceDue;
    }
}
