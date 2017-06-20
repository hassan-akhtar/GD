package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class SyncPostMoveInventoryRequestDTO extends BaseDTO {


    List<MoveInventory> moveInventoryList;

    public SyncPostMoveInventoryRequestDTO(int callBackId, List<MoveInventory> moveInventoryList) {
        super(callBackId);
        this.moveInventoryList = moveInventoryList;
    }

    public List<MoveInventory> getMoveInventoryList() {
        return moveInventoryList;
    }

    public void setMoveInventoryList(List<MoveInventory> moveInventoryList) {
        this.moveInventoryList = moveInventoryList;
    }
}
