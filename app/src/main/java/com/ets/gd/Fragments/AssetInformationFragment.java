package com.ets.gd.Fragments;


import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.DatePickerFragmentEditText;
import com.ets.gd.Utils.SharedPreferencesManager;

public class AssetInformationFragment extends Fragment implements Spinner.OnItemSelectedListener {

    public static Spinner spDeviceType, spManufacturer, spVendor, spAgent, spModel;
    View rootView;
    //Asset asset;
    public static EditText tvTagID, tvSrNo, tvMfgDate;
    private TextInputLayout ltvTagID, lModel, lSrNo, lMfgDate;
    FireBugEquipment fireBugEquipment;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;
    SharedPreferencesManager sharedPreferencesManager;

    public static int posDeviceType = 0, posManufacturer = 0, posVendor = 0, posAgent = 0, posModel = 0;


    public AssetInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_asset_information, container, false);


        initViews();
        initObj();
        initListeners();
        return rootView;

    }

    private void initViews() {
        spDeviceType = (Spinner) rootView.findViewById(R.id.spDeviceType);
        spManufacturer = (Spinner) rootView.findViewById(R.id.spManufacturer);
        spVendor = (Spinner) rootView.findViewById(R.id.spVendor);
        spAgent = (Spinner) rootView.findViewById(R.id.spAgent);
        spModel = (Spinner) rootView.findViewById(R.id.spModel);

        ltvTagID = (TextInputLayout) rootView.findViewById(R.id.ltvTagID);
        lSrNo = (TextInputLayout) rootView.findViewById(R.id.lSrNo);
        lMfgDate = (TextInputLayout) rootView.findViewById(R.id.lMfgDate);
        tvTagID = (EditText) rootView.findViewById(R.id.tvTagID);
        tvMfgDate = (EditText) rootView.findViewById(R.id.tvMfgDate);
        tvSrNo = (EditText) rootView.findViewById(R.id.tvSrNo);


    }

    private void initObj() {
        // asset = ((ViewAssetInformationActivity) getActivity()).getAsset();
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        if (!"addAsset".equals(getActivity().getIntent().getStringExtra("action"))) {
            fireBugEquipment = ((ViewAssetInformationActivity) getActivity()).getEquipment();
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        }else{
            realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        }


        int sizeDeviceType = realmSyncGetResponseDTO.getLstDeviceType().size()+1;
        int sizeManufacturers= realmSyncGetResponseDTO.getLstManufacturers().size()+1;
        int sizeModels= realmSyncGetResponseDTO.getLstModels().size()+1;
        int sizeVendors = realmSyncGetResponseDTO.getLstVendorCodes().size()+1;
        int sizeAgents = realmSyncGetResponseDTO.getLstAgentTypes().size()+1;

        String[] deviceTypes = new String[sizeDeviceType];
        String[] manufacturers  = new String[sizeManufacturers];
        String[] models = new String[sizeModels];
        String[] vendors = new String[sizeVendors];
        String[] agents = new String[sizeAgents];



        for (int i = 0; i < realmSyncGetResponseDTO.getLstDeviceType().size(); i++) {
            deviceTypes[i+1] = realmSyncGetResponseDTO.getLstDeviceType().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstManufacturers().size(); i++) {
            manufacturers[i+1] = realmSyncGetResponseDTO.getLstManufacturers().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstModels().size(); i++) {
            models[i+1] = realmSyncGetResponseDTO.getLstModels().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstVendorCodes().size(); i++) {
            vendors[i+1] = realmSyncGetResponseDTO.getLstVendorCodes().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstAgentTypes().size(); i++) {
            agents[i+1] = realmSyncGetResponseDTO.getLstAgentTypes().get(i).getCode();
        }

        deviceTypes[0] ="Please select a device type";
        manufacturers[0] ="Please select a manufacturer";
        models[0] = "Please select a model";
        vendors[0] ="Please select a vendor";
        agents[0] = "Please select a agent";



        ViewAssetInformationActivity.currentFragment = new AssetInformationFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayAdapter<String> dataAdapterDeviceType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, deviceTypes);
        dataAdapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeviceType.setAdapter(dataAdapterDeviceType);

        ArrayAdapter<String> dataAdapterManufacturer = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,manufacturers);
        dataAdapterManufacturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spManufacturer.setAdapter(dataAdapterManufacturer);


        ArrayAdapter<String> dataAdapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, models);
        dataAdapterModel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModel.setAdapter(dataAdapterModel);


        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, vendors);
        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVendor.setAdapter(dataAdapterVendor);

        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, agents);
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAgent.setAdapter(dataAdapterAgent);

        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {

        tvTagID.setText("" + fireBugEquipment.getCode());
        tvTagID.setEnabled(false);
        if (null != fireBugEquipment.getSerialNo()) {
            tvSrNo.setText(fireBugEquipment.getSerialNo());
        }

        if(null!=fireBugEquipment.getManufacturerDate()){
            String[] separated =fireBugEquipment.getManufacturerDate().split("T");
            tvMfgDate.setText("" + separated[0]);
        }else{
            tvMfgDate.setText("YY-MM-DD");
        }


        for (int i = 0; i < realmSyncGetResponseDTO.getLstDeviceType().size(); i++) {
            if (fireBugEquipment.getDeviceType().getCode().toLowerCase().equals(spDeviceType.getItemAtPosition(i).toString().toLowerCase())) {
                spDeviceType.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstManufacturers().size(); i++) {
            if (fireBugEquipment.getManufacturers().getCode().toLowerCase().equals(spManufacturer.getItemAtPosition(i).toString().toLowerCase())) {
                spManufacturer.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstModels().size(); i++) {
            if (fireBugEquipment.getModel().getCode().toLowerCase().equals(spModel.getItemAtPosition(i).toString().toLowerCase())) {
                spModel.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstVendorCodes().size(); i++) {
            if (fireBugEquipment.getVendorCode().getCode().toLowerCase().equals(spVendor.getItemAtPosition(i).toString().toLowerCase())) {
                spVendor.setSelection(i);
                break;
            }
        }

        for (int i = 1; i < realmSyncGetResponseDTO.getLstAgentTypes().size(); i++) {
            if (fireBugEquipment.getAgentType().getCode().toLowerCase().equals(spAgent.getItemAtPosition(i).toString().toLowerCase())) {
                spAgent.setSelection(i);
                break;
            }
        }

    }


    void setViewForVAddAsset() {
        tvTagID.setText("");
        tvTagID.setEnabled(true);
        tvSrNo.setText("");
        tvMfgDate.setText("YY-MM-DD");
        spDeviceType.setSelection(0);
        spManufacturer.setSelection(0);
        spVendor.setSelection(0);
        spAgent.setSelection(0);
        spModel.setSelection(0);

    }


    private void initListeners() {
        spDeviceType.setOnItemSelectedListener(this);
        spManufacturer.setOnItemSelectedListener(this);
        spVendor.setOnItemSelectedListener(this);
        spAgent.setOnItemSelectedListener(this);
        spModel.setOnItemSelectedListener(this);
        tvMfgDate.setOnClickListener(mGlobal_OnClickListener);

        tvMfgDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    hideKeyboard();
                    InspectionDatesFragment.viewID = v.getId();
                    DialogFragment newFragment = new DatePickerFragmentEditText();
                    newFragment.show(getActivity().getFragmentManager(), "Date Picker");
                } else {

                }


            }
        });

        spDeviceType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });

    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                tvTagID.getWindowToken(), 0);
    }
    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.tvMfgDate: {
                    hideKeyboard();
                    InspectionDatesFragment.viewID = v.getId();
                    DialogFragment newFragment = new DatePickerFragmentEditText();
                    newFragment.show(getActivity().getFragmentManager(), "Date Picker");
                    break;
                }
            }
        }

    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spDeviceType: {
                posDeviceType = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            break;

            case R.id.spManufacturer: {
                posManufacturer = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            break;

            case R.id.spModel: {
                posModel = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            break;


            case R.id.spVendor: {
                posVendor = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            break;


            case R.id.spAgent: {
                posAgent = position;
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

    void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
