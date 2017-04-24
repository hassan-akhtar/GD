package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;


public class SyncPostRequestDTO extends BaseDTO {

    private String userId;
    private List<Equipment> lstAddEquipment;
    private List<Equipment> lstEditEquipment;
}
