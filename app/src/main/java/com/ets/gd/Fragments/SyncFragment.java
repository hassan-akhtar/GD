package com.ets.gd.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SyncFragment extends Fragment {

    View rootView;
    TextView tvLastSyncDate, tvLastSyncTime,tvSyncInProgress;

    public SyncFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sync, container, false);
        initViews();
        initObj();
        initListeners();
        return rootView;

    }

    private void initViews() {
        tvLastSyncDate = (TextView) rootView.findViewById(R.id.tvLastSyncDate);
        tvLastSyncTime = (TextView) rootView.findViewById(R.id.tvLastSyncTime);
        tvSyncInProgress  = (TextView) rootView.findViewById(R.id.tvSyncInProgress);
    }

    private void initObj() {
        BaseActivity.currentFragment = new SyncFragment();

        CommonActions.showProgressDialog(getActivity());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Sync Complete", Toast.LENGTH_LONG);
                tvSyncInProgress.setText("Sync Complete!");
                CommonActions.DismissesDialog();
                setCurrentDateAndTime();

            }
        }, 5000);


    }

    private void initListeners() {
    }

    void setCurrentDateAndTime() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());
        tvLastSyncDate.setText("" + formattedDate);
        String delegate = "hh:mm aaa";
        tvLastSyncTime.setText("" + DateFormat.format(delegate, Calendar.getInstance().getTime()));


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
