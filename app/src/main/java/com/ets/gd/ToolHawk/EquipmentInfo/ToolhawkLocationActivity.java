package com.ets.gd.ToolHawk.EquipmentInfo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.AllCustomers;
import com.ets.gd.NetworkLayer.ResponseDTOs.Building;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSBuilding;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Site;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class ToolhawkLocationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack, ivTick;
    TextView tbTitleBottom, tbTitleTop;
    Spinner spSite, spBuilding, spCustomer;
    public static EditText tvDescprition, tvLocationID;
    public static int posLoc = 0, posSite = 0, posBuilding = 0, posCustomer = 0;
    List<Site> allSites = new ArrayList<Site>();
    List<ETSBuilding> allBuilding = new ArrayList<ETSBuilding>();
    List<AllCustomers> allCustomers = new ArrayList<AllCustomers>();
    SharedPreferencesManager sharedPreferencesManager;
    String[] sites;
    String[] buildings;
    String[] customers;
    String taskType, barcodeID;
    ETSLocations etsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolhawk_location);

        initViews();
        initObj();
        initListeners();
        hideKeyboard();

    }

    private void initViews() {

        spSite = (Spinner) findViewById(R.id.spDep);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvLocationID = (EditText) findViewById(R.id.tvLocationID);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        spBuilding = (Spinner) findViewById(R.id.spLoc);
        spCustomer = (Spinner) findViewById(R.id.spCustomer);
        tvDescprition = (EditText) findViewById(R.id.tvDescprition);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        taskType = getIntent().getStringExtra("taskType");
        barcodeID = getIntent().getStringExtra("barcodeID");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Add Location");

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            spSite.setBackgroundColor(Color.parseColor("#ffffff"));
            spBuilding.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    private void initObj() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sharedPreferencesManager = new SharedPreferencesManager(ToolhawkLocationActivity.this);


        allSites = DataManager.getInstance().getAllSites();
        allBuilding = DataManager.getInstance().getAllETSBuildings();
        allCustomers = DataManager.getInstance().getAllCustomerList();

        int sizeSite = allSites.size() + 1;
        sites = new String[sizeSite];

        for (int i = 0; i < allSites.size(); i++) {
            sites[i + 1] = allSites.get(i).getCode();
        }
        sites[0] = "Please select a site";

        ArrayAdapter<String> dataAdapterSIte = new ArrayAdapter<String>(ToolhawkLocationActivity.this, android.R.layout.simple_spinner_item, sites);
        dataAdapterSIte.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterSIte);

        int sizeBuilding = allBuilding.size() + 1;
        buildings = new String[sizeBuilding];

        for (int i = 0; i < allBuilding.size(); i++) {
            buildings[i + 1] = allBuilding.get(i).getCode();
        }
        buildings[0] = "Please select a building";
        ArrayAdapter<String> dataAdapterBuilding = new ArrayAdapter<String>(ToolhawkLocationActivity.this, android.R.layout.simple_spinner_item, buildings);
        dataAdapterBuilding.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuilding.setAdapter(dataAdapterBuilding);


        int customerSize = allCustomers.size() + 1;
        customers = new String[customerSize];

        for (int i = 0; i < allCustomers.size(); i++) {
            customers[i + 1] = allCustomers.get(i).getCode();
        }
        customers[0] = "Please select a company";

        ArrayAdapter<String> dataAdapterCus = new ArrayAdapter<String>(ToolhawkLocationActivity.this, android.R.layout.simple_spinner_item, customers);
        dataAdapterCus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCustomer.setAdapter(dataAdapterCus);


        if(taskType.toLowerCase().startsWith("add")){
            setViewForAddLoc();
        }else{
            setViewForViewLoc();
        }



    }


    void setViewForAddLoc() {
        ivTick.setVisibility(View.VISIBLE);
        tvLocationID.setVisibility(View.VISIBLE);
        tvLocationID.setText("");
        tvDescprition.setText("");
        spSite.setSelection(0);
        spBuilding.setSelection(0);
        tvDescprition.setEnabled(true);
        tvLocationID.setEnabled(true);
        spBuilding.setEnabled(true);
        spSite.setEnabled(true);


    }

    void setViewForViewLoc() {
        etsLocation = DataManager.getInstance().getETSLocationsByCode(barcodeID);
        if (null != etsLocation) {
            tvLocationID.setText(""+etsLocation.getCode());
            tvDescprition.setText(""+etsLocation.getDescription());
        }
        ivTick.setVisibility(View.GONE);
        tvLocationID.setVisibility(View.VISIBLE);


        tvDescprition.setEnabled(false);
        tvLocationID.setEnabled(false);
        spBuilding.setEnabled(false);
        spSite.setEnabled(false);
        spCustomer.setEnabled(false);


        for (int i = 0; i < allSites.size() + 1; i++) {
            if (null != DataManager.getInstance().getSiteByID(etsLocation.getSite().getID())) {
                if (DataManager.getInstance().getSiteByID(etsLocation.getSite().getID()).getCode().toLowerCase().equals(spSite.getItemAtPosition(i).toString().toLowerCase())) {
                    spSite.setSelection(i);
                    posSite = i;
                    break;
                }
            }
        }

        for (int i = 0; i < allBuilding.size() + 1; i++) {
            if (null != DataManager.getInstance().getBuilding(etsLocation.getBuilding().getID())) {
                if (DataManager.getInstance().getBuilding(etsLocation.getBuilding().getID()).getCode().toLowerCase().equals(spBuilding.getItemAtPosition(i).toString().toLowerCase())) {
                    spBuilding.setSelection(i);
                    posBuilding = i;
                    break;
                }
            }
        }


        for (int i = 0; i < allCustomers.size() + 1; i++) {
            if (null != DataManager.getInstance().getCustomerByID(etsLocation.getCustomer().getID())) {
                if (DataManager.getInstance().getCustomerByID(etsLocation.getCustomer().getID()).getCode().toLowerCase().equals(spCustomer.getItemAtPosition(i).toString().toLowerCase())) {
                    spCustomer.setSelection(i);
                    posCustomer = i;
                    break;
                }
            }
        }

    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                tvLocationID.getWindowToken(), 0);
        InputMethodManager imm2 = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.hideSoftInputFromWindow(
                tvDescprition.getWindowToken(), 0);
    }

    private void initListeners() {
        spSite.setOnItemSelectedListener(this);
        spBuilding.setOnItemSelectedListener(this);
        spCustomer.setOnItemSelectedListener(this);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);


        spSite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });


        spBuilding.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });


        spCustomer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

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
                        ETSLocation etsLoc = DataManager.getInstance().getETSLocationByCodeOnly(tvLocationID.getText().toString());

                        if (null == etsLoc) {
                            ETSLocation etsLocation = new ETSLocation(
                                    0,
                                    tvLocationID.getText().toString(),
                                    tvDescprition.getText().toString(),
                                    DataManager.getInstance().getCustomerByCode(spCustomer.getItemAtPosition(posCustomer).toString()).getID(),
                                    DataManager.getInstance().getLocationSite(spSite.getItemAtPosition(posSite).toString()).getID(),
                                    DataManager.getInstance().getETSBuilding(spBuilding.getItemAtPosition(posBuilding).toString()).getID(),
                                    true
                            );
                            DataManager.getInstance().addETSLocation(etsLocation);
                            showToast("ETS Location Added!");
                            finish();
                        } else {
                            showToast("ETS with Location ID " + tvLocationID.getText().toString() + " Already Exist!");
                        }
                    }
                    break;
                }


 /*                   List<Locations> locationsList = DataManager.getInstance().getAllCompanyLocations(DataManager.getInstance().getCustomerByCode(spCustomer.getSelectedItem().toString()).getID());
                    Locations location = DataManager.getInstance().getLocation(tvLocationID.getText().toString());
                    boolean exists = false;
                    if (checkValidation()) {

                        if (null!=location) {
                            for(Locations loc : locationsList){
                                if(loc.getCode().equals(location.getCode())){
                                    exists = true;
                                    break;
                                }
                            }
                        }
                        if (!exists) {
                            Locations locations = new Locations(tvLocationID.getText().toString(),
                                    tvDescprition.getText().toString(),
                                    DataManager.getInstance().getCustomerByCode(spCustomer.getItemAtPosition(posCustomer).toString()),
                                    DataManager.getInstance().getLocationSite(spSite.getItemAtPosition(posSite).toString()),
                                    DataManager.getInstance().getLocationBuilding(spBuilding.getItemAtPosition(posBuilding).toString()), true);
                            DataManager.getInstance().addNewLocation(locations);

                            Toast.makeText(getApplicationContext(), "Location Added!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Location Already Added!", Toast.LENGTH_LONG).show();
                        }
                    }
*/

            }
        }

    };

    private boolean checkValidation() {
        if ("".equals(tvLocationID.getText().toString().trim())) {
            showToast("Please enter a location");
        } else if ("".equals(tvDescprition.getText().toString().trim())) {
            showToast("Please enter a Description");
        } else if (0 == posSite) {
            showToast("Please select a site");
        } else if (0 == posBuilding) {
            showToast("Please select a building");
        } else if (0 == posCustomer) {
            showToast("Please select a Company");
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

            case R.id.spDep: {
                posSite = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }


            }
            break;

            case R.id.spLoc: {
                posBuilding = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }


            }
            break;

            case R.id.spCustomer: {
                posCustomer = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }


            }
            break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
