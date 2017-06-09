package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;


public class SyncPostQuickCountRequestDTO extends BaseDTO{

    private List<QuickCount> lstQuickCount;

    public SyncPostQuickCountRequestDTO(int callBackId, List<QuickCount> lstQuickCount) {
        super(callBackId);
        this.lstQuickCount = lstQuickCount;
    }

    public SyncPostQuickCountRequestDTO() {

    }

    public List<QuickCount> getLstQuickCount() {
        return lstQuickCount;
    }

    public void setLstQuickCount(List<QuickCount> lstQuickCount) {
        this.lstQuickCount = lstQuickCount;
    }
}
