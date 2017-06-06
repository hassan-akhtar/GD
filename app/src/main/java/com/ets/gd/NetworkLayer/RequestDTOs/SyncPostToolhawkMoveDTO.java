package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class SyncPostToolhawkMoveDTO extends BaseDTO  {

    private List<ToolhawkMoveDTO> moveDTOList;

    public SyncPostToolhawkMoveDTO(int callBackId, List<ToolhawkMoveDTO> moveDTOList) {
        super(callBackId);
        this.moveDTOList = moveDTOList;
    }

    public List<ToolhawkMoveDTO> getMoveDTOList() {
        return moveDTOList;
    }

    public void setMoveDTOList(List<ToolhawkMoveDTO> moveDTOList) {
        this.moveDTOList = moveDTOList;
    }
}
