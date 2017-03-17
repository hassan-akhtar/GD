package com.ets.gd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ets.gd.Activities.BaseActivity;
import com.ets.gd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolhawkDashboardFragment extends Fragment {

    View rootView;

    public ToolhawkDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_toolhawk_dashboard, container, false);
        initViews();
        initObj();
        initListeners();

        return rootView;

    }

    private void initViews() {
    }

    private void initObj() {
        BaseActivity.currentFragment = new ToolhawkDashboardFragment();
    }

    private void initListeners() {
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


}
