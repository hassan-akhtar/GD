package com.ets.gd.Activities.Sync;


import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Constants.Constants;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.InventoryMoveCall;
import com.ets.gd.Models.MoveInventoryCall;
import com.ets.gd.NetworkLayer.RequestDTOs.AddLocation;
import com.ets.gd.NetworkLayer.RequestDTOs.CheckIn;
import com.ets.gd.NetworkLayer.RequestDTOs.CheckOut;
import com.ets.gd.NetworkLayer.RequestDTOs.ETSLoc;
import com.ets.gd.NetworkLayer.RequestDTOs.Equipment;
import com.ets.gd.NetworkLayer.RequestDTOs.EquipmentMaintenance;
import com.ets.gd.NetworkLayer.RequestDTOs.InspectionDates;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveInventoryRealm;
import com.ets.gd.NetworkLayer.RequestDTOs.QuickCount;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncCheckInRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncCheckOutRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddETSLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentMaintenanceDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostMoveInventoryRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostQuickCountRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostToolhawkEquipment;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostToolhawkMoveDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncToolhawkTransferDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.THEquipment;
import com.ets.gd.NetworkLayer.RequestDTOs.ToolhawkMove;
import com.ets.gd.NetworkLayer.RequestDTOs.ToolhawkMoveDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.ToolhawkTransferDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.TransferToolhawk;
import com.ets.gd.NetworkLayer.RequestDTOs.UnitinspectionResult;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransfer;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransferRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.Note;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncGetDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostUnitInspectionRequestDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyInspectionDates;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipmentResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.NetworkLayer.Service.GSDServiceFactory;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SyncFragment extends Fragment implements MyCallBack {

    View rootView;
    TextView tvLastSyncDate, tvLastSyncTime, tvSyncInProgress;
    SharedPreferencesManager sharedPreferencesManager;
    String lastSyncDate, lastSyncTime;
    SyncPostEquipmentRequestDTO syncPostEquipmentRequestDTO;
    SyncPostToolhawkEquipment syncPostTHEquipmentRequestDTO;
    String[] itemList;
    private List<SyncPostEquipment> lstSyncPostEquipmentResults = new ArrayList<SyncPostEquipment>();
    private List<Equipment> lstAddEquipment = new ArrayList<Equipment>();
    private List<Equipment> lstEditEquipment = new ArrayList<Equipment>();
    private List<FireBugEquipment> lstEquipmentsEdit = new ArrayList<FireBugEquipment>();
    private List<FireBugEquipment> lstEquipmentsAdd = new ArrayList<FireBugEquipment>();
    List<Locations> locationsList = new ArrayList<Locations>();
    List<AddLocation> lstAddLocation = new ArrayList<AddLocation>();
    SyncPostAddLocationRequestDTO syncPostAddLocationRequestDTO;
    boolean sendEquipmentCall = false, sendLocationcall = false, sendMovecall = false, sendUnitInspcall = false, sendToolHawkEquipmentCall = false, sendETSLocationCall = false;
    boolean sendTransferToolhawkCall = false, sendMoveToolhawkCall = false, sendQuickCountCall = false, sendMaintenanceCall = false, sendCheckInCall = false, sendCheckOutCall = false;
    boolean sendMoveInventoryCall = false, sendIssueInventoryCall = false, sendReceiveInventoryCall = false;
    MoveTransferRequestDTO moveTransferRequestDTO;
    List<MoveTransfer> lstMoveEquipment = new ArrayList<MoveTransfer>();
    List<FireBugEquipment> lstAllAssets = new ArrayList<FireBugEquipment>();
    SyncPostUnitInspectionRequestDTO syncPostUnitInspectionRequestDTO;
    List<UnitinspectionResult> lstInspectionResult;
    private List<ToolhawkEquipment> lstTHEquipmentsEdit = new ArrayList<ToolhawkEquipment>();
    private List<ToolhawkEquipment> lstTHEquipmentsAdd = new ArrayList<ToolhawkEquipment>();
    private List<THEquipment> lstAddTHEquipment = new ArrayList<THEquipment>();
    private List<THEquipment> lstEditTHEquipment = new ArrayList<THEquipment>();
    List<ETSLocation> etsLocationsList = new ArrayList<ETSLocation>();
    List<ETSLoc> lstAddETSLocation = new ArrayList<ETSLoc>();
    SyncPostAddETSLocationRequestDTO syncPostAddETSLocationRequestDTO;
    SyncToolhawkTransferDTO syncToolhawkTransferDTO;
    List<TransferToolhawk> lstTHTansfer = new ArrayList<TransferToolhawk>();
    List<ToolhawkTransferDTO> lstTHTansferDto = new ArrayList<ToolhawkTransferDTO>();
    List<ToolhawkMove> lstTHMoveDto = new ArrayList<ToolhawkMove>();
    List<ToolhawkMoveDTO> lstTHMove = new ArrayList<ToolhawkMoveDTO>();
    SyncPostToolhawkMoveDTO syncPostToolhawkMoveDTO;
    SyncPostQuickCountRequestDTO syncPostQuickCountRequestDTO;
    private List<QuickCount> lstQuickCount;
    SyncPostEquipmentMaintenanceDTO syncPostEquipmentMaintenanceDTO;
    List<EquipmentMaintenance> equipmentMaintenanceList;
    SyncCheckInRequestDTO syncCheckInRequestDTO;
    SyncCheckOutRequestDTO syncCheckOutRequestDTO;
    List<CheckIn> checkInList;
    private List<CheckOut> checkOutList;
    SyncPostMoveInventoryRequestDTO syncPostMoveInventoryRequestDTO;
    List<MoveInventoryRealm> lstmoveInventoryRealm = new ArrayList<MoveInventoryRealm>();
    List<MoveInventoryCall> lstmoveInventory = new ArrayList<MoveInventoryCall>();

    SyncPostMoveInventoryRequestDTO syncPostIssueInventoryRequestDTO;
    List<MoveInventoryRealm> lstIssueInventoryRealm = new ArrayList<MoveInventoryRealm>();
    List<MoveInventoryCall> lstIssueInventory = new ArrayList<MoveInventoryCall>();

    SyncPostMoveInventoryRequestDTO syncPostReceiveInventoryRequestDTO;
    List<MoveInventoryRealm> lstReceiveInventoryRealm = new ArrayList<MoveInventoryRealm>();
    List<MoveInventoryCall> lstReceiveInventory = new ArrayList<MoveInventoryCall>();

    public SyncFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sync, container, false);
        initViews();
        initObj();
        initListeners();
        return rootView;

    }

    private void initViews() {
        tvLastSyncDate = (TextView) rootView.findViewById(R.id.tvLastSyncDate);
        tvLastSyncTime = (TextView) rootView.findViewById(R.id.tvLastSyncTime);
        tvSyncInProgress = (TextView) rootView.findViewById(R.id.tvSyncInProgress);
    }

    private void initObj() {
        BaseActivity.currentFragment = new SyncFragment();
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());

        lastSyncDate = sharedPreferencesManager.getString(SharedPreferencesManager.LAST_SYNC_DATE);
        lastSyncTime = sharedPreferencesManager.getString(SharedPreferencesManager.LAST_SYNC_TIME);

        if ("Not Found".equals(lastSyncDate)) {
            tvLastSyncDate.setText("--/--/--");
        } else {
            tvLastSyncDate.setText(lastSyncDate);
        }

        if ("Not Found".equals(lastSyncTime)) {
            tvLastSyncTime.setText("--:--");
        } else {
            tvLastSyncTime.setText(lastSyncTime);
        }

        setupPostAddUpdateEquipmentData();
        setupPostAddedLocationsData();
        setupPostMoveAssetData();
        setupPostInspectAssetData();
        setupPostAddUpdateToolhawkEquipmentData();
        setupPostETSLocationData();
        setupPostTHTransferData();
        setupPostTHMoveData();
        setupQuickCountData();
        setupMaintenanceData();
        setupCheckInData();
        setupCheckOutData();
        setupMoveInventoryData();
        setupIssueInventoryData();
        setupReceiveInventoryData();

        if (sendEquipmentCall) {
            callSyncPostEqupmentService();
        } else if (sendLocationcall) {
            callSyncPostLocationService();
        } else if (sendMovecall) {
            callSyncPostMoveService();
        } else if (sendUnitInspcall) {
            callSyncPostUnitInspectService();
        } else if (sendToolHawkEquipmentCall) {
            callSyncPostToolhawkEqupmentService();
        } else if (sendETSLocationCall) {
            callSyncPostETSLocationService();
        } else if (sendTransferToolhawkCall) {
            callSyncPostTHTransferService();
        } else if (sendMoveToolhawkCall) {
            callSyncPostTHMoveService();
        } else if (sendQuickCountCall) {
            callSyncPostQuickCountService();
        } else if (sendMaintenanceCall) {
            callSyncPostMaintenanceService();
        } else if (sendCheckInCall) {
            callSyncPostCheckInService();
        } else if (sendCheckOutCall) {
            callSyncPostCheckOutService();
        } else if(sendMoveInventoryCall){
            callSyncPostMoveInventoryService();
        }else if(sendIssueInventoryCall){
            callSyncPostIssueInventoryService();
        }else if(sendReceiveInventoryCall){
            callSyncPostReceiveInventoryService();
        }else {
            // tvSyncInProgress.setText("No data found for syncing");
            // showToast("No data found for syncing");
            DataManager.getInstance().deleteRealm();
            callSyncGetService();
        }
    }

    private void setupCheckOutData() {

        checkOutList = DataManager.getInstance().getAllCheckOutList();

        syncCheckOutRequestDTO = new SyncCheckOutRequestDTO(Constants.RESPONSE_SYNC_POST_CHECKOUT, checkOutList);

        if (0 != checkOutList.size()) {
            sendCheckOutCall = true;
        }
    }

    private void setupMoveInventoryData() {

        lstmoveInventoryRealm = DataManager.getInstance().getAllMoveInventoryList();
        List<InventoryMoveCall> Materials = new ArrayList<InventoryMoveCall>();
        Materials.clear();
        for(int i=0; i<lstmoveInventoryRealm.size();i++){
            MoveInventoryCall moveInventoryCall = new MoveInventoryCall();
            moveInventoryCall.setEquipmentID(lstmoveInventoryRealm.get(i).getEquipmentID());
            moveInventoryCall.setLocationID(lstmoveInventoryRealm.get(i).getLocationID());
            moveInventoryCall.setJobNumberID(lstmoveInventoryRealm.get(i).getJobNumberID());
            moveInventoryCall.setMoveType(lstmoveInventoryRealm.get(i).getMoveType());
            moveInventoryCall.setUserID(lstmoveInventoryRealm.get(i).getUserID());

            for(int j=0; j<lstmoveInventoryRealm.get(i).getMaterials().size();j++){
                InventoryMoveCall inventoryMoveCall = new InventoryMoveCall();
                inventoryMoveCall.setCode(lstmoveInventoryRealm.get(i).getMaterials().get(j).getCode());
                inventoryMoveCall.setQuantity(lstmoveInventoryRealm.get(i).getMaterials().get(j).getQuantity());
                inventoryMoveCall.setInventoryID(lstmoveInventoryRealm.get(i).getMaterials().get(j).getInventoryID());
                inventoryMoveCall.setMaterialID(lstmoveInventoryRealm.get(i).getMaterials().get(j).getMaterialID());
                Materials.add(inventoryMoveCall);
            }
            moveInventoryCall.setMaterials(Materials);
            lstmoveInventory.add(moveInventoryCall);
        }


        syncPostMoveInventoryRequestDTO = new SyncPostMoveInventoryRequestDTO(Constants.RESPONSE_SYNC_POST_MOVE_INVENTORY, lstmoveInventory);

        if (0 != lstmoveInventory.size()) {
            sendMoveInventoryCall = true;
        }
    }


    private void setupIssueInventoryData() {

        lstIssueInventoryRealm = DataManager.getInstance().getAllIssueInventoryList();
        List<InventoryMoveCall> Materials = new ArrayList<InventoryMoveCall>();
        Materials.clear();
        for(int i=0; i<lstIssueInventoryRealm.size();i++){
            MoveInventoryCall moveInventoryCall = new MoveInventoryCall();
            moveInventoryCall.setEquipmentID(lstIssueInventoryRealm.get(i).getEquipmentID());
            moveInventoryCall.setJobNumberID(lstIssueInventoryRealm.get(i).getJobNumberID());
            moveInventoryCall.setIssueType(lstIssueInventoryRealm.get(i).getIssueType());
            moveInventoryCall.setUserID(lstIssueInventoryRealm.get(i).getUserID());

            for(int j=0; j<lstIssueInventoryRealm.get(i).getMaterials().size();j++){
                InventoryMoveCall inventoryMoveCall = new InventoryMoveCall();
                inventoryMoveCall.setCode(lstIssueInventoryRealm.get(i).getMaterials().get(j).getCode());
                inventoryMoveCall.setQuantity(lstIssueInventoryRealm.get(i).getMaterials().get(j).getQuantity());
                inventoryMoveCall.setInventoryID(lstIssueInventoryRealm.get(i).getMaterials().get(j).getInventoryID());
                inventoryMoveCall.setMaterialID(lstIssueInventoryRealm.get(i).getMaterials().get(j).getMaterialID());
                Materials.add(inventoryMoveCall);
            }
            moveInventoryCall.setMaterials(Materials);
            lstIssueInventory.add(moveInventoryCall);
        }


        syncPostIssueInventoryRequestDTO = new SyncPostMoveInventoryRequestDTO(Constants.RESPONSE_SYNC_POST_ISSUE, lstIssueInventory);

        if (0 != lstIssueInventory.size()) {
            sendIssueInventoryCall = true;
        }
    }


    private void setupReceiveInventoryData() {

        lstReceiveInventoryRealm = DataManager.getInstance().getAllReceiveInventoryList();
        List<InventoryMoveCall> Materials = new ArrayList<InventoryMoveCall>();
        Materials.clear();
        for(int i=0; i<lstReceiveInventoryRealm.size();i++){
            MoveInventoryCall moveInventoryCall = new MoveInventoryCall();
            moveInventoryCall.setLocationID(lstReceiveInventoryRealm.get(i).getLocationID());
            moveInventoryCall.setJobNumberID(lstReceiveInventoryRealm.get(i).getJobNumberID());
            moveInventoryCall.setUserID(lstReceiveInventoryRealm.get(i).getUserID());

            for(int j=0; j<lstReceiveInventoryRealm.get(i).getMaterials().size();j++){
                InventoryMoveCall inventoryMoveCall = new InventoryMoveCall();
                inventoryMoveCall.setCode(lstReceiveInventoryRealm.get(i).getMaterials().get(j).getCode());
                inventoryMoveCall.setQuantity(lstReceiveInventoryRealm.get(i).getMaterials().get(j).getQuantity());
                inventoryMoveCall.setInventoryID(lstReceiveInventoryRealm.get(i).getMaterials().get(j).getInventoryID());
                inventoryMoveCall.setMaterialID(lstReceiveInventoryRealm.get(i).getMaterials().get(j).getMaterialID());
                Materials.add(inventoryMoveCall);
            }
            moveInventoryCall.setMaterials(Materials);
            lstReceiveInventory.add(moveInventoryCall);
        }


        syncPostReceiveInventoryRequestDTO = new SyncPostMoveInventoryRequestDTO(Constants.RESPONSE_SYNC_POST_RECEIVE, lstReceiveInventory);

        if (0 != lstReceiveInventory.size()) {
            sendReceiveInventoryCall = true;
        }
    }
    private void setupCheckInData() {

        checkInList = DataManager.getInstance().getAllCheckInList();

        syncCheckInRequestDTO = new SyncCheckInRequestDTO(Constants.RESPONSE_SYNC_POST_CHECKIN, checkInList);

        if (0 != checkInList.size()) {
            sendCheckInCall = true;
        }
    }

    private void setupQuickCountData() {

        lstQuickCount = DataManager.getInstance().getAllChangesQuickCountAssetList();

        syncPostQuickCountRequestDTO = new SyncPostQuickCountRequestDTO(Constants.RESPONSE_SYNC_POST_QUICK_COUNT,
                lstQuickCount);

        if (0 != lstQuickCount.size()) {
            sendQuickCountCall = true;
        }
    }

    private void setupMaintenanceData() {

        equipmentMaintenanceList = DataManager.getInstance().getAllEquipmentMaintenanceList();

        syncPostEquipmentMaintenanceDTO = new SyncPostEquipmentMaintenanceDTO(Constants.RESPONSE_SYNC_POST_MAINTENANCE,
                equipmentMaintenanceList);

        if (0 != equipmentMaintenanceList.size()) {
            sendMaintenanceCall = true;
        }
    }

    private void callSyncPostLocationService() {

        CommonActions.showProgressDialog(getActivity());
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncLocation(
                syncPostAddLocationRequestDTO, this
        );


    }


    private void callSyncPostETSLocationService() {
        CommonActions.showProgressDialog(getActivity());
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncETSLocation(
                syncPostAddETSLocationRequestDTO, this
        );

    }


    private void callSyncPostUnitInspectService() {

        CommonActions.showProgressDialog(getActivity());
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postInspectEquipment(
                syncPostUnitInspectionRequestDTO, this
        );


    }


    void callSyncPostEqupmentService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncEquipment(
                syncPostEquipmentRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostTHTransferService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncToolhawkTransfer(
                syncToolhawkTransferDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostTHMoveService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncToolhawkMove(
                syncPostToolhawkMoveDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostQuickCountService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncQuickCount(
                syncPostQuickCountRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostMaintenanceService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncMaintenace(
                syncPostEquipmentMaintenanceDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostCheckInService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncCheckIn(
                syncCheckInRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostCheckOutService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncCheckOut(
                syncCheckOutRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostMoveInventoryService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncMoveInventory(
                syncPostMoveInventoryRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    void callSyncPostIssueInventoryService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncIssueInventory(
                syncPostIssueInventoryRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }

    void callSyncPostReceiveInventoryService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncReceiveInventory(
                syncPostReceiveInventoryRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }




    void callSyncPostToolhawkEqupmentService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncToolhawkEquipment(
                syncPostTHEquipmentRequestDTO, this
        );
        //} else {
        //     tvSyncInProgress.setText("No data found for syncing");
        //      showToast("No data found for syncing");
        //  }
    }


    private void initListeners() {
    }

    void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    void setCurrentDateAndTime() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());
        tvLastSyncDate.setText("" + formattedDate);
        String delegate = "hh:mm A";
        tvLastSyncTime.setText("" + DateFormat.format(delegate, Calendar.getInstance().getTime()));
        sharedPreferencesManager.setString(SharedPreferencesManager.LAST_SYNC_DATE, formattedDate);
        sharedPreferencesManager.setString(SharedPreferencesManager.LAST_SYNC_TIME,
                DateFormat.format(delegate, Calendar.getInstance().getTime()).toString());

    }


    private void callSyncGetService() {
        tvSyncInProgress.setText("Sync in progress...");
        CommonActions.showProgressDialog(getActivity());
        GSDServiceFactory.getService(getActivity()).getSyncData(new SyncGetDTO(Constants.RESPONSE_SYNC_GET, Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID)), this);
    }


    private void setupPostMoveAssetData() {
        lstAllAssets = DataManager.getInstance().getAllAssets();

        if (null != lstAllAssets) {
            for (FireBugEquipment asset : lstAllAssets) {
                if (asset.isMoved()) {
                    MoveTransfer equipment = new MoveTransfer();
                    equipment.setAction("Move");
                    equipment.setCustomerID(asset.getCustomer().getID());
                    equipment.setEquipmentID(asset.getID());
                    equipment.setLocationID(asset.getLocation().getID());
                    lstMoveEquipment.add(equipment);
                }
            }

            for (FireBugEquipment asset : lstAllAssets) {
                if (asset.isTransferred()) {
                    MoveTransfer equipment = new MoveTransfer();
                    equipment.setAction("Transfer");
                    equipment.setCustomerID(asset.getCustomer().getID());
                    equipment.setEquipmentID(asset.getID());
                    equipment.setLocationID(asset.getLocation().getID());
                    lstMoveEquipment.add(equipment);
                }
            }
        }


        moveTransferRequestDTO = new MoveTransferRequestDTO(Constants.RESPONSE_SYNC_POST_MOVE_TRANSFER,
                String.valueOf(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                lstMoveEquipment);

        if (0 != lstMoveEquipment.size()) {
            sendMovecall = true;
        }
    }


    private void setupPostInspectAssetData() {
        lstInspectionResult = DataManager.getInstance().getAllUnitInspectedAssets();

        syncPostUnitInspectionRequestDTO = new SyncPostUnitInspectionRequestDTO(Constants.RESPONSE_SYNC_POST_INSPECT_EQUIPMENT,
                String.valueOf(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                lstInspectionResult);

        if (0 != lstInspectionResult.size()) {
            sendUnitInspcall = true;
        }
    }


    private void setupPostTHTransferData() {
        lstTHTansferDto = DataManager.getInstance().getAllToolhawkTransferDTO();


        for (ToolhawkTransferDTO dto : lstTHTansferDto) {

            TransferToolhawk transferToolhawk = new TransferToolhawk();
            transferToolhawk.setEquipmentID(dto.getEquipmentID());
            transferToolhawk.setDepartmentID(dto.getDepartmentID());
            transferToolhawk.setLocationID(dto.getLocationID());
            lstTHTansfer.add(transferToolhawk);
        }

        syncToolhawkTransferDTO = new SyncToolhawkTransferDTO(Constants.RESPONSE_SYNC_POST_TOOLHAWK_TRANSFER,
                lstTHTansfer);

        if (0 != lstTHTansfer.size()) {
            sendTransferToolhawkCall = true;
        }
    }


    private void setupPostTHMoveData() {
        lstTHMoveDto = DataManager.getInstance().getAllToolhawkMoveDTO();


        for (ToolhawkMove dto : lstTHMoveDto) {

            ToolhawkMoveDTO moveDTO = new ToolhawkMoveDTO();
            moveDTO.setEquipmentID(dto.getEquipmentID());
            moveDTO.setMoveType(dto.getMoveType());
            moveDTO.setToEquipmentID(dto.getToEquipmentID());
            moveDTO.setToLocationID(dto.getToLocationID());
            moveDTO.setToJobNumberID(dto.getToJobNumberID());
            moveDTO.setConditionID(dto.getConditionID());
            moveDTO.setUserID(dto.getUserID());
            lstTHMove.add(moveDTO);
        }

        syncPostToolhawkMoveDTO = new SyncPostToolhawkMoveDTO(Constants.RESPONSE_SYNC_POST_TOOLHAWK_MOVE,
                lstTHMove);

        if (0 != lstTHMove.size()) {
            sendMoveToolhawkCall = true;
        }
    }


    private void setupPostAddedLocationsData() {
        locationsList = DataManager.getInstance().getAllAddedLocations();

        if (null != locationsList) {
            for (Locations location : locationsList) {
                AddLocation addLocation = new AddLocation();
                addLocation.setCode(location.getCode());
                addLocation.setDescription(location.getDescription());
                addLocation.setCustomer(location.getCustomer().getID());
                addLocation.setSite(location.getSite().getID());
                if (null!=location.getBuilding()) {
                    addLocation.setBuilding(location.getBuilding().getID());
                }
                lstAddLocation.add(addLocation);
            }
        }


        syncPostAddLocationRequestDTO = new SyncPostAddLocationRequestDTO(Constants.RESPONSE_SYNC_POST_ADD_LOCATION,
                String.valueOf(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                lstAddLocation);

        if (0 != lstAddLocation.size()) {
            sendLocationcall = true;
        }

    }

    void setupPostETSLocationData() {
        etsLocationsList = DataManager.getInstance().getAllAddedETSLocations();

        if (null != etsLocationsList) {
            for (ETSLocation location : etsLocationsList) {
                ETSLoc addLocation = new ETSLoc();
                addLocation.setCode(location.getCode());
                addLocation.setDescription(location.getDescription());
                addLocation.setCustomerID(location.getCustomerID());
                addLocation.setSiteID(location.getSiteID());
                addLocation.setBuildingID(location.getBuildingID());
                lstAddETSLocation.add(addLocation);
            }
        }


        syncPostAddETSLocationRequestDTO = new SyncPostAddETSLocationRequestDTO(Constants.RESPONSE_SYNC_POST_ETS_LOCATION,
                String.valueOf(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                lstAddETSLocation);

        if (0 != lstAddETSLocation.size()) {
            sendETSLocationCall = true;
        }
    }


    void setupPostAddUpdateToolhawkEquipmentData() {
        lstTHEquipmentsEdit = DataManager.getInstance().getAllUpdateToolhawkAssets();
        if (null != lstTHEquipmentsEdit) {
            for (ToolhawkEquipment toolhawkEquipment : lstTHEquipmentsEdit) {
                THEquipment equipment = new THEquipment();
                equipment.setID(toolhawkEquipment.getID());
                equipment.setCode(toolhawkEquipment.getCode());
                equipment.setDepartmentID(toolhawkEquipment.getDepartment().getID());
                equipment.setManufacturerID(toolhawkEquipment.getManufacturer().getID());
                equipment.setModelID(toolhawkEquipment.getModel().getID());
                if (null != toolhawkEquipment.getETSLocation()) {
                    equipment.setLocationID(toolhawkEquipment.getETSLocation().getID());
                }
                try {
                    equipment.setUnitCost(Float.valueOf(toolhawkEquipment.getUnitCost()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                lstEditTHEquipment.add(equipment);
            }
        }

        lstTHEquipmentsAdd = DataManager.getInstance().getAllAddToolhawkAssets();
        if (null != lstTHEquipmentsAdd) {
            for (ToolhawkEquipment toolhawkEquipment : lstTHEquipmentsAdd) {
                THEquipment equipment = new THEquipment();
                equipment.setID(0);
                equipment.setCode(toolhawkEquipment.getCode());
                equipment.setDepartmentID(toolhawkEquipment.getDepartment().getID());
                equipment.setManufacturerID(toolhawkEquipment.getManufacturer().getID());
                equipment.setModelID(toolhawkEquipment.getModel().getID());
                if (null != toolhawkEquipment.getETSLocation()) {
                    equipment.setLocationID(toolhawkEquipment.getETSLocation().getID());
                }
                try {
                    equipment.setUnitCost(Float.valueOf(toolhawkEquipment.getUnitCost()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                lstAddTHEquipment.add(equipment);
            }
        }


        syncPostTHEquipmentRequestDTO = new SyncPostToolhawkEquipment(Constants.RESPONSE_SYNC_POST_TOOLHAWK_EQUIPMENT,
                String.valueOf(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                lstAddTHEquipment,
                lstEditTHEquipment);

        if (0 != lstEditTHEquipment.size() || 0 != lstAddTHEquipment.size()) {
            sendToolHawkEquipmentCall = true;
        }


    }

    void setupPostAddUpdateEquipmentData() {
        lstEquipmentsEdit = DataManager.getInstance().getAllUpdateAssets();
        if (null != lstEquipmentsEdit) {
            for (FireBugEquipment fireBugEquipment : lstEquipmentsEdit) {
                Equipment equipment = new Equipment();
                equipment.setID(fireBugEquipment.getID());
                equipment.setCode(fireBugEquipment.getCode());
                equipment.setDeviceTypeID(fireBugEquipment.getDeviceType().getID());
                equipment.setManufacturerID(fireBugEquipment.getManufacturer().getID());
                equipment.setModelID(fireBugEquipment.getModel().getID());
                equipment.setSerialNo(fireBugEquipment.getSerialNo());
                equipment.setManufacturerDate(fireBugEquipment.getManufacturerDate());
                if (null != fireBugEquipment.getVendorCode()) {
                    equipment.setVendorID(fireBugEquipment.getVendorCode().getID());
                }
                if (null != fireBugEquipment.getAgentType()) {
                    equipment.setAgentID(fireBugEquipment.getAgentType().getID());
                }

                if (null != fireBugEquipment.getLocation()) {
                    equipment.setAssignedLocation(fireBugEquipment.getLocation().getID());
                } else {
                }
                equipment.setCustomerID(fireBugEquipment.getCustomer().getID());
                List<InspectionDates> InspectionDates = new ArrayList<InspectionDates>();

                List<MyInspectionDates> InspectionDatess = DataManager.getInstance().getEquipmentInspectionDates(fireBugEquipment.getID());
                for (int j = 0; j < InspectionDatess.size(); j++) {
                    InspectionDates inspectionDates = new InspectionDates();
                    inspectionDates.setEquipmentID(InspectionDatess.get(j).getEquipmentID());
                    inspectionDates.setDueDate(InspectionDatess.get(j).getDueDate());
                    inspectionDates.setInspectionType(InspectionDatess.get(j).getInspectionType());
                    InspectionDates.add(inspectionDates);
                }
                equipment.setInspectionDates(InspectionDates);

                List<Note> lstFbEquipmentNotes = new ArrayList<Note>();
                List<EquipmentNote> lstNotes = DataManager.getInstance().getAllNotesByCode(fireBugEquipment.getCode());
                for (int k = 0; k < lstNotes.size(); k++) {
                    Note note = new Note();
                    note.setModifiedBy(lstNotes.get(k).getModifiedBy());
                    note.setNote(lstNotes.get(k).getNote());
                    note.setModifiedTime(lstNotes.get(k).getModifiedTime());
                    lstFbEquipmentNotes.add(note);
                }


                equipment.setLstFbEquipmentNotes(lstFbEquipmentNotes);
                lstEditEquipment.add(equipment);
            }
        }

        lstEquipmentsAdd = DataManager.getInstance().getAllAddAssets();
        if (null != lstEquipmentsAdd) {

            for (FireBugEquipment fireBugEquipment : lstEquipmentsAdd) {
                Equipment equipment = new Equipment();
                equipment.setID(0);
                equipment.setCode(fireBugEquipment.getCode());
                equipment.setDeviceTypeID(fireBugEquipment.getDeviceType().getID());
                equipment.setManufacturerID(fireBugEquipment.getManufacturer().getID());
                equipment.setModelID(fireBugEquipment.getModel().getID());
                equipment.setSerialNo(fireBugEquipment.getSerialNo());
                equipment.setManufacturerDate(fireBugEquipment.getManufacturerDate());

                if (null != fireBugEquipment.getVendorCode()) {
                    equipment.setVendorID(fireBugEquipment.getVendorCode().getID());
                }
                if (null != fireBugEquipment.getAgentType()) {
                    equipment.setAgentID(fireBugEquipment.getAgentType().getID());
                }

                // if (null==fireBugEquipment.getLocation().getID()) {
                equipment.setAssignedLocation(fireBugEquipment.getLocation().getID());
                //  }
                equipment.setCustomerID(fireBugEquipment.getCustomer().getID());
                List<InspectionDates> InspectionDates = new ArrayList<InspectionDates>();

                List<MyInspectionDates> InspectionDatess = DataManager.getInstance().getEquipmentInspectionDates(fireBugEquipment.getID());
                for (int j = 0; j < InspectionDatess.size(); j++) {
                    InspectionDates inspectionDates = new InspectionDates();
                    inspectionDates.setEquipmentID(InspectionDatess.get(j).getEquipmentID());
                    inspectionDates.setDueDate(InspectionDatess.get(j).getDueDate());
                    inspectionDates.setInspectionType(InspectionDatess.get(j).getInspectionType());
                    InspectionDates.add(inspectionDates);
                }
                equipment.setInspectionDates(InspectionDates);

                List<Note> lstFbEquipmentNotes = new ArrayList<Note>();
                List<EquipmentNote> lstNotes = DataManager.getInstance().getAllNotesByCode(fireBugEquipment.getCode());
                for (int k = 0; k < lstNotes.size(); k++) {
                    Note note = new Note();
                    note.setModifiedBy(lstNotes.get(k).getModifiedBy());
                    note.setNote(lstNotes.get(k).getNote());
                    note.setModifiedTime(lstNotes.get(k).getModifiedTime());
                    lstFbEquipmentNotes.add(note);
                }


                equipment.setLstFbEquipmentNotes(lstFbEquipmentNotes);
                lstAddEquipment.add(equipment);
            }
        }


        syncPostEquipmentRequestDTO = new SyncPostEquipmentRequestDTO(Constants.RESPONSE_SYNC_POST_EQUIPMENT,
                String.valueOf(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                lstAddEquipment,
                lstEditEquipment);

        if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
            sendEquipmentCall = true;
        }
    }


    void showSyncResults() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, itemList);


        LayoutInflater li = LayoutInflater.from(getActivity());
        View dialogView = li.inflate(R.layout.assets_view_all, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder.setTitle("Sync Results");
        alertDialogBuilder.setView(dialogView);
        final ListView listAssets = (ListView) dialogView
                .findViewById(R.id.lvAssets);
        listAssets.setAdapter(adapter);
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onSuccess(ResponseDTO responseDTO) {
        switch (responseDTO.getCallBackId()) {

            case Constants.RESPONSE_SYNC_POST_EQUIPMENT: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                   // lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                    //Toast.makeText(getActivity(), "Sync Post Complete", Toast.LENGTH_LONG).show();

                    if (sendLocationcall) {
                        callSyncPostLocationService();
                    } else if (sendMovecall) {
                        callSyncPostMoveService();
                    } else if (sendUnitInspcall) {
                        callSyncPostUnitInspectService();
                    } else if (sendToolHawkEquipmentCall) {
                        callSyncPostToolhawkEqupmentService();
                    } else if (sendETSLocationCall) {
                        callSyncPostETSLocationService();
                    } else if (sendTransferToolhawkCall) {
                        callSyncPostTHTransferService();
                    } else if (sendMoveToolhawkCall) {
                        callSyncPostTHMoveService();
                    } else if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_ADD_LOCATION: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                  //  lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if (sendMovecall) {
                        callSyncPostMoveService();
                    } else if (sendUnitInspcall) {
                        callSyncPostUnitInspectService();
                    } else if (sendToolHawkEquipmentCall) {
                        callSyncPostToolhawkEqupmentService();
                    } else if (sendETSLocationCall) {
                        callSyncPostETSLocationService();
                    } else if (sendTransferToolhawkCall) {
                        callSyncPostTHTransferService();
                    } else if (sendMoveToolhawkCall) {
                        callSyncPostTHMoveService();
                    } else if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_MOVE_TRANSFER: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                 //   lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if (sendUnitInspcall) {
                        callSyncPostUnitInspectService();
                    } else if (sendToolHawkEquipmentCall) {
                        callSyncPostToolhawkEqupmentService();
                    } else if (sendETSLocationCall) {
                        callSyncPostETSLocationService();
                    } else if (sendTransferToolhawkCall) {
                        callSyncPostTHTransferService();
                    } else if (sendMoveToolhawkCall) {
                        callSyncPostTHMoveService();
                    } else if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;

            case Constants.RESPONSE_SYNC_POST_INSPECT_EQUIPMENT: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                  //  lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if (sendToolHawkEquipmentCall) {
                        callSyncPostToolhawkEqupmentService();
                    } else if (sendETSLocationCall) {
                        callSyncPostETSLocationService();
                    } else if (sendTransferToolhawkCall) {
                        callSyncPostTHTransferService();
                    } else if (sendMoveToolhawkCall) {
                        callSyncPostTHMoveService();
                    } else if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_TOOLHAWK_EQUIPMENT: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                  //  lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                    if (sendETSLocationCall) {
                        callSyncPostETSLocationService();
                    } else if (sendTransferToolhawkCall) {
                        callSyncPostTHTransferService();
                    } else if (sendMoveToolhawkCall) {
                        callSyncPostTHMoveService();
                    } else if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }


                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;

            case Constants.RESPONSE_SYNC_POST_ETS_LOCATION: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                 //   lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if (sendTransferToolhawkCall) {
                        callSyncPostTHTransferService();
                    } else if (sendMoveToolhawkCall) {
                        callSyncPostTHMoveService();
                    } else if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }


                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_TOOLHAWK_TRANSFER: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                  //  lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                    if (sendMoveToolhawkCall) {
                        callSyncPostTHMoveService();
                    } else if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }


                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_TOOLHAWK_MOVE: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                 //   lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                    if (sendQuickCountCall) {
                        callSyncPostQuickCountService();
                    } else if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }
                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_QUICK_COUNT: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                  //  lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if (sendMaintenanceCall) {
                        callSyncPostMaintenanceService();
                    } else if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }
                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;

            case Constants.RESPONSE_SYNC_POST_MAINTENANCE: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                 //   lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());


                    if (sendCheckInCall) {
                        callSyncPostCheckInService();
                    } else if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_CHECKIN: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                //    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());


                    if (sendCheckOutCall) {
                        callSyncPostCheckOutService();
                    } else if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;


            case Constants.RESPONSE_SYNC_POST_CHECKOUT: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                //    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if(sendMoveInventoryCall){
                        callSyncPostMoveInventoryService();
                    }else if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }


                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;



            case Constants.RESPONSE_SYNC_POST_MOVE_INVENTORY: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                //    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                    if(sendIssueInventoryCall){
                        callSyncPostIssueInventoryService();
                    }else if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;

            case Constants.RESPONSE_SYNC_POST_ISSUE: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                 //   lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                    if(sendReceiveInventoryCall){
                        callSyncPostReceiveInventoryService();
                    }else {
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();
                    }

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;



            case Constants.RESPONSE_SYNC_POST_RECEIVE: {
                SyncPostEquipmentResponseDTO syncPostEquipmentResponseDTO = (SyncPostEquipmentResponseDTO) responseDTO;
                if (null != syncPostEquipmentResponseDTO) {
                    CommonActions.DismissesDialog();
                //    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                        DataManager.getInstance().deleteRealm();
                        callSyncGetService();

                } else {
                    CommonActions.DismissesDialog();
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.txt_login)
                            .setMessage("Something went wrong!")
                            .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
            break;

            case Constants.RESPONSE_SYNC_GET:
                SyncGetResponseDTO syncGetResponseDTO = (SyncGetResponseDTO) responseDTO;
                if (responseDTO != null) {
                    if (null != syncGetResponseDTO && null==responseDTO.getErrorMsg()) {

                        tvSyncInProgress.setText("Sync Complete!");
                        // Toast.makeText(getActivity(), "Sync Get Complete!", Toast.LENGTH_LONG).show();

                        itemList = new String[lstSyncPostEquipmentResults.size() + 1];

                        for (int i = 0; i < lstSyncPostEquipmentResults.size(); i++) {
                            String status = "";
                            if (null != lstSyncPostEquipmentResults.get(i).getStatus() && lstSyncPostEquipmentResults.get(i).getStatus().toLowerCase().startsWith("f")) {
                                status = "FAILURE";
                            } else {
                                status = "SUCCESS";
                            }
                            itemList[i] = lstSyncPostEquipmentResults.get(i).getCode() + ", " + lstSyncPostEquipmentResults.get(i).getOperation() + ", " + status;
                        }

                        itemList[itemList.length - 1] = "Update Db SUCCESS";

                        CommonActions.DismissesDialog();
                        DataManager.getInstance().saveSyncGetResponse(syncGetResponseDTO);
                        //  sharedPreferencesManager.setInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID, syncGetResponseDTO.getCustomerId());
                        setCurrentDateAndTime();
                        showSyncResults();
                    } else {
                        CommonActions.DismissesDialog();
                        itemList = new String[lstSyncPostEquipmentResults.size() + 1];

                        for (int i = 0; i < lstSyncPostEquipmentResults.size(); i++) {
                            String status = "";
                            if (lstSyncPostEquipmentResults.get(i).getStatus().toLowerCase().startsWith("f")) {
                                status = "FAILURE";
                            } else {
                                status = "SUCCESS";
                            }
                            itemList[i] = lstSyncPostEquipmentResults.get(i).getCode() + ", " + lstSyncPostEquipmentResults.get(i).getOperation() + ", " + status;
                        }

                        itemList[itemList.length - 1] = ""+responseDTO.getErrorMsg();
                        showSyncResults();
                    }
                }
                break;

            default:
                break;
        }

    }

    private void callSyncPostMoveService() {
        CommonActions.showProgressDialog(getActivity());
        tvSyncInProgress.setText("Sync in progress...");
        GSDServiceFactory.getService(getActivity()).postMoveTransfer(
                moveTransferRequestDTO, this
        );
    }


    @Override
    public void onFailure(ResponseDTO errorDTO) {
        CommonActions.DismissesDialog();
        if (404 == errorDTO.getCode())
            Toast.makeText(getActivity(), R.string.error_404_msg, Toast.LENGTH_LONG).show();
        else if (1 == errorDTO.getCode())
            Toast.makeText(getActivity(), R.string.error_poor_con, Toast.LENGTH_LONG).show();
        else if (400 == errorDTO.getCode()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.txt_login)
                    .setMessage(R.string.msg_login_failed)
                    .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else if (500 == errorDTO.getCode())
            Toast.makeText(getActivity(), R.string.error_404_msg, Toast.LENGTH_LONG).show();

        else
            Toast.makeText(getActivity(), R.string.error_con_timeout, Toast.LENGTH_LONG).show();
    }


}
