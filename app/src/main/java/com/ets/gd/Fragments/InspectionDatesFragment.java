package com.ets.gd.Fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ets.gd.R;
import com.ets.gd.Utils.DatePickerFragmentTextView;


public class InspectionDatesFragment extends Fragment {

    View rootView;
    RelativeLayout rlDaily, rlWeekly, rlMonthly, rlQuarterly, rlSemiAnnual, rlAnnual, rlFiveYears, rlSixYears, rlTenYears, rlTwelveYears;
    TextView tvDaily, tvWeekly, tvMonthly, tvQuarterly, tvSemiAnnual, tvAnnual, tvFiveYears, tvSixYears, tvTenYears, tvTwelveYears;
    public static int viewID = 0;

    public InspectionDatesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_inspection_dates, container, false);

        initViews();
        initObj();
        initListeners();

        return rootView;
    }

    private void initViews() {
        rlDaily = (RelativeLayout) rootView.findViewById(R.id.rlDaily);

        rlWeekly = (RelativeLayout) rootView.findViewById(R.id.rlWeekly);
        rlMonthly = (RelativeLayout) rootView.findViewById(R.id.rlMonthly);
        rlQuarterly = (RelativeLayout) rootView.findViewById(R.id.rlQuarterly);
        rlSemiAnnual = (RelativeLayout) rootView.findViewById(R.id.rlSemiAnnual);
        rlAnnual = (RelativeLayout) rootView.findViewById(R.id.rlAnnual);
        rlFiveYears = (RelativeLayout) rootView.findViewById(R.id.rlFiveYears);
        rlSixYears = (RelativeLayout) rootView.findViewById(R.id.rlSixYears);
        rlTenYears = (RelativeLayout) rootView.findViewById(R.id.rlTenYears);
        rlTwelveYears = (RelativeLayout) rootView.findViewById(R.id.rlTwelveYears);


        tvDaily = (TextView) rootView.findViewById(R.id.tvDaily);
        tvWeekly = (TextView) rootView.findViewById(R.id.tvWeekly);
        tvMonthly = (TextView) rootView.findViewById(R.id.tvMonthly);
        tvQuarterly = (TextView) rootView.findViewById(R.id.tvQuarterly);
        tvSemiAnnual = (TextView) rootView.findViewById(R.id.tvSemiAnnual);
        tvAnnual = (TextView) rootView.findViewById(R.id.tvAnnual);
        tvFiveYears = (TextView) rootView.findViewById(R.id.tvFiveYears);
        tvSixYears = (TextView) rootView.findViewById(R.id.tvSixYears);
        tvTenYears = (TextView) rootView.findViewById(R.id.tvTenYears);
        tvTwelveYears = (TextView) rootView.findViewById(R.id.tvTwelveYears);


    }

    private void initObj() {
    }

    private void initListeners() {

        rlDaily.setOnClickListener(mGlobal_OnClickListener);
        rlWeekly.setOnClickListener(mGlobal_OnClickListener);
        rlMonthly.setOnClickListener(mGlobal_OnClickListener);
        rlQuarterly.setOnClickListener(mGlobal_OnClickListener);
        rlSemiAnnual.setOnClickListener(mGlobal_OnClickListener);
        rlAnnual.setOnClickListener(mGlobal_OnClickListener);
        rlFiveYears.setOnClickListener(mGlobal_OnClickListener);
        rlSixYears.setOnClickListener(mGlobal_OnClickListener);
        rlTenYears.setOnClickListener(mGlobal_OnClickListener);
        rlTwelveYears.setOnClickListener(mGlobal_OnClickListener);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {

            switch (v.getId()) {
                case R.id.rlDaily: {
                    viewID = tvDaily.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlWeekly: {
                    viewID = tvWeekly.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlMonthly: {
                    viewID = tvMonthly.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlQuarterly: {
                    viewID = tvQuarterly.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlSemiAnnual: {
                    viewID = tvSemiAnnual.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlAnnual: {
                    viewID = tvAnnual.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlFiveYears: {
                    viewID = tvFiveYears.getId();
                    openDateDialoge();
                    break;
                }
                case R.id.rlSixYears: {
                    viewID = tvSixYears.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlTenYears: {
                    viewID = tvTenYears.getId();
                    openDateDialoge();
                    break;
                }

                case R.id.rlTwelveYears: {
                    viewID = tvTwelveYears.getId();
                    openDateDialoge();
                    break;
                }


            }
        }

    };

    void openDateDialoge() {
        DialogFragment newFragment = new DatePickerFragmentTextView();
        newFragment.show(getActivity().getFragmentManager(), "Date Picker");
    }
}
