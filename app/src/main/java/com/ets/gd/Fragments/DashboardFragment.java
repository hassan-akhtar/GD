package com.ets.gd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import org.greenrobot.eventbus.EventBus;


public class DashboardFragment extends Fragment {

    private View rootView;
    CommonActions ca;
    SharedPreferencesManager sharedPreferencesManager;
    RelativeLayout rlFireBugBody, rlFireBugHeader, rlToolHawkHeader, rlToolHawkBody, ivForwardArrowFb, ivForwardArrowTh;


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
        ivForwardArrowFb = (RelativeLayout) rootView.findViewById(R.id.ivForwardArrowFb);
        ivForwardArrowTh = (RelativeLayout) rootView.findViewById(R.id.ivForwardArrowTh);
        rlToolHawkHeader = (RelativeLayout) rootView.findViewById(R.id.rlToolHawkHeader);
        rlToolHawkBody = (RelativeLayout) rootView.findViewById(R.id.rlToolHawkBody);
    }

    private void initObj() {
        ca = new CommonActions(getActivity());
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        DataManager.getInstance().setupDataForApp();
    }

    private void initListeners() {
        rlFireBugHeader.setOnClickListener(mGlobal_OnClickListener);
        rlToolHawkHeader.setOnClickListener(mGlobal_OnClickListener);
        ivForwardArrowTh.setOnClickListener(mGlobal_OnClickListener);
        ivForwardArrowFb.setOnClickListener(mGlobal_OnClickListener);
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

                case R.id.ivForwardArrowTh: {
                    BaseActivity.refreshMainViewByNew(new ToolhawkDashboardFragment());
                    break;
                }

                case R.id.ivForwardArrowFb: {

                 //   if (DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).isServiceCompany()
                 //           && 0!=DataManager.getInstance().getAllCustomerList(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).size()) {
                        BaseActivity.refreshMainViewByNew(new CustomerFragment());
                //    } else {
                 //       BaseActivity.refreshMainViewByNew(new FirebugDashboardFragment());
                  //      EventBus.getDefault().post(sharedPreferencesManager.getString(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_CODE));

                  //  }
                    break;
                }
            }
        }

    };

}
