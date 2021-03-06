package com.ets.gd.Constants;

public class Constants {


    public static final int READ_TIMEOUT_SECONDS = 600;
    public static final int CONNECTION_TIMEOUT_SECONDS = 600;

    // Base URLs
    public static final String BASE_URL_UAT = "http://gd-uat.deliveredoncloud.com//Services/Services/Mobile";
    public static final String BASE_URL_STAGE = "http://192.168.0.131/Services/Services/Mobile/";

    public static final String URL_BASE = BASE_URL_UAT;


    public static final String URL_GENERAL_DATA = "https://www.general-data.com";

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
    public static final int RESPONSE_SYNC_POST_CHANGE_ROUTE_STATUS = 18;


    // Urls Firebug
    public static final String URL_LOGIN = "/Services/Services/Mobile/Login";
    public static final String URL_SYNC_GET = "/Services/Services/Mobile/GetCustomerData";
    public static final String URL_SYNC_POST_EQUIPMENT = "/Services/Services/Mobile/CreateEquipment";
    public static final String URL_SYNC_POST_ADD_LOCATION = "/Services/Services/Mobile/CreateFbLocations";
    public static final String URL_SYNC_POST_MOVE_TRANSFER = "/Services/Services/Mobile/MoveOrTransferEquipments";
    public static final String URL_SYNC_POST_INSPECT_EQUIPMENT = "/Services/Services/Mobile/InspectEquipments";
    public static final String URL_SYNC_POST_CHANGE_ROUTE_STATUS = "/Services/Services/Mobile/ChangeRouteStatus";


    // Urls Toolhawk
    public static final String URL_SYNC_POST_TOOLHAWK_EQUIPMENT = "/Services/Services/Mobile/CreateToolHawkEquipment";
    public static final String URL_SYNC_POST_ETS_LOCATION = "/Services/Services/Mobile/CreateETSLocation";
    public static final String URL_SYNC_POST_TOOLHAWK_TRANSFER = "/Services/Services/Mobile/TransferToolHawkEquipment";
    public static final String URL_SYNC_POST_TOOLHAWK_MOVE = "/Services/Services/Mobile/MoveToolHawkEquipment";
    public static final String URL_SYNC_POST_QUICK_COUNT = "/Services/Services/Mobile/QuickCount";
    public static final String URL_SYNC_POST_MAINTENANCE = "/Services/Services/Mobile/ToolHawkEquipmentMaintenance";
    public static final String URL_SYNC_POST_CHECKOUT = "/Services/Services/Mobile/CheckOutToolHawkEquipment";
    public static final String URL_SYNC_POST_CHECKIN = "/Services/Services/Mobile/CheckInToolHawkEquipment";


    // Urls Inventory
    public static final String URL_SYNC_POST_MOVE_INVENTORY = "/Services/Services/Mobile/MoveInventory";
    public static final String URL_SYNC_POST_ISSUE = "/Services/Services/Mobile/IssueInventory";
    public static final String URL_SYNC_POST_RECEIVE = "/Services/Services/Mobile/ReceiveInventory";
}