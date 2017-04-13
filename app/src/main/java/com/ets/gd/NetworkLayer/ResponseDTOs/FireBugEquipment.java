package com.ets.gd.NetworkLayer.ResponseDTOs;

import java.util.List;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class FireBugEquipment {

    private int ID;
    private String Code;
    private String SerialNo;
    private String ManufacturerDate;
    private String InitialFileDate;
    private String LastActivityDate;
    private AgentType AgentType;
    private Customer Customer;
    private DeviceType DeviceType;
    private Location Location;
    private Manufacturer Manufacturers;
    private Rating Rating;
    private Size Size;
    private VendorCode VendorCode;
    private Model Model;
    private int TotalDocuments;
    private int TotalNotes;
    private List<Images> Images;
    private List<InspectionDates> InspectionDates;
    private List<InspectionResult> InspectionResults;

}
