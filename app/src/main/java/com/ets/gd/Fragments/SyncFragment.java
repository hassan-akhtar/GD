package com.ets.gd.Fragments;


import android.content.DialogInterface;
import android.nfc.FormatException;
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
import com.ets.gd.NetworkLayer.RequestDTOs.AddLocation;
import com.ets.gd.NetworkLayer.RequestDTOs.Equipment;
import com.ets.gd.NetworkLayer.RequestDTOs.InspectionDates;
import com.ets.gd.NetworkLayer.RequestDTOs.UnitinspectionResult;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransfer;
import com.ets.gd.NetworkLayer.RequestDTOs.MoveTransferRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.Note;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncGetDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostAddLocationRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostEquipmentRequestDTO;
import com.ets.gd.NetworkLayer.RequestDTOs.SyncPostUnitInspectionRequestDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyInspectionDates;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncPostEquipmentResponseDTO;
import com.ets.gd.NetworkLayer.Service.GSDServiceFactory;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    String[] itemList;
    private List<SyncPostEquipment> lstSyncPostEquipmentResults = new ArrayList<SyncPostEquipment>();
    private List<Equipment> lstAddEquipment = new ArrayList<Equipment>();
    private List<Equipment> lstEditEquipment = new ArrayList<Equipment>();
    private List<FireBugEquipment> lstEquipmentsEdit = new ArrayList<FireBugEquipment>();
    private List<FireBugEquipment> lstEquipmentsAdd = new ArrayList<FireBugEquipment>();
    List<Locations> locationsList = new ArrayList<Locations>();
    List<AddLocation> lstAddLocation = new ArrayList<AddLocation>();
    SyncPostAddLocationRequestDTO syncPostAddLocationRequestDTO;
    boolean sendEquipmentCall = false, sendLocationcall = false, sendMovecall = false, sendUnitInspcall = false;
    MoveTransferRequestDTO moveTransferRequestDTO;
    List<MoveTransfer> lstMoveEquipment = new ArrayList<MoveTransfer>();
    List<FireBugEquipment> lstAllAssets = new ArrayList<FireBugEquipment>();
    SyncPostUnitInspectionRequestDTO syncPostUnitInspectionRequestDTO;
    List<UnitinspectionResult> lstInspectionResult;


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


        if (sendEquipmentCall) {
            callSyncPostEqupmentService();
        } else if (sendLocationcall) {
            callSyncPostLocationService();
        } else if (sendMovecall) {
            callSyncPostMoveService();
        } else if (sendUnitInspcall) {
            callSyncPostUnitInspectService();
        } else {
            tvSyncInProgress.setText("No data found for syncing");
            showToast("No data found for syncing");

        }
    }

    private void callSyncPostLocationService() {

        CommonActions.showProgressDialog(getActivity());
        tvSyncInProgress.setText("Sync post in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncLocation(
                syncPostAddLocationRequestDTO, this
        );


    }


    private void callSyncPostUnitInspectService() {

        CommonActions.showProgressDialog(getActivity());
        tvSyncInProgress.setText("Sync post in progress...");
        GSDServiceFactory.getService(getActivity()).postInspectEquipment(
                syncPostUnitInspectionRequestDTO, this
        );


    }


    void callSyncPostEqupmentService() {

        //if (0 != lstEditEquipment.size() || 0 != lstAddEquipment.size()) {
        CommonActions.showProgressDialog(getActivity());
        // Toast.makeText(getActivity(), "Sync Post Initiated", Toast.LENGTH_LONG).show();
        tvSyncInProgress.setText("Sync post in progress...");
        GSDServiceFactory.getService(getActivity()).postSyncEquipment(
                syncPostEquipmentRequestDTO, this
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
        String delegate = "hh:mm aaa";
        tvLastSyncTime.setText("" + DateFormat.format(delegate, Calendar.getInstance().getTime()));
        sharedPreferencesManager.setString(SharedPreferencesManager.LAST_SYNC_DATE, formattedDate);
        sharedPreferencesManager.setString(SharedPreferencesManager.LAST_SYNC_TIME,
                DateFormat.format(delegate, Calendar.getInstance().getTime()).toString());

    }


    private void callSyncGetService() {
        tvSyncInProgress.setText("Sync get in progress...");
        CommonActions.showProgressDialog(getActivity());
        GSDServiceFactory.getService(getActivity()).getSyncData(new SyncGetDTO(Constants.RESPONSE_SYNC_GET, sharedPreferencesManager.getString(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_CODE), Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID)), this);
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


    private void setupPostAddedLocationsData() {
        locationsList = DataManager.getInstance().getAllAddedLocations();

        if (null != locationsList) {
            for (Locations location : locationsList) {
                AddLocation addLocation = new AddLocation();
                addLocation.setCode(location.getCode());
                addLocation.setDescription(location.getDescription());
                addLocation.setCustomer(location.getCustomer().getID());
                addLocation.setSite(location.getSite().getID());
                addLocation.setBuilding(location.getBuilding().getID());
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
                equipment.setVendorID(fireBugEquipment.getVendorCode().getID());
                equipment.setAgentID(fireBugEquipment.getAgentType().getID());
                equipment.setAssignedLocation(fireBugEquipment.getLocation().getID());
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
                List<EquipmentNote> lstNotes = DataManager.getInstance().getAllNotes(fireBugEquipment.getID());
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
                equipment.setVendorID(fireBugEquipment.getVendorCode().getID());
                equipment.setAgentID(fireBugEquipment.getAgentType().getID());
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
                List<EquipmentNote> lstNotes = DataManager.getInstance().getAllNotes(fireBugEquipment.getID());
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
                    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
                    //Toast.makeText(getActivity(), "Sync Post Complete", Toast.LENGTH_LONG).show();

                    if (sendLocationcall) {
                        callSyncPostLocationService();
                    } else if (sendMovecall) {
                        callSyncPostMoveService();
                    } else if (sendUnitInspcall) {
                        callSyncPostUnitInspectService();
                    } else {
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
                    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if (sendMovecall) {
                        callSyncPostMoveService();
                    } else if (sendUnitInspcall) {
                        callSyncPostUnitInspectService();
                    } else {
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
                    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());

                    if (sendUnitInspcall) {
                        callSyncPostUnitInspectService();
                    } else {
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
                    lstSyncPostEquipmentResults.addAll(syncPostEquipmentResponseDTO.getSyncPostEquipments());
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
                    if (null != syncGetResponseDTO) {
                        CommonActions.DismissesDialog();
                        tvSyncInProgress.setText("Sync Complete!");
                        // Toast.makeText(getActivity(), "Sync Get Complete!", Toast.LENGTH_LONG).show();

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

                        itemList[itemList.length - 1] = "Update Db SUCCESS";


                        DataManager.getInstance().saveSyncGetResponse(syncGetResponseDTO);
                        sharedPreferencesManager.setInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID, syncGetResponseDTO.getCustomerId());
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

                        itemList[itemList.length - 1] = "Update Db FAILURE";
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
        tvSyncInProgress.setText("Sync post in progress...");
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
