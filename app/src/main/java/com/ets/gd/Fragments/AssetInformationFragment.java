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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;

public class AssetInformationFragment extends Fragment implements Spinner.OnItemSelectedListener {

    Spinner spDeviceType, spManufacturer, spMfgDate, spVendor, spAgent;
    View rootView;
    private EditText tvTagID, tvModel, tvSrNo;
    private TextInputLayout ltvTagID, lModel, lSrNo;

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
        spMfgDate = (Spinner) rootView.findViewById(R.id.spMfgDate);
        spVendor = (Spinner) rootView.findViewById(R.id.spVendor);
        spAgent = (Spinner) rootView.findViewById(R.id.spAgent);

        ltvTagID = (TextInputLayout) rootView.findViewById(R.id.ltvTagID);
        lModel = (TextInputLayout) rootView.findViewById(R.id.lModel);
        lSrNo = (TextInputLayout) rootView.findViewById(R.id.lSrNo);
        tvTagID = (EditText) rootView.findViewById(R.id.tvTagID);
        tvModel = (EditText) rootView.findViewById(R.id.tvModel);
        tvSrNo = (EditText) rootView.findViewById(R.id.tvSrNo);


        tvTagID.setText("00382");
        tvModel.setText("Ans");
        tvSrNo.setText("An-2501");


    }

    private void initObj() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayAdapter<String> dataAdapterDeviceType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.deviceTypes));
        dataAdapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDeviceType.setAdapter(dataAdapterDeviceType);

        ArrayAdapter<String> dataAdapterManufacturer = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.manufacturers));
        dataAdapterManufacturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spManufacturer.setAdapter(dataAdapterManufacturer);

        ArrayAdapter<String> dataAdapterMfgDate = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.manufacturers));
        dataAdapterMfgDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMfgDate.setAdapter(dataAdapterMfgDate);

        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vendors));
        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVendor.setAdapter(dataAdapterVendor);

        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.agents));
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAgent.setAdapter(dataAdapterAgent);

        spDeviceType.setSelection(1);
        spManufacturer.setSelection(1);
        spMfgDate.setSelection(1);
        spVendor.setSelection(1);
        spAgent.setSelection(1);
    }

    private void initListeners() {
        spDeviceType.setOnItemSelectedListener(this);
        spManufacturer.setOnItemSelectedListener(this);
        spMfgDate.setOnItemSelectedListener(this);
        spVendor.setOnItemSelectedListener(this);
        spAgent.setOnItemSelectedListener(this);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btnLogin: {

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
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;

            case R.id.spManufacturer: {
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;


            case R.id.spMfgDate: {
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;


            case R.id.spVendor: {
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                }

            }
            break;


            case R.id.spAgent: {
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

    void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }
}
