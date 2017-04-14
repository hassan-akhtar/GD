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
import com.ets.gd.R;

public class ViewLocationInformationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    ImageView ivBack, ivTick;
    Spinner spLocation;
    public static EditText tvSite, tvDescprition, tvBuilding;
    Location location;
    private TextInputLayout lLocationID, lDescprition;
    public static String actionType, barCodeID;
    public static int posLoc = 0;
    FireBugEquipment fireBugEquipment;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;

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
        lLocationID = (TextInputLayout) findViewById(R.id.lLocationID);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        lDescprition = (TextInputLayout) findViewById(R.id.lDescprition);
        tvBuilding = (EditText) findViewById(R.id.tvBuilding);
        tvDescprition = (EditText) findViewById(R.id.tvDescprition);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        location = DataManager.getInstance().getLocation(barCodeID);

    }

    private void initObj() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //asset = ((ViewAssetInformationActivity) getActivity()).getAsset();
        fireBugEquipment = ((ViewAssetInformationActivity) getApplicationContext()).getEquipment();
        realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(fireBugEquipment.getCustomer().getID());

        String[] locations = new String[realmSyncGetResponseDTO.getLstLocations().size()];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            locations[i] = realmSyncGetResponseDTO.getLstLocations().get(i).getCode();
        }
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);

        actionType = getIntent().getStringExtra("action");
        if ("viewLoc".equals(actionType)) {
            ivTick.setVisibility(View.GONE);
            setViewForViewLoc();
        } else {
            ivTick.setVisibility(View.VISIBLE);
            setViewForAddLoc();
        }

    }


    void setViewForViewLoc() {

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            if (fireBugEquipment.getDeviceType().getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                spLocation.setSelection(i);
                tvDescprition.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getDescription());
                tvSite.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getSite().getCode());
                tvBuilding.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getBuilding().getCode());
                break;
            }
        }
    }


    void setViewForAddLoc() {

        spLocation.setSelection(0);
        tvDescprition.setText("");
        tvSite.setText("");
        tvBuilding.setText("");


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
         if (0==posLoc) {
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
