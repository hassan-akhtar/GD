package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.Models.MoveInventoryCall;

import java.util.List;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class SyncPostMoveInventoryRequestDTO extends BaseDTO {


    List<MoveInventoryCall> moveInventoryList;

    public SyncPostMoveInventoryRequestDTO(int callBackId, List<MoveInventoryCall> moveInventoryList) {
        super(callBackId);
        this.moveInventoryList = moveInventoryList;
    }

    public List<MoveInventoryCall> getMoveInventoryList() {
        return moveInventoryList;
    }

    public void setMoveInventoryList(List<MoveInventoryCall> moveInventoryList) {
        this.moveInventoryList = moveInventoryList;
    }
}
