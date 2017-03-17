package com.ets.gd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

public class ScanFragment extends Fragment {

    View rootView;

    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_scan, container, false);
        initViews();
        initObj();
        initListeners();
        return rootView;


    }


    private void doSync() {
    }

    private void initViews() {

    }

    private void initObj() {

    }

    private void initListeners() {

    }


}
