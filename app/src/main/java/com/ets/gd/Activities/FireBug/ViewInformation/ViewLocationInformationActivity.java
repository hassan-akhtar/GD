package com.ets.gd.Activities.FireBug.ViewInformation;

import android.graphics.Color;
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
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Site;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack, ivTick;
    Spinner spLocation;
    public static EditText tvSite, tvDescprition, tvBuilding, tvCustomerID, tvLocationID;
    Locations location;
    private TextInputLayout lLocationID, lDescprition;
    public static String actionType, barCodeID;
    public static int posLoc = 0;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;
    SharedPreferencesManager sharedPreferencesManager;

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
        tvSite = (EditText) findViewById(R.id.tvSite);
        tvCustomerID = (EditText) findViewById(R.id.tvCustomerID);
        tvLocationID = (EditText) findViewById(R.id.tvLocationID);
        lLocationID = (TextInputLayout) findViewById(R.id.lLocationID);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        lDescprition = (TextInputLayout) findViewById(R.id.lDescprition);
        tvBuilding = (EditText) findViewById(R.id.tvBuilding);
        tvDescprition = (EditText) findViewById(R.id.tvDescprition);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        barCodeID = getIntent().getStringExtra("barCode");
        location = DataManager.getInstance().getLocation(barCodeID);

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

        actionType = getIntent().getStringExtra("action");
        if ("viewLoc".equals(actionType)) {
            ivTick.setVisibility(View.GONE);
            setViewForViewLoc();
        } else {
            setViewForAddLoc();
        }

    }

    void setViewForViewLoc() {
        tvCustomerID.setEnabled(false);
        tvLocationID.setVisibility(View.VISIBLE);
        tvLocationID.setEnabled(false);
        spLocation.setVisibility(View.GONE);
        tvDescprition.setEnabled(false);
        tvBuilding.setEnabled(false);
        tvSite.setEnabled(false);
        tvCustomerID.setText("" + sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        tvLocationID.setText(location.getCode());
        tvDescprition.setText(location.getDescription());
        if (null!=location.getSite()) {
            tvSite.setText(location.getSite().getCode());
        }
        if (null!=location.getBuilding()) {
            tvBuilding.setText(location.getBuilding().getCode());
        }

    }


    void setViewForAddLoc() {
        tvLocationID.setVisibility(View.VISIBLE);
        tvLocationID.setText("");
        tvDescprition.setText("");
        tvSite.setText("");
        tvBuilding.setText("");
        spLocation.setVisibility(View.GONE);
        tvDescprition.setEnabled(true);
        tvLocationID.setEnabled(true);
        tvBuilding.setEnabled(true);
        tvSite.setEnabled(true);
        tvCustomerID.setEnabled(false);
        tvCustomerID.setText("" + sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));


    }

    private void initListeners() {
        spLocation.setOnItemSelectedListener(this);
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
                                DataManager.getInstance().getAssetCustomer(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                               // DataManager.getInstance().getLocationSite(tvSite.getText().toString().trim()),
                              //  DataManager.getInstance().getLocationBuilding(tvBuilding.getText().toString().trim()
                                new Site(tvSite.getText().toString().trim()) , new Building(tvBuilding.getText().toString().trim()));
                        DataManager.getInstance().addNewLocation(locations);
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
                            tvSite.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getSite().getCode());
                            tvBuilding.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getBuilding().getCode());
                            break;
                        }
                    }
                } else {
                    setViewForAddLoc();
                }
                //  }


            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
