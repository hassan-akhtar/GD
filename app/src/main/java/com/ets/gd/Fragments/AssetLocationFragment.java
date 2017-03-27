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
import com.ets.gd.Models.Asset;
import com.ets.gd.R;


public class AssetLocationFragment extends Fragment implements Spinner.OnItemSelectedListener {


    Spinner spSite, spBuilding;
    View rootView;
    private EditText tvLocationID, tvDescprition;
    private TextInputLayout lLocationID, lDescprition;
    Asset asset;

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

        spSite = (Spinner) rootView.findViewById(R.id.spSite);
        spBuilding = (Spinner) rootView.findViewById(R.id.spBuilding);
        lLocationID = (TextInputLayout) rootView.findViewById(R.id.lLocationID);
        lDescprition = (TextInputLayout) rootView.findViewById(R.id.lDescprition);
        tvLocationID = (EditText) rootView.findViewById(R.id.tvLocationID);
        tvDescprition = (EditText) rootView.findViewById(R.id.tvDescprition);


    }

    private void initObj() {

        asset = ((ViewAssetInformationActivity) getActivity()).getAsset();
        ViewAssetInformationActivity.currentFragment = new AssetLocationFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vendors));
        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterVendor);

        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.agents));
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuilding.setAdapter(dataAdapterAgent);


        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {

        if (null!=asset.getLocation()) {
            if (null!=asset.getLocation().getLocationID()) {
                tvLocationID.setText(asset.getLocation().getLocationID());
            }
            if (null!=asset.getLocation().getDescription()) {
                tvDescprition.setText(asset.getLocation().getDescription());
            }


            for (int i = 0; i < getResources().getStringArray(R.array.vendors).length; i++) {
                if (asset.getLocation().getVendor().toLowerCase().equals(spSite.getItemAtPosition(i).toString().toLowerCase())) {
                    spSite.setSelection(i);
                }
            }

            for (int i = 0; i < getResources().getStringArray(R.array.agents).length; i++) {
                if (asset.getLocation().getAgent().toString().toLowerCase().equals(spBuilding.getItemAtPosition(i).toString().toLowerCase())) {
                    spBuilding.setSelection(i);
                }
            }
        }

    }


    void setViewForVAddAsset() {

        tvLocationID.setText("");
        tvDescprition.setText("");

        spSite.setSelection(0);
        spBuilding.setSelection(0);


    }

    private void initListeners() {
        spSite.setOnItemSelectedListener(this);
        spBuilding.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spSite: {

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

            case R.id.spBuilding: {
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
