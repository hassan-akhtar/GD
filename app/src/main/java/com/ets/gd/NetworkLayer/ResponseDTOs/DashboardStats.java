package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

public class DashboardStats extends RealmObject{

    FireBugDashBoard fireBugDashBoard;
    ToolHawkDashBoard toolHawkDashBoard;
    InventoryDashboard etsInventoryDashboard;


    public FireBugDashBoard getFireBugDashBoard() {
        return fireBugDashBoard;
    }

    public void setFireBugDashBoard(FireBugDashBoard fireBugDashBoard) {
        this.fireBugDashBoard = fireBugDashBoard;
    }

    public ToolHawkDashBoard getToolHawkDashBoard() {
        return toolHawkDashBoard;
    }

    public void setToolHawkDashBoard(ToolHawkDashBoard toolHawkDashBoard) {
        this.toolHawkDashBoard = toolHawkDashBoard;
    }

    public InventoryDashboard getEtsInventoryDashboard() {
        return etsInventoryDashboard;
    }

    public void setEtsInventoryDashboard(InventoryDashboard etsInventoryDashboard) {
        this.etsInventoryDashboard = etsInventoryDashboard;
    }
}
