package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/12/2017.
 * General Data
 */

public class SyncCheckOutRequestDTO extends BaseDTO {

    private List<CheckOut> checkOutList;

    public SyncCheckOutRequestDTO(int callBackId, List<CheckOut> checkOutList) {
        super(callBackId);
        this.checkOutList = checkOutList;
    }

    public List<CheckOut> getCheckOutList() {
        return checkOutList;
    }

    public void setCheckOutList(List<CheckOut> checkOutList) {
        this.checkOutList = checkOutList;
    }
}
