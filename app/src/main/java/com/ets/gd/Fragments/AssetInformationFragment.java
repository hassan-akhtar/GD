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

import com.ets.gd.Activities.FireBugViewInformation.ViewAssetInformationActivity;
import com.ets.gd.R;
import com.ets.gd.Utils.DatePickerFragmentEditText;
import com.ets.gd.Utils.DatePickerFragmentTextView;

public class AssetInformationFragment extends Fragment implements Spinner.OnItemSelectedListener {

    Spinner spDeviceType, spManufacturer, spVendor, spAgent;
    View rootView;
    private EditText tvTagID, tvModel, tvSrNo, tvMfgDate;
    private TextInputLayout ltvTagID, lModel, lSrNo, lMfgDate;


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

        ltvTagID = (TextInputLayout) rootView.findViewById(R.id.ltvTagID);
        lModel = (TextInputLayout) rootView.findViewById(R.id.lModel);
        lSrNo = (TextInputLayout) rootView.findViewById(R.id.lSrNo);
        lMfgDate = (TextInputLayout) rootView.findViewById(R.id.lMfgDate);
        tvTagID = (EditText) rootView.findViewById(R.id.tvTagID);
        tvMfgDate = (EditText) rootView.findViewById(R.id.tvMfgDate);
        tvModel = (EditText) rootView.findViewById(R.id.tvModel);
        tvSrNo = (EditText) rootView.findViewById(R.id.tvSrNo);


    }

    private void initObj() {

        ViewAssetInformationActivity.currentFragment = new AssetInformationFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayAdapter<String> dataAdapterDeviceType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.deviceTypes));
        dataAdapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeviceType.setAdapter(dataAdapterDeviceType);

        ArrayAdapter<String> dataAdapterManufacturer = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.manufacturers));
        dataAdapterManufacturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spManufacturer.setAdapter(dataAdapterManufacturer);

        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vendors));
        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVendor.setAdapter(dataAdapterVendor);

        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.agents));
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAgent.setAdapter(dataAdapterAgent);

        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {

        tvTagID.setText("00382");
        tvModel.setText("Ans");
        tvSrNo.setText("An-2501");

        spDeviceType.setSelection(1);
        spManufacturer.setSelection(1);
        spVendor.setSelection(1);
        spAgent.setSelection(1);

    }


    void setViewForVAddAsset() {

        tvTagID.setText("");
        tvModel.setText("");
        tvSrNo.setText("");

        spDeviceType.setSelection(0);
        spManufacturer.setSelection(0);
        spVendor.setSelection(0);
        spAgent.setSelection(0);

    }


    private void initListeners() {
        spDeviceType.setOnItemSelectedListener(this);
        spManufacturer.setOnItemSelectedListener(this);
        spVendor.setOnItemSelectedListener(this);
        spAgent.setOnItemSelectedListener(this);
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
