package com.ets.gd.Fragments;


import android.app.DialogFragment;
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
import android.widget.Toast;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.DatePickerFragmentEditText;

public class AssetInformationFragment extends Fragment implements Spinner.OnItemSelectedListener {

    public static Spinner spDeviceType, spManufacturer, spVendor, spAgent, spModel;
    View rootView;
    //Asset asset;
    public static EditText tvTagID, tvSrNo, tvMfgDate;
    private TextInputLayout ltvTagID, lModel, lSrNo, lMfgDate;
    FireBugEquipment fireBugEquipment;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;

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
        realmSyncGetResponseDTO = DataManager.getInstance().getSyncGetResponseDTO(fireBugEquipment.getCustomer().getID());
        fireBugEquipment = ((ViewAssetInformationActivity) getActivity()).getEquipment();

        String[] deviceTypes = new String[realmSyncGetResponseDTO.getLstDeviceType().size()];
        String[] manufacturers  = new String[realmSyncGetResponseDTO.getLstManufacturers().size()];
        String[] models = new String[realmSyncGetResponseDTO.getLstModels().size()];
        String[] vendors = new String[realmSyncGetResponseDTO.getLstVendorCodes().size()];
        String[] agents = new String[realmSyncGetResponseDTO.getLstAgentTypes().size()];

        for (int i = 0; i < realmSyncGetResponseDTO.getLstDeviceType().size(); i++) {
            deviceTypes[i] = realmSyncGetResponseDTO.getLstDeviceType().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstManufacturers().size(); i++) {
            manufacturers[i] = realmSyncGetResponseDTO.getLstManufacturers().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstModels().size(); i++) {
            models[i] = realmSyncGetResponseDTO.getLstModels().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstVendorCodes().size(); i++) {
            vendors[i] = realmSyncGetResponseDTO.getLstVendorCodes().get(i).getCode();
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstAgentTypes().size(); i++) {
            agents[i] = realmSyncGetResponseDTO.getLstAgentTypes().get(i).getCode();
        }




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

        tvTagID.setText("" + fireBugEquipment.getID());
        tvTagID.setEnabled(false);
        if (null != fireBugEquipment.getSerialNo()) {
            tvSrNo.setText(fireBugEquipment.getSerialNo());
        }
        tvMfgDate.setText(fireBugEquipment.getManufacturerDate());


        for (int i = 0; i < realmSyncGetResponseDTO.getLstDeviceType().size(); i++) {
            if (fireBugEquipment.getDeviceType().getCode().toLowerCase().equals(spDeviceType.getItemAtPosition(i).toString().toLowerCase())) {
                spDeviceType.setSelection(i);
            }
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstManufacturers().size(); i++) {
            if (fireBugEquipment.getManufacturers().getCode().toLowerCase().equals(spManufacturer.getItemAtPosition(i).toString().toLowerCase())) {
                spManufacturer.setSelection(i);
            }
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstModels().size(); i++) {
            if (fireBugEquipment.getModel().getCode().toLowerCase().equals(spModel.getItemAtPosition(i).toString().toLowerCase())) {
                spModel.setSelection(i);
            }
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstVendorCodes().size(); i++) {
            if (fireBugEquipment.getVendorCode().getCode().toLowerCase().equals(spVendor.getItemAtPosition(i).toString().toLowerCase())) {
                spVendor.setSelection(i);
            }
        }

        for (int i = 0; i < realmSyncGetResponseDTO.getLstAgentTypes().size(); i++) {
            if (fireBugEquipment.getAgentType().getCode().toLowerCase().equals(spDeviceType.getItemAtPosition(i).toString().toLowerCase())) {
                spAgent.setSelection(i);
            }
        }

    }


    void setViewForVAddAsset() {

        tvTagID.setText("");
        tvTagID.setEnabled(true);
        tvSrNo.setText("");
        tvMfgDate.setText("");
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

    }

    public void hideKeyboard() {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
