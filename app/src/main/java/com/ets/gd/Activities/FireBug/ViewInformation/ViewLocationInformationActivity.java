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
import com.ets.gd.Models.Location;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack, ivTick;
    Spinner spLocation;
    public static EditText tvSite, tvDescprition, tvBuilding,tvCustomerID;
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
        realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(Integer.parseInt(sharedPreferencesManager.getString(SharedPreferencesManager.MY_SYNC_CUSTOMER_ID)));

        String[] locations = new String[realmSyncGetResponseDTO.getLstLocations().size()];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            locations[i] = realmSyncGetResponseDTO.getLstLocations().get(i).getCode();
        }
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(ViewLocationInformationActivity.this, android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);

        actionType = getIntent().getStringExtra("action");
        if ("viewLoc".equals(actionType)) {
            setViewForViewLoc();
        } else {
            setViewForAddLoc();
        }

    }


    void setViewForViewLoc() {
        tvCustomerID.setEnabled(false);
        tvDescprition.setEnabled(false);
        tvBuilding.setEnabled(false);
        tvSite.setEnabled(false);
        tvCustomerID.setText(""+sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            if (location.getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                spLocation.setSelection(i);
                tvDescprition.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getDescription());
                tvSite.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getSite().getCode());
                tvBuilding.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getBuilding().getCode());
                break;
            }
        }
    }


    void setViewForAddLoc() {
        tvCustomerID.setEnabled(false);
        tvCustomerID.setText(""+sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        spLocation.setSelection(0);
        tvDescprition.setText("No location selected");
        tvSite.setText("No location selected");
        tvBuilding.setText("No location selected");


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
        if (0 == posLoc) {
            showToast("Please enter location ID");
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
                position = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (!"viewLoc".equals(actionType)) {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    }

                }

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
                }

            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
