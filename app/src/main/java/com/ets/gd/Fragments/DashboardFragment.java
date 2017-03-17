package com.ets.gd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;


public class DashboardFragment extends Fragment {

    private View rootView;
    CommonActions ca;
    SharedPreferencesManager sharedPreferencesManager;
    RelativeLayout rlFireBugBody, rlFireBugHeader, rlToolHawkHeader, rlToolHawkBody;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViews();
        initObj();
        initListeners();

        if (sharedPreferencesManager.getBoolean(SharedPreferencesManager.SYNC_STATE)) {
            doSync();
        }
        return rootView;


    }


    private void doSync() {
    }

    private void initViews() {
        rlFireBugBody = (RelativeLayout) rootView.findViewById(R.id.rlFireBugBody);
        rlFireBugHeader = (RelativeLayout) rootView.findViewById(R.id.rlFireBugHeader);
        rlToolHawkHeader = (RelativeLayout) rootView.findViewById(R.id.rlToolHawkHeader);
        rlToolHawkBody = (RelativeLayout) rootView.findViewById(R.id.rlToolHawkBody);
    }

    private void initObj() {
        ca = new CommonActions(getActivity());
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
    }

    private void initListeners() {
        rlFireBugHeader.setOnClickListener(mGlobal_OnClickListener);
        rlToolHawkHeader.setOnClickListener(mGlobal_OnClickListener);
    }


    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.rlFireBugHeader: {
                    if (rlToolHawkBody.getVisibility() == View.VISIBLE) {
                        rlToolHawkBody.setVisibility(View.GONE);
                    }

                    if (rlFireBugBody.getVisibility() == View.VISIBLE) {
                        rlFireBugBody.setVisibility(View.GONE);
                    } else {
                        rlFireBugBody.setVisibility(View.VISIBLE);
                    }

                    break;
                }

                case R.id.rlToolHawkHeader: {
                    if (rlFireBugBody.getVisibility() == View.VISIBLE) {
                        rlFireBugBody.setVisibility(View.GONE);
                    }
                    if (rlToolHawkBody.getVisibility() == View.VISIBLE) {
                        rlToolHawkBody.setVisibility(View.GONE);
                    } else {
                        rlToolHawkBody.setVisibility(View.VISIBLE);
                    }

                    break;
                }
            }
        }

    };

}
