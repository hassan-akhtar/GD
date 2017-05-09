package com.ets.gd.Constants;

public class Constants {


    public static final int READ_TIMEOUT_SECONDS = 600;
    public static final int CONNECTION_TIMEOUT_SECONDS = 600;

    // Base URLs
    public static final String BASE_URL_UAT = "http://gd-uat.deliveredoncloud.com/";
    public static final String BASE_URL_STAGE = "http://192.168.0.131/Services/Services/Mobile/";

    public static final String URL_BASE = BASE_URL_UAT;

    //  Callback Keys
    public static final int RESPONSE_LOGIN = 0;
    public static final int RESPONSE_CUSTOMERS = 1;
    public static final int RESPONSE_SYNC_GET = 2;
    public static final int RESPONSE_SYNC_POST_EQUIPMENT = 3;
    public static final int RESPONSE_SYNC_POST_ADD_LOCATION = 4;
    public static final int RESPONSE_SYNC_POST_MOVE_TRANSFER = 5;
    public static final int RESPONSE_SYNC_POST_INSPECT_EQUIPMENT = 6;

    // Urls
    public static final String URL_LOGIN = "/Services/Services/Mobile/Login";
    public static final String URL_SYNC_GET= "/Services/Services/Mobile/GetCustomerData";
    public static final String URL_SYNC_POST_EQUIPMENT= "/Services/Services/Mobile/CreateEquipment";
    public static final String URL_SYNC_POST_ADD_LOCATION= "/Services/Services/Mobile/CreateFbLocations";
    public static final String URL_SYNC_POST_MOVE_TRANSFER= "/Services/Services/Mobile/MoveOrTransferEquipments";
    public static final String URL_SYNC_POST_INSPECT_EQUIPMENT= "/Services/Services/Mobile/InspectEquipments";










}
