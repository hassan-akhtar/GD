package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/12/2017.
 * General Data
 */

public class SyncCheckInRequestDTO extends BaseDTO {

    private List<CheckIn> checkInList;

    public SyncCheckInRequestDTO(int callBackId, List<CheckIn> checkInList) {
        super(callBackId);
        this.checkInList = checkInList;
    }

    public List<CheckIn> getCheckInList() {
        return checkInList;
    }

    public void setCheckInList(List<CheckIn> checkInList) {
        this.checkInList = checkInList;
    }
}
