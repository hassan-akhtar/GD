package com.ets.gd.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Activities.FireBug.ViewInformation.ViewLocationInformationActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;


public class AssetLocationFragment extends Fragment implements Spinner.OnItemSelectedListener {


    public static Spinner spLocation;
    View rootView;
    public static EditText tvSite, tvDescprition, tvBuilding,tvCustomerID;
    private TextInputLayout lLocationID, lDescprition;
    // Asset asset;
    SharedPreferencesManager sharedPreferencesManager;
    public static int posLoc = 0;
    FireBugEquipment fireBugEquipment;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;

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
        tvCustomerID = (EditText) rootView.findViewById(R.id.tvCustomerID);
        spLocation = (Spinner) rootView.findViewById(R.id.spLocation);
        tvSite = (EditText) rootView.findViewById(R.id.tvSite);
        lLocationID = (TextInputLayout) rootView.findViewById(R.id.lLocationID);
        lDescprition = (TextInputLayout) rootView.findViewById(R.id.lDescprition);
        tvBuilding = (EditText) rootView.findViewById(R.id.tvBuilding);
        tvDescprition = (EditText) rootView.findViewById(R.id.tvDescprition);


    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        //asset = ((ViewAssetInformationActivity) getActivity()).getAsset();
        if (!"addAsset".equals(getActivity().getIntent().getStringExtra("action"))) {
            fireBugEquipment = ((ViewAssetInformationActivity) getActivity()).getEquipment();
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(fireBugEquipment.getCustomer().getID());
        }else{
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        }

        int size = realmSyncGetResponseDTO.getLstLocations().size()+ 1 ;
        String[] locations = new String[size];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            locations[i+1] = realmSyncGetResponseDTO.getLstLocations().get(i).getCode();
        }
        locations[0] = "Please select a location";
        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locations);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(dataAdapterAgent);

        tvCustomerID.setEnabled(false);
        tvCustomerID.setText(""+sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {
        spLocation.setEnabled(false);
        for (int i = 0; i < realmSyncGetResponseDTO.getLstLocations().size(); i++) {
            if (fireBugEquipment.getLocation().getCode().toLowerCase().equals(spLocation.getItemAtPosition(i).toString().toLowerCase())) {
                spLocation.setSelection(i);
                tvDescprition.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getDescription());
                tvSite.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getSite().getCode());
                tvBuilding.setText(realmSyncGetResponseDTO.getLstLocations().get(i).getBuilding().getCode());
                break;
            }
        }


    }


    void setViewForVAddAsset() {
        spLocation.setEnabled(true);
        spLocation.setSelection(0);
        tvDescprition.setText("No location selected");
        tvSite.setText("No location selected");
        tvBuilding.setText("No location selected");


    }

    private void initListeners() {
        spLocation.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spLocation: {
                posLoc = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
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
                }else{
                    setViewForVAddAsset();
                }
//
//                tvDescprition.setText(realmSyncGetResponseDTO.getLstLocations().get(position).getDescription());
//                tvSite.setText(realmSyncGetResponseDTO.getLstLocations().get(position).getSite().getCode());
//                tvBuilding.setText(realmSyncGetResponseDTO.getLstLocations().get(position).getBuilding().getCode());
                try {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
