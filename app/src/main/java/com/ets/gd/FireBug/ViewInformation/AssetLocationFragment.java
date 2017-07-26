package com.ets.gd.FireBug.ViewInformation;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Scan.BarcodeScanActivity;
import com.ets.gd.Interfaces.BarcodeScan;
import com.ets.gd.Models.Barcode;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.FirebugBuilding;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncCustomer;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;


public class AssetLocationFragment extends Fragment implements Spinner.OnItemSelectedListener, BarcodeScan {


    public static Spinner spLocation, spCustomer;
    AutoCompleteTextView spSite, spBuilding;
    View rootView;
    public static EditText tvDescprition, tvLocationID;
    private TextInputLayout letLocationID, lLocationID, lDescprition;
    // Asset asset;
    SharedPreferencesManager sharedPreferencesManager;
    public static int posLoc = 0, posSite = 0, posBuilding = 0, posCustomer = 0;
    FireBugEquipment fireBugEquipment;
    SyncCustomer realmSyncGetResponseDTO;
    RealmSyncGetResponseDTO realmSyncGetResponse;
    String[] sites;
    String[] buildings;
    String[] locations;
    String[] customers;
    Customer customer;
    public static String customerName;
    List<FirebugBuilding> allBuilding = new ArrayList<FirebugBuilding>();
    Button btnSearchLoc;
    private static final int CAMERA_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;


    public AssetLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_asset_location, container, false);


        initViews();
        initObj();
        initListeners();
        return rootView;

    }

    private void initViews() {
        spCustomer = (Spinner) rootView.findViewById(R.id.spCustomer);
        spSite = (AutoCompleteTextView) rootView.findViewById(R.id.spDep);
        spBuilding = (AutoCompleteTextView) rootView.findViewById(R.id.spLoc);
        spLocation = (Spinner) rootView.findViewById(R.id.spLocation);
        lLocationID = (TextInputLayout) rootView.findViewById(R.id.lLocationID);
        letLocationID = (TextInputLayout) rootView.findViewById(R.id.letLocationID);
        tvDescprition = (EditText) rootView.findViewById(R.id.tvDescprition);
        tvLocationID = (EditText) rootView.findViewById(R.id.tvLocationID);
        btnSearchLoc = (Button) rootView.findViewById(R.id.btnSearchLoc);
        letLocationID.setVisibility(View.INVISIBLE);
        tvLocationID.setVisibility(View.INVISIBLE);

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            spCustomer.setBackgroundColor(Color.parseColor("#ffffff"));
            spSite.setBackgroundColor(Color.parseColor("#ffffff"));
            spLocation.setBackgroundColor(Color.parseColor("#ffffff"));
            spBuilding.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        //asset = ((ViewAssetInformationActivity) getActivity()).getAsset();
        fireBugEquipment = ((ViewAssetInformationActivity) getActivity()).getEquipment();

        if (!"addAsset".equals(getActivity().getIntent().getStringExtra("action"))) {
            customer = fireBugEquipment.getCustomer();
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
            realmSyncGetResponse = DataManager.getInstance().getSyncRealmGetResponseDTO();
        } else {
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
            realmSyncGetResponse = DataManager.getInstance().getSyncRealmGetResponseDTO();
        }


        RealmList<Locations> locationsRealmList = new RealmList<Locations>();

        for (int k = 0; k < realmSyncGetResponseDTO.getLstLocations().size(); k++) {
            if (!realmSyncGetResponseDTO.getLstLocations().get(k).isAdded()) {
                locationsRealmList.add(realmSyncGetResponseDTO.getLstLocations().get(k));
            }
        }

        locations = new String[1];
        locations[0] = "Please select a location";
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);


        int sizeCustomer = realmSyncGetResponse.getLstCustomers().size() + 1;
        customers = new String[sizeCustomer];

        for (int i = 0; i < realmSyncGetResponse.getLstCustomers().size(); i++) {
            customers[i + 1] = realmSyncGetResponse.getLstCustomers().get(i).getCode();
        }
        customers[0] = "Please select a company";
        ArrayAdapter<String> dataAdapterCustomer = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, customers);
        dataAdapterCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(dataAdapterCustomer);

        if (!"addAsset".equals(getActivity().getIntent().getStringExtra("action"))) {
            for (int i = 0; i < customers.length; i++) {
                if (customer.getCode().toLowerCase().equals(spCustomer.getItemAtPosition(i).toString().toLowerCase())) {
                    spCustomer.setSelection(i);
                    posCustomer = i;
                    break;
                }
            }
        } else {
            for (int i = 0; i < customers.length; i++) {
                if (customerName.toLowerCase().equals(spCustomer.getItemAtPosition(i).toString().toLowerCase())) {
                    spCustomer.setSelection(i);
                    posCustomer = i;
                    break;
                }
            }
        }

        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {
        spLocation.setEnabled(false);
        spSite.setEnabled(false);
        btnSearchLoc.setVisibility(View.GONE);
        spBuilding.setEnabled(false);
        spCustomer.setEnabled(false);

        Customer customer = DataManager.getInstance().getCustomerByCode(spCustomer.getSelectedItem().toString());
        SyncCustomer syncCustomer = DataManager.getInstance().getSyncGetResponseDTO(customer.getID());

        int sizeSite = syncCustomer.getLstLocations().size();
        sites = new String[sizeSite];

        for (int i = 0; i < syncCustomer.getLstLocations().size(); i++) {
            sites[i] = syncCustomer.getLstLocations().get(i).getSite().getCode();
        }
        ArrayAdapter<String> dataAdapterSIte = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sites);
        dataAdapterSIte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterSIte);


        int size = syncCustomer.getLstLocations().size() + 1;
        String[] locations = new String[size];

        for (int i = 0; i < syncCustomer.getLstLocations().size(); i++) {
            locations[i + 1] = syncCustomer.getLstLocations().get(i).getCode();
        }
        locations[0] = "Please select a location";
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);


        spLocation.setEnabled(false);
        if (null != fireBugEquipment.getLocation()) {
            for (int i = 0; i < locations.length; i++) {
                if (fireBugEquipment.getLocation().getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                    spLocation.setSelection(i);
                    tvDescprition.setText(realmSyncGetResponseDTO.getLstLocations().get(i - 1).getDescription());
                    break;
                }
            }
        }


    }


    void setViewForVAddAsset() {
        spLocation.setEnabled(true);
        spLocation.setSelection(0);
        tvDescprition.setText("No location selected");
        spSite.setEnabled(false);
        spBuilding.setEnabled(false);
        spCustomer.setEnabled(false);
        btnSearchLoc.setVisibility(View.VISIBLE);

    }

    private void initListeners() {
        spLocation.setOnItemSelectedListener(this);
        spCustomer.setOnItemSelectedListener(this);

        btnSearchLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spLocation: {
                posLoc = position;
                int selectedSiteID = 0;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (null != DataManager.getInstance().getLocation(strSelectedState)) {
                    for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size() + 1; i++) {
                        if (DataManager.getInstance().getLocation(strSelectedState).getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                            spLocation.setSelection(i);
                            tvDescprition.setText(DataManager.getInstance().getLocation(strSelectedState).getDescription());
                            for (int k = 0; k < sites.length; k++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getSite().getCode().toLowerCase().equals(sites[k].toString().toLowerCase())) {
                                    spSite.setText(sites[k].toString());
                                    selectedSiteID = DataManager.getInstance().getLocation(strSelectedState).getSite().getID();
                                    break;
                                }
                            }


                            allBuilding.clear();
                            if (0 != selectedSiteID) {
                                allBuilding = DataManager.getInstance().getAllFirebugSiteBuildings(selectedSiteID);
                            }
                            if (null != allBuilding) {
                                int sizeBuilding = allBuilding.size();
                                buildings = new String[sizeBuilding];

                                for (int l = 0; l < allBuilding.size(); l++) {
                                    buildings[l] = allBuilding.get(l).getCode();
                                }
                                ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, buildings);
                                dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spBuilding.setAdapter(dataAdapterBuilding);
                            }


                            for (int j = 0; j < buildings.length; j++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getBuilding().getCode().toLowerCase().equals(buildings[j].toString().toLowerCase())) {
                                    spBuilding.setText(buildings[j].toString());
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } else {
                    spLocation.setEnabled(true);
                    spLocation.setSelection(0);
                    tvDescprition.setText("No location selected");
                    spSite.setEnabled(false);
                    spBuilding.setEnabled(false);
                    spSite.setSelection(0);
                    spBuilding.setSelection(0);
                }

                try {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            break;

            case R.id.spCustomer: {
                posCustomer = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                try {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (0 != posCustomer && !"viewAsset".equals(ViewAssetInformationActivity.actionType)) {
                Customer customer = DataManager.getInstance().getCustomerByCode(spCustomer.getItemAtPosition(posCustomer).toString());
                SyncCustomer syncCustomer = DataManager.getInstance().getSyncGetResponseDTO(customer.getID());
                int sizeSite = syncCustomer.getLstLocations().size();
                sites = new String[sizeSite];

                for (int i = 0; i < syncCustomer.getLstLocations().size(); i++) {
                    sites[i] = syncCustomer.getLstLocations().get(i).getSite().getCode();
                }
                ArrayAdapter<String> dataAdapterSIte = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sites);
                dataAdapterSIte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSite.setAdapter(dataAdapterSIte);

                int sizeBuilding = syncCustomer.getLstLocations().size();
                buildings = new String[sizeBuilding];

                for (int i = 0; i < syncCustomer.getLstLocations().size(); i++) {
                    buildings[i] = syncCustomer.getLstLocations().get(i).getBuilding().getCode();
                }
                ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, buildings);
                dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBuilding.setAdapter(dataAdapterBuilding);


                int size = syncCustomer.getLstLocations().size() + 1;
                 locations = new String[size];

                for (int i = 0; i < syncCustomer.getLstLocations().size(); i++) {
                    locations[i + 1] = syncCustomer.getLstLocations().get(i).getCode();
                }
                locations[0] = "Please select a location";
                ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locations);
                dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLocation.setAdapter(dataAdapterAgent);

            }
            break;

        }
    }


    private void checkCameraPermission() {

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION)) {
            BarcodeScanActivity.barcodeScan = this;
            Intent in = new Intent(getActivity(), BarcodeScanActivity.class);
            in.putExtra("taskType", "searchLoc");
            startActivity(in);
        } else {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission.");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.IS_NEVER_ASK_AGAIN)) {
                    //Previously Permission Request was cancelled with 'Dont Ask Again',
                    // Redirect to Settings after showing Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            Toast.makeText(getActivity(), "Go to Permissions to Grant Camera permission", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    //just request the permission
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CONSTANT);
                }

            } else {
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(getActivity(), BarcodeScanActivity.class);
                in.putExtra("taskType", "searchLoc");
                startActivity(in);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sharedPreferencesManager.setBoolean(SharedPreferencesManager.IS_CAMERA_PERMISSION, true);
                BarcodeScanActivity.barcodeScan = this;
                Intent in = new Intent(getActivity(), BarcodeScanActivity.class);
                in.putExtra("taskType", "searchLoc");
                startActivity(in);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Camera Permission");
                    builder.setMessage("This app needs permission to use camera");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    sharedPreferencesManager.setBoolean(SharedPreferencesManager.IS_NEVER_ASK_AGAIN, true);
                    Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void BarcodeScanned(Barcode barcode) {
        String message = barcode.getMessage();
        boolean found = false;
        for (int i = 0; i < locations.length; i++) {
            if (message.toLowerCase().equals(locations[i].toLowerCase())) {
                spLocation.setSelection(i);
                found = true;
            }
        }

        if (!found) {
            showToast("Location not found!");
            spLocation.setSelection(0);
            spSite.setSelection(0);
            spBuilding.setSelection(0);
        }

    }


    void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
