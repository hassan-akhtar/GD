package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class SyncToolhawkTransferDTO extends  BaseDTO {

    List<TransferToolhawk> transferToolhawkList;

    public SyncToolhawkTransferDTO(int callBackId, List<TransferToolhawk> transferToolhawkList) {
        super(callBackId);
        this.transferToolhawkList = transferToolhawkList;
    }

    public List<TransferToolhawk> getTransferToolhawkList() {
        return transferToolhawkList;
    }

    public void setTransferToolhawkList(List<TransferToolhawk> transferToolhawkList) {
        this.transferToolhawkList = transferToolhawkList;
    }
}
