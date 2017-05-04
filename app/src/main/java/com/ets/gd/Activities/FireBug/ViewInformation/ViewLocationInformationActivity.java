package com.ets.gd.Activities.FireBug.ViewInformation;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.AssetLocationFragment;
import com.ets.gd.Models.Location;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.Building;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.Site;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack, ivTick;
    Spinner spLocation, spSite, spBuilding,spCustomer;
    public static EditText tvDescprition, tvLocationID;
    Locations location;
    Customer customer;
    private TextInputLayout lLocationID, lDescprition;
    public static String actionType, barCodeID;
    public static int posLoc = 0, posSite = 0, posBuilding = 0, posCustomer= 0;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;
    SharedPreferencesManager sharedPreferencesManager;
    String[] sites;
    String[] buildings;
    String[] customers;

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
        spSite = (Spinner) findViewById(R.id.spSite);
        spCustomer = (Spinner) findViewById(R.id.spCustomer);
        tvLocationID = (EditText) findViewById(R.id.tvLocationID);
        lLocationID = (TextInputLayout) findViewById(R.id.lLocationID);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        spBuilding = (Spinner) findViewById(R.id.spBuilding);
        tvDescprition = (EditText) findViewById(R.id.tvDescprition);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        barCodeID = getIntent().getStringExtra("barCode");
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

        int size = realmSyncGetResponseDTO.getLstLocations().size() + 1;
        String[] locations = new String[size];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            locations[i + 1] = realmSyncGetResponseDTO.getLstLocations().get(i).getCode();
        }
        locations[0] = "Please select a location";
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);


        int sizeSite = realmSyncGetResponseDTO.getLstLocations().size() + 1;
        sites = new String[sizeSite];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            sites[i + 1] = realmSyncGetResponseDTO.getLstLocations().get(i).getSite().getCode();
        }
        sites[0] = "Please select a site";
        ArrayAdapter<String> dataAdapterSIte = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, sites);
        dataAdapterSIte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterSIte);

        int sizeCustomer = realmSyncGetResponseDTO.getLstCustomers().size() + 1;
        customers = new String[sizeCustomer];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstCustomers().size(); i++) {
            customers[i + 1] = realmSyncGetResponseDTO.getLstCustomers().get(i).getCode();
        }
        customers[0] = "Please select a company";
        ArrayAdapter<String> dataAdapterCustomer = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, customers);
        dataAdapterCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(dataAdapterCustomer);


        int sizeBuilding = realmSyncGetResponseDTO.getLstLocations().size() + 1;
        buildings = new String[sizeBuilding];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            buildings[i + 1] = realmSyncGetResponseDTO.getLstLocations().get(i).getBuilding().getCode();
        }
        buildings[0] = "Please select a building";
        ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, buildings);
        dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuilding.setAdapter(dataAdapterBuilding);


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
        tvDescprition.setEnabled(false);
        spBuilding.setEnabled(false);
        spSite.setEnabled(false);

        tvLocationID.setText(location.getCode());
        tvDescprition.setText(location.getDescription());


        for (int i = 0; i < customers.length; i++) {
            if (customer.getCode().toLowerCase().equals(spCustomer.getItemAtPosition(i).toString().toLowerCase())) {
                spCustomer.setSelection(i);
                break;
            }
        }



        for (int i = 0; i < sites.length; i++) {
            if (location.getSite().getCode().toLowerCase().equals(spSite.getItemAtPosition(i).toString().toLowerCase())) {
                spSite.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < buildings.length; i++) {
            if (location.getBuilding().getCode().toLowerCase().equals(spBuilding.getItemAtPosition(i).toString().toLowerCase())) {
                spBuilding.setSelection(i);
                break;
            }
        }

    }


    void setViewForAddLoc() {
        tvLocationID.setVisibility(View.VISIBLE);
        tvLocationID.setText("");
        tvDescprition.setText("");
        spSite.setSelection(0);
        spBuilding.setSelection(0);
        spLocation.setVisibility(View.GONE);
        tvDescprition.setEnabled(true);
        tvLocationID.setEnabled(true);
        spBuilding.setEnabled(true);
        spSite.setEnabled(true);
        spCustomer.setEnabled(true);
        spCustomer.setSelection(0);


    }

    private void initListeners() {
        spLocation.setOnItemSelectedListener(this);
        spSite.setOnItemSelectedListener(this);
        spCustomer.setOnItemSelectedListener(this);
        spBuilding.setOnItemSelectedListener(this);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {
                    if (checkValidation()) {
//                        if (!DataManager.getInstance().doesLocationExist(tvLocationID.getText().toString().trim())) {
//                            DataManager.getInstance().addLocation(
//
//                                    new Location(tvLocationID.getText().toString().trim(),
//                                            tvDescprition.getText().toString().trim(),spSite.getItemAtPosition(posSite).toString(),
//                                            spBuilding.getItemAtPosition(posBuilding).toString(),"Shelf")
//                            );
                        Locations locations = new Locations(tvLocationID.getText().toString().trim(),
                                tvDescprition.getText().toString().trim(),
                                DataManager.getInstance().getCustomerByCode(spCustomer.getItemAtPosition(posCustomer).toString()),
                                // DataManager.getInstance().getLocationSite(tvSite.getText().toString().trim()),
                                //  DataManager.getInstance().getLocationBuilding(tvBuilding.getText().toString().trim()
                                DataManager.getInstance().getLocationSite(spSite.getItemAtPosition(posSite).toString()),
                                DataManager.getInstance().getLocationBuilding(spBuilding.getItemAtPosition(posBuilding).toString()), true);
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
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Location Already Added!", Toast.LENGTH_LONG).show();
//                        }
                    }

                    break;
                }
            }
        }

    };

    private boolean checkValidation() {
        if ("".equals(tvLocationID.getText().toString().trim())) {
            showToast("Please enter a location");
        }else if (0 == posCustomer) {
            showToast("Please select a company");
        }  else if (0 == posSite) {
            showToast("Please select a site");
        } else if (0 == posBuilding) {
            showToast("Please select a building");
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


                            for (int k = 0; k < sites.length;k++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getSite().getCode().toLowerCase().equals(spSite.getItemAtPosition(k).toString().toLowerCase())) {
                                    spSite.setSelection(k);
                                    break;
                                }
                            }

                            for (int j = 0; j < buildings.length; j++) {
                                if (DataManager.getInstance().getLocation(strSelectedState).getBuilding().getCode().toLowerCase().equals(spBuilding.getItemAtPosition(j).toString().toLowerCase())) {
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

            }
            break;

            case R.id.spSite: {
                posSite = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (!"viewLoc".equals(actionType)) {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    }

                }

            }
            break;

            case R.id.spBuilding: {
                posBuilding = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (!"viewLoc".equals(actionType)) {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    }

                }

            }
            break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
