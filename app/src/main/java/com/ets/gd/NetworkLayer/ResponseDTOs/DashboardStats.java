package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 5/23/2017.
 * General Data
 */

public class DashboardStats extends RealmObject{

    FireBugDashBoard fireBugDashBoard;
    ToolHawkDashBoard toolHawkDashBoard;
    //InventoryDashboard etsInventoryDashboard;


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

}
