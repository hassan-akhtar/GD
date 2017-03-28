package com.ets.gd.Fragments;


import android.os.AsyncTask;
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
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SyncFragment extends Fragment {

    View rootView;
    TextView tvLastSyncDate, tvLastSyncTime, tvSyncInProgress;
    SharedPreferencesManager sharedPreferencesManager;
    String lastSyncDate, lastSyncTime;

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
        tvSyncInProgress = (TextView) rootView.findViewById(R.id.tvSyncInProgress);
    }

    private void initObj() {
        BaseActivity.currentFragment = new SyncFragment();
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());

        lastSyncDate = sharedPreferencesManager.getString(SharedPreferencesManager.LAST_SYNC_DATE);
        lastSyncTime = sharedPreferencesManager.getString(SharedPreferencesManager.LAST_SYNC_TIME);

        if ("Not Found".equals(lastSyncDate)) {
            tvLastSyncDate.setText("--/--/--");
        } else {
            tvLastSyncDate.setText(lastSyncDate);
        }

        if ("Not Found".equals(lastSyncTime)) {
            tvLastSyncTime.setText("--:--");
        } else {
            tvLastSyncTime.setText(lastSyncTime);
        }

        CommonActions.showProgressDialog(getActivity());
        DataManager.getInstance().setupSyncData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Sync Complete", Toast.LENGTH_LONG);
                tvSyncInProgress.setText("Sync Complete!");
                CommonActions.DismissesDialog();
                setCurrentDateAndTime();

            }
        }, 4000);



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
        sharedPreferencesManager.setString(SharedPreferencesManager.LAST_SYNC_DATE, formattedDate);
        sharedPreferencesManager.setString(SharedPreferencesManager.LAST_SYNC_TIME,
                DateFormat.format(delegate, Calendar.getInstance().getTime()).toString());

    }


/*    private class GetSyncData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            DataManager.getInstance().setupSyncData();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), "Sync Complete", Toast.LENGTH_LONG);
            tvSyncInProgress.setText("Sync Complete!");
            CommonActions.DismissesDialog();
            setCurrentDateAndTime();
        }
    }*/

}
