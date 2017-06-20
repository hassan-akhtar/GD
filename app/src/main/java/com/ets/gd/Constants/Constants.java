package com.ets.gd.Constants;

public class Constants {


    public static final int READ_TIMEOUT_SECONDS = 600;
    public static final int CONNECTION_TIMEOUT_SECONDS = 600;

    // Base URLs
    public static final String BASE_URL_UAT = "http://gd-uat.deliveredoncloud.com//Services/Services/Mobile";
    public static final String BASE_URL_STAGE = "http://192.168.0.131/Services/Services/Mobile/";

    public static final String URL_BASE = BASE_URL_UAT;

    //  Callback Keys Firebug
    public static final int RESPONSE_LOGIN = 0;
    public static final int RESPONSE_CUSTOMERS = 1;
    public static final int RESPONSE_SYNC_GET = 2;
    public static final int RESPONSE_SYNC_POST_EQUIPMENT = 3;
    public static final int RESPONSE_SYNC_POST_ADD_LOCATION = 4;
    public static final int RESPONSE_SYNC_POST_MOVE_TRANSFER = 5;
    public static final int RESPONSE_SYNC_POST_INSPECT_EQUIPMENT = 6;

    //  Callback Keys Toolhawk
    public static final int RESPONSE_SYNC_POST_TOOLHAWK_EQUIPMENT = 7;
    public static final int RESPONSE_SYNC_POST_ETS_LOCATION = 8;
    public static final int RESPONSE_SYNC_POST_TOOLHAWK_TRANSFER = 9;
    public static final int RESPONSE_SYNC_POST_TOOLHAWK_MOVE = 10;
    public static final int RESPONSE_SYNC_POST_QUICK_COUNT = 11;
    public static final int RESPONSE_SYNC_POST_MAINTENANCE = 12;
    public static final int RESPONSE_SYNC_POST_CHECKOUT = 13;
    public static final int RESPONSE_SYNC_POST_CHECKIN = 14;

    //  Callback Keys Inventory
    public static final int RESPONSE_SYNC_POST_MOVE_INVENTORY = 15;
    public static final int RESPONSE_SYNC_POST_ISSUE = 16;
    public static final int RESPONSE_SYNC_POST_RECEIVE = 17;


    // Urls Firebug
    public static final String URL_LOGIN = "/Login";
    public static final String URL_SYNC_GET = "/GetCustomerData";
    public static final String URL_SYNC_POST_EQUIPMENT = "/CreateEquipment";
    public static final String URL_SYNC_POST_ADD_LOCATION = "/CreateFbLocations";
    public static final String URL_SYNC_POST_MOVE_TRANSFER = "/MoveOrTransferEquipments";
    public static final String URL_SYNC_POST_INSPECT_EQUIPMENT = "/InspectEquipments";

    // Urls Toolhawk
    public static final String URL_SYNC_POST_TOOLHAWK_EQUIPMENT = "/CreateToolHawkEquipment";
    public static final String URL_SYNC_POST_ETS_LOCATION = "/CreateETSLocation";
    public static final String URL_SYNC_POST_TOOLHAWK_TRANSFER = "/TransferToolHawkEquipment";
    public static final String URL_SYNC_POST_TOOLHAWK_MOVE = "/MoveToolHawkEquipment";
    public static final String URL_SYNC_POST_QUICK_COUNT = "/QuickCount";
    public static final String URL_SYNC_POST_MAINTENANCE  = "/ToolHawkEquipmentMaintenance";
    public static final String URL_SYNC_POST_CHECKOUT  = "/CheckOutToolHawkEquipment";
    public static final String URL_SYNC_POST_CHECKIN  = "/CheckInToolHawkEquipment";


    // Urls Inventory
    public static final String URL_SYNC_POST_MOVE_INVENTORY  = "/MoveInventory";
    public static final String URL_SYNC_POST__ISSUE = "/IssueInventory";
    public static final String URL_SYNC_POST_RECEIVE = "/ReceiveInventory";
}