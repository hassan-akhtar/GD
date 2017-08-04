package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.Interfaces.BarcodeScan;

import java.util.List;

/**
 * Created by hakhtar on 8/4/2017.
 * General Data
 */

public class ChangeRouteStatusDTO  extends BaseDTO{

    private String routeStatus;
    private List<Integer> routeIDs;


    public ChangeRouteStatusDTO(int callBackId, String routeStatus, List<Integer> routeIDs) {
        super(callBackId);
        this.routeStatus = routeStatus;
        this.routeIDs = routeIDs;
    }

    public String getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(String routeStatus) {
        this.routeStatus = routeStatus;
    }
}
