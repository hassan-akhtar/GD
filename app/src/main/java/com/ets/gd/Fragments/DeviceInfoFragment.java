package com.ets.gd.Fragments;


import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.R;

public class DeviceInfoFragment extends Fragment {

    View rootView;
    TextView tvDeviceID;

    public DeviceInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_device_info, container, false);
        initViews();
        initObj();
        initListeners();
        return rootView;
    }

    private void initViews() {
        tvDeviceID = (TextView) rootView.findViewById(R.id.tvDeviceID);
        tvDeviceID.setText(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    private void initObj() {
    }

    private void initListeners() {
    }


}
