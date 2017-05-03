package com.ets.gd.Fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Asset;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyInspectionDates;
import com.ets.gd.R;
import com.ets.gd.Utils.DatePickerFragmentTextView;

import io.realm.RealmList;


public class InspectionDatesFragment extends Fragment {

    View rootView;
    RelativeLayout rlDaily, rlWeekly, rlMonthly, rlQuarterly, rlSemiAnnual, rlAnnual, rlFiveYears, rlSixYears, rlTenYears, rlTwelveYears;
    public static TextView tvDaily, tvWeekly, tvMonthly, tvQuarterly, tvSemiAnnual, tvAnnual, tvFiveYears, tvSixYears, tvTenYears, tvTwelveYears;
    public static int viewID = 0;
    FireBugEquipment fireBugEquipment;
    RealmList<MyInspectionDates> InspectionDates;

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
        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {

        fireBugEquipment = DataManager.getInstance().getEquipment(AssetInformationFragment.tvTagID.getText().toString());
        if (null != fireBugEquipment) {
            InspectionDates = fireBugEquipment.getInspectionDates();
            if (null != InspectionDates && 0!=InspectionDates.size() ) {

                if(null!=InspectionDates.get(0).getDueDate()){
                    String[] separated = InspectionDates.get(0).getDueDate().split("T");
                    tvDaily.setText("" + separated[0]);
                }else{
                    tvDaily.setText("YY-MM-DD");
                }

                if(null!=InspectionDates.get(1).getDueDate()){
                    String[] separated = InspectionDates.get(1).getDueDate().split("T");
                    tvWeekly.setText("" + separated[0]);
                }else{
                    tvWeekly.setText("YY-MM-DD");
                }

                if(null!=InspectionDates.get(2).getDueDate()){
                    String[] separated = InspectionDates.get(2).getDueDate().split("T");
                    tvMonthly.setText("" + separated[0]);
                }else{
                    tvMonthly.setText("YY-MM-DD");
                }


                if(null!=InspectionDates.get(3).getDueDate()){
                    String[] separated = InspectionDates.get(3).getDueDate().split("T");
                    tvQuarterly.setText("" + separated[0]);
                }else{
                    tvQuarterly.setText("YY-MM-DD");
                }


                if(null!=InspectionDates.get(4).getDueDate()){
                    String[] separated = InspectionDates.get(4).getDueDate().split("T");
                    tvSemiAnnual.setText("" + separated[0]);
                }else{
                    tvSemiAnnual.setText("YY-MM-DD");
                }


                if(null!=InspectionDates.get(5).getDueDate()){
                    String[] separated = InspectionDates.get(5).getDueDate().split("T");
                    tvAnnual.setText("" + separated[0]);
                }else{
                    tvAnnual.setText("YY-MM-DD");
                }

                if(null!=InspectionDates.get(6).getDueDate()){
                    String[] separated = InspectionDates.get(6).getDueDate().split("T");
                    tvFiveYears.setText("" + separated[0]);
                }else{
                    tvFiveYears.setText("YY-MM-DD");
                }


                if(null!=InspectionDates.get(7).getDueDate()){
                    String[] separated = InspectionDates.get(7).getDueDate().split("T");
                    tvSixYears.setText("" + separated[0]);
                }else{
                    tvSixYears.setText("YY-MM-DD");
                }


                if(null!=InspectionDates.get(8).getDueDate()){
                    String[] separated = InspectionDates.get(8).getDueDate().split("T");
                    tvTenYears.setText("" + separated[0]);
                }else{
                    tvTenYears.setText("YY-MM-DD");
                }

                if(null!=InspectionDates.get(9).getDueDate()){
                    String[] separated = InspectionDates.get(9).getDueDate().split("T");
                    tvTwelveYears.setText("" + separated[0]);
                }else{
                    tvTwelveYears.setText("YY-MM-DD");
                }



            }
        }

    }


    void setViewForVAddAsset() {

        tvDaily.setText("YY-MM-DD");
        tvWeekly.setText("YY-MM-DD");
        tvMonthly.setText("YY-MM-DD");
        tvQuarterly.setText("YY-MM-DD");
        tvSemiAnnual.setText("YY-MM-DD");
        tvAnnual.setText("YY-MM-DD");
        tvFiveYears.setText("YY-MM-DD");
        tvSixYears.setText("YY-MM-DD");
        tvTenYears.setText("YY-MM-DD");
        tvTwelveYears.setText("YY-MM-DD");

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
