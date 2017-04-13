package com.ets.gd.NetworkLayer.ResponseDTOs;


import java.util.List;

public class SyncGetResponseDTO extends ResponseDTO {

    private int CustomerId;
    private String DeviceId;
    private String SyncGetTime;
    List<RegisteredDevice> lstDevices;
    List<MobileUser> lstMusers;
    List<FireBugEquipment> lstFbEquipments;
    List<Locations> lstLocations;
    List<DeviceType> lstDeviceType;
    List<Manufacturer> lstManufacturers;
    List<Model> lstModels;
    List<VendorCode> lstVendorCodes;
    List<AgentType> lstAgentTypes;
}
