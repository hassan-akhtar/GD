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
import android.widget.Toast;

import com.ets.gd.R;


public class AssetLocationFragment extends Fragment  implements Spinner.OnItemSelectedListener {


    Spinner spSite, spBuilding;
    View rootView;
    private EditText tvLocationID, tvDescprition;
    private TextInputLayout lLocationID, lDescprition;

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



        tvLocationID.setText("L00382");
        tvDescprition.setText("Prod");


    }

    private void initObj() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ArrayAdapter<String> dataAdapterVendor = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vendors));
        dataAdapterVendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSite.setAdapter(dataAdapterVendor);

        ArrayAdapter<String> dataAdapterAgent = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.agents));
        dataAdapterAgent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBuilding.setAdapter(dataAdapterAgent);

        spSite.setSelection(1);
        spBuilding.setSelection(1);
    }

    private void initListeners() {
        spSite.setOnItemSelectedListener(this);
        spBuilding.setOnItemSelectedListener(this);

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
