package com.ets.gd.FireBug.ViewInformation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.FirebugBuilding;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Site;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncCustomer;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack, ivTick;
    Spinner spLocation, spCustomer;
    AutoCompleteTextView spSite, spBuilding;
    public static EditText tvDescprition, tvLocationID;
    Locations location;
    Customer customer;
    String customerName, compName;
    private TextInputLayout lLocationID, lDescprition;
    public static String actionType, barCodeID;
    public static int posLoc = 0, posBuilding = 0, posCustomer = 0;
    SyncCustomer realmSyncGetResponseDTO;
    List<Site> allSites = new ArrayList<Site>();
    TextView tvLableLocation, tbTitleBottom;
    List<FirebugBuilding> allBuilding = new ArrayList<FirebugBuilding>();
    RealmSyncGetResponseDTO realmSyncGetResponse;
    SharedPreferencesManager sharedPreferencesManager;
    Button btnSearchLoc;
    String[] sites;
    String[] buildings;
    String[] customers;
    String[] locations;
    Button btnViewAllAssets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location_information);

        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {

        spLocation = (Spinner) findViewById(R.id.spLocation);
        spSite = (AutoCompleteTextView) findViewById(R.id.spDep);
        spCustomer = (Spinner) findViewById(R.id.spCustomer);
        tvLocationID = (EditText) findViewById(R.id.tvLocationID);
        lLocationID = (TextInputLayout) findViewById(R.id.lLocationID);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tvLableLocation = (TextView) findViewById(R.id.tvLableLocation);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        spBuilding = (AutoCompleteTextView) findViewById(R.id.spLoc);
        tvDescprition = (EditText) findViewById(R.id.tvDescprition);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnViewAllAssets = (Button) findViewById(R.id.btnViewAllAssets);
        btnSearchLoc= (Button) findViewById(R.id.btnSearchLoc);
        btnSearchLoc.setVisibility(View.GONE);
        barCodeID = getIntent().getStringExtra("barCode");
        customerName = getIntent().getStringExtra("customerName");
        compName = getIntent().getStringExtra("compName");
        if (null != barCodeID) {
            location = DataManager.getInstance().getLocation(barCodeID);
            customer = location.getCustomer();
        }

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            spCustomer.setBackgroundColor(Color.parseColor("#ffffff"));
            spSite.setBackgroundColor(Color.parseColor("#ffffff"));
            spLocation.setBackgroundColor(Color.parseColor("#ffffff"));
            spBuilding.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    private void initObj() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //asset = ((ViewAssetInformationActivity) getActivity()).getAsset();
        sharedPreferencesManager = new SharedPreferencesManager(ViewLocationInformationActivity.this);
        realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        realmSyncGetResponse = DataManager.getInstance().getSyncRealmGetResponseDTO();
        int size = realmSyncGetResponseDTO.getLstLocations().size() + 1;
        locations = new String[size];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            locations[i + 1] = realmSyncGetResponseDTO.getLstLocations().get(i).getCode();
        }
        locations[0] = "Please select a location";
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);

        int sizeCustomer = realmSyncGetResponse.getLstCustomers().size() + 1;
        customers = new String[sizeCustomer];

        for (int i = 0; i < realmSyncGetResponse.getLstCustomers().size(); i++) {
            customers[i + 1] = realmSyncGetResponse.getLstCustomers().get(i).getCode();
        }
        customers[0] = "Please select a company";
        ArrayAdapter<String> dataAdapterCustomer = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, customers);
        dataAdapterCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(dataAdapterCustomer);


        if (null != barCodeID) {
            for (int i = 0; i < customers.length; i++) {
                if (customer.getCode().toLowerCase().equals(spCustomer.getItemAtPosition(i).toString().toLowerCase())) {
                    spCustomer.setSelection(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < customers.length; i++) {
                if (customerName.toLowerCase().equals(spCustomer.getItemAtPosition(i).toString().toLowerCase())) {
                    spCustomer.setSelection(i);
                    break;
                }
            }
        }


        allSites = DataManager.getInstance().getAllSites();

        int sizeSite = allSites.size();
        sites = new String[sizeSite];

        for (int i = 0; i < allSites.size(); i++) {
            sites[i] = allSites.get(i).getCode();
        }
        ArrayAdapter<String> dataAdapterSIte = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_list_item_1, sites);
        dataAdapterSIte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterSIte);

        // int sizeBuilding = allBuilding.size() + 1;

//        for (int i = 0; i < allBuilding.size(); i++) {
//            buildings[i + 1] = allBuilding.get(i).getCode();
//        }


        actionType = getIntent().getStringExtra("action");
        if ("viewLoc".equals(actionType)) {
            ivTick.setVisibility(View.GONE);
            setViewForViewLoc();
        } else {
            setViewForAddLoc();
        }

    }

    void setViewForViewLoc() {
        spCustomer.setEnabled(false);
        tvLocationID.setVisibility(View.VISIBLE);
        tvLocationID.setEnabled(false);
        spLocation.setVisibility(View.GONE);
        tvLableLocation.setVisibility(View.GONE);
        tvDescprition.setEnabled(false);
        spBuilding.setEnabled(false);
        spSite.setEnabled(false);
        tbTitleBottom.setText("View Location");
        tvLocationID.setText(location.getCode());
        tvDescprition.setText(location.getDescription());
        btnViewAllAssets.setVisibility(View.VISIBLE);


        for (int i = 0; i < sites.length; i++) {
            if (location.getSite().getCode().toLowerCase().equals(sites[i].toString().toLowerCase())) {
                spSite.setText("" + sites[i].toString());
                break;
            }
        }


        allBuilding.clear();
        if (null != DataManager.getInstance().getSite(spSite.getText().toString().trim())) {

            allBuilding = DataManager.getInstance().getAllFirebugSiteBuildings(DataManager.getInstance().getSite(spSite.getText().toString().trim()).getID());
        }
        if (null != allBuilding) {
            int sizeBuilding = allBuilding.size();
            buildings = new String[sizeBuilding];

            for (int i = 0; i < allBuilding.size(); i++) {
                buildings[i] = allBuilding.get(i).getCode();
            }
            ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_list_item_1, buildings);
            dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spBuilding.setAdapter(dataAdapterBuilding);

            if ("viewLoc".equals(actionType)) {
                for (int j = 0; j < buildings.length; j++) {
                    if (location.getBuilding().getCode().toLowerCase().equals(buildings[j].toString().toLowerCase())) {
                        spBuilding.setText(buildings[j].toString());
                        break;
                    }
                }
            }
        }

    }


    void setViewForAddLoc() {
        tvLocationID.setVisibility(View.VISIBLE);
        tbTitleBottom.setText("Add Location");
        tvLocationID.setText("");
        tvDescprition.setText("");
        // spSite.setSelection(0);
        // spBuilding.setSelection(0);
        spLocation.setVisibility(View.GONE);
        tvLableLocation.setVisibility(View.GONE);
        tvDescprition.setEnabled(true);
        tvLocationID.setEnabled(true);
        spBuilding.setEnabled(true);
        spSite.setEnabled(true);
        spCustomer.setEnabled(false);
        btnViewAllAssets.setVisibility(View.GONE);
        //spCustomer.setSelection(0);
    }

    private void initListeners() {
        spLocation.setOnItemSelectedListener(this);
        spCustomer.setOnItemSelectedListener(this);
        btnViewAllAssets.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

        spSite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();
                allBuilding.clear();
                if (null != DataManager.getInstance().getSite(spSite.getText().toString().trim())) {

                    allBuilding = DataManager.getInstance().getAllFirebugSiteBuildings(DataManager.getInstance().getSite(spSite.getText().toString().trim()).getID());
                }
                if (null != allBuilding) {
                    int sizeBuilding = allBuilding.size();
                    buildings = new String[sizeBuilding];

                    for (int i = 0; i < allBuilding.size(); i++) {
                        buildings[i] = allBuilding.get(i).getCode();
                    }
                    ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_list_item_1, buildings);
                    dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spBuilding.setAdapter(dataAdapterBuilding);

                    if ("viewLoc".equals(actionType)) {
                        for (int j = 0; j < buildings.length; j++) {
                            if (DataManager.getInstance().getBuilding(DataManager.getInstance().getLocation(tvLocationID.getText().toString()).getBuilding().getID()).getCode().toLowerCase().equals(buildings[j].toString().toLowerCase())) {
                                spBuilding.setText(buildings[j].toString());
                                break;
                            }
                        }
                    }
                }

            }
        });

        spBuilding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hideKeyboard();
            }
        });
        spSite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spSite.showDropDown();
                return false;
            }
        });


        spBuilding.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spBuilding.showDropDown();
                return false;
            }
        });
    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                spSite.getWindowToken(), 0);

        InputMethodManager imm2 = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.hideSoftInputFromWindow(
                spBuilding.getWindowToken(), 0);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }


                case R.id.btnViewAllAssets: {
                    String locCode = tvLocationID.getText().toString();
                    if (null != DataManager.getInstance().getFirebugLocEquipments(locCode) && 0 != DataManager.getInstance().getFirebugLocEquipments(locCode).size()) {
                        Intent in = new Intent(ViewLocationInformationActivity.this, ViewAllAssetsActivity.class);
                        in.putExtra("compName", compName);
                        in.putExtra("locCode", locCode);
                        startActivity(in);
                    } else {
                        showToast("" + locCode + " has no Asset(s)!");
                    }
                    break;
                }


                case R.id.ivTick: {
                    List<Locations> locationsList = DataManager.getInstance().getAllCompanyLocations(DataManager.getInstance().getCustomerByCode(spCustomer.getSelectedItem().toString()).getID());
                    Locations location = DataManager.getInstance().getLocation(tvLocationID.getText().toString());
                    boolean exists = false;
                    if (checkValidation()) {

                        if (null != location) {
                            for (Locations loc : locationsList) {
                                if (loc.getCode().equals(location.getCode())) {
                                    exists = true;
                                    break;
                                }
                            }
                        }
                        if (!exists) {
//                            DataManager.getInstance().addLocation(
//
//                                    new Location(tvLocationID.getText().toString().trim(),
//                                            tvDescprition.getText().toString().trim(),spSite.getItemAtPosition(posSite).toString(),
//                                            spBuilding.getItemAtPosition(posBuilding).toString(),"Shelf")
//                            );
                            Locations locations = new Locations(tvLocationID.getText().toString(),
                                    tvDescprition.getText().toString(),
                                    DataManager.getInstance().getCustomerByCode(spCustomer.getItemAtPosition(posCustomer).toString()),
                                    // DataManager.getInstance().getLocationSite(tvSite.getText().toString().trim()),
                                    //  DataManager.getInstance().getLocationBuilding(tvBuilding.getText().toString().trim()
                                    DataManager.getInstance().getLocationSite(spSite.getText().toString()),
                                    DataManager.getInstance().getLocationBuilding(spBuilding.getText().toString()), true);
                            DataManager.getInstance().addNewLocation(locations);
//                        MyLocation myLocation = new MyLocation(
//                                tvLocationID.getText().toString(),
//                                tvDescprition.getText().toString(),
//                                sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID),
//                                DataManager.getInstance().getLocationSite(spSite.getItemAtPosition(posSite).toString()).getID(),
//                                DataManager.getInstance().getLocationBuilding(spBuilding.getItemAtPosition(posBuilding).toString()).getID());
//
//                        DataManager.getInstance().addNewLocation(myLocation);
                            Toast.makeText(getApplicationContext(), "Location Added!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Location Already Added!", Toast.LENGTH_LONG).show();
                        }
                    }

                    break;
                }

            }
        }

    };

    private boolean checkValidation() {
        if ("".equals(tvLocationID.getText().toString().trim())) {
            showToast("Please enter a location");
        } else if (0 == posCustomer) {
            showToast("Please select a company");
        } else if ("".equals(spSite.getText().toString().trim())) {
            showToast("Please select a site");
        } else if (null == DataManager.getInstance().getLocationSite(spSite.getText().toString())) {
            showToast("Please select a valid site");
        } else if ("".equals(spBuilding.getText().toString().trim())) {
            showToast("Please select a building");
        } else if (null == DataManager.getInstance().getLocationBuilding(spBuilding.getText().toString())) {
            showToast("Please select a valid building");
        } else if (DataManager.getInstance().getLocationSite(spSite.getText().toString()).getID() != DataManager.getInstance().getLocationBuilding(spBuilding.getText().toString()).getSiteID()) {
            showToast("This Building doesn't belong to " + spSite.getText().toString());
        } else {

            return true;
        }

        return false;
    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spLocation: {
                posLoc = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (!"viewLoc".equals(actionType)) {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    }

                }


                //   if (position != 0) {
                if (null != DataManager.getInstance().getLocation(strSelectedState)) {
                    for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
                        if (DataManager.getInstance().getLocation(strSelectedState).getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                            spLocation.setSelection(i);
                            tvDescprition.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getDescription());


                            for (int k = 0; k < sites.length; k++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getSite().getCode().toLowerCase().equals(sites[i].toString().toLowerCase())) {
                                    spSite.setText(sites[k].toString());
                                    break;
                                }
                            }

                            for (int j = 0; j < buildings.length; j++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getBuilding().getCode().toLowerCase().equals(buildings[j].toString().toLowerCase())) {
                                    spBuilding.setSelection(j);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } else {
                    setViewForAddLoc();
                }
                //  }


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

//                if(0!=posCustomer){
//                    Customer customer = DataManager.getInstance().getCustomerByCode(spCustomer.getItemAtPosition(posCustomer).toString());
//                    SyncCustomer syncCustomer = DataManager.getInstance().getSyncGetResponseDTO(customer.getID());
//                    int sizeSite = syncCustomer.getLstLocations().size() + 1;
//                    sites = new String[sizeSite];
//
//                    for (int i = 0; i < syncCustomer.getLstLocations().size(); i++) {
//                        sites[i + 1] = syncCustomer.getLstLocations().get(i).getSite().getCode();
//                    }
//                    sites[0] = "Please select a site";
//                    ArrayAdapter<String> dataAdapterSIte = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, sites);
//                    dataAdapterSIte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spSite.setAdapter(dataAdapterSIte);
//
//                    int sizeBuilding = syncCustomer.getLstLocations().size() + 1;
//                    buildings = new String[sizeBuilding];
//
//                    for (int i = 0; i < syncCustomer.getLstLocations().size(); i++) {
//                        buildings[i + 1] = syncCustomer.getLstLocations().get(i).getBuilding().getCode();
//                    }
//                    buildings[0] = "Please select a building";
//                    ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, buildings);
//                    dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spBuilding.setAdapter(dataAdapterBuilding);
//                }
            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
