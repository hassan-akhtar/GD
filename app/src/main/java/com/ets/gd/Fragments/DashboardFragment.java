package com.ets.gd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ets.gd.FireBug.Customer.CustomerFragment;
import com.ets.gd.FireBug.Fragments.FirebugDashboardFragment;
import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.InspectionDue;
import com.ets.gd.NetworkLayer.ResponseDTOs.InspectionOverDue;
import com.ets.gd.NetworkLayer.ResponseDTOs.MaintenanceDue;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Fragments.ToolhawkDashboardFragment;
import com.ets.gd.ToolHawk.Fragments.ToolhawkDashboardFragmentNew;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import org.greenrobot.eventbus.EventBus;


public class DashboardFragment extends Fragment {

    private View rootView;
    CommonActions ca;
    SharedPreferencesManager sharedPreferencesManager;
    RelativeLayout rlFireBugBody, rlFireBugHeader, rlToolHawkHeader, rlToolHawkBody, ivForwardArrowFb, ivForwardArrowTh;
    TextView WeeklyOverdueValue, MonthlyOverdueValue, QuarterlyOverdueValue, SemiAnnualOverdueValue, AnnualOverdueValue, YearsOverdueValue, tvWeeklyDueValue, MonthlyDueValue, QuarterlyDueValue, SemiAnnualDueValue, AnnualDueValue, YearsDueValue;
    TextView thOverdueValue, thMaintdueValue, thCheckoutValue, thMaintOverdueValue;
    InspectionDue inspectionDue;
    InspectionOverDue inspectionOverDue;
    MaintenanceDue maintenanceDue;

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


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
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

        WeeklyOverdueValue = (TextView) rootView.findViewById(R.id.WeeklyOverdueValue);
        MonthlyOverdueValue = (TextView) rootView.findViewById(R.id.MonthlyOverdueValue);
        QuarterlyOverdueValue = (TextView) rootView.findViewById(R.id.QuarterlyOverdueValue);
        SemiAnnualOverdueValue = (TextView) rootView.findViewById(R.id.SemiAnnualOverdueValue);
        AnnualOverdueValue = (TextView) rootView.findViewById(R.id.AnnualOverdueValue);
        YearsOverdueValue = (TextView) rootView.findViewById(R.id.YearsOverdueValue);
        tvWeeklyDueValue = (TextView) rootView.findViewById(R.id.tvWeeklyDueValue);
        MonthlyDueValue = (TextView) rootView.findViewById(R.id.MonthlyDueValue);
        QuarterlyDueValue = (TextView) rootView.findViewById(R.id.QuarterlyDueValue);
        SemiAnnualDueValue = (TextView) rootView.findViewById(R.id.SemiAnnualDueValue);
        AnnualDueValue = (TextView) rootView.findViewById(R.id.AnnualDueValue);
        YearsDueValue = (TextView) rootView.findViewById(R.id.YearsDueValue);


        thOverdueValue = (TextView) rootView.findViewById(R.id.thOverdueValue);
        thMaintdueValue = (TextView) rootView.findViewById(R.id.thMaintdueValue);
        thCheckoutValue = (TextView) rootView.findViewById(R.id.thCheckoutValue);
        thMaintOverdueValue = (TextView) rootView.findViewById(R.id.thMaintOverdueValue);


        ///fb overdue
        inspectionOverDue = DataManager.getInstance().getInspectionOverDue();
        if (null != inspectionOverDue) {
            String weekly, monthly, quarterly, semiAnnual, annual, FiveSixAndTwelveDue;
            weekly = "" + inspectionOverDue.getWeekly();
            monthly = "" + inspectionOverDue.getMonthly();
            quarterly = "" + inspectionOverDue.getQuarterly();
            semiAnnual = "" + inspectionOverDue.getSemiAnnual();
            annual = "" + inspectionOverDue.getAnnual();
            int fiveSixTwelweSum = inspectionOverDue.getFiveYears() + inspectionOverDue.getSixYears() + inspectionOverDue.getTwelveYears() + inspectionOverDue.getTenYears();
            FiveSixAndTwelveDue = "" + fiveSixTwelweSum;

            if (weekly.length() == 1) {
                WeeklyOverdueValue.setText("0" + weekly);
            } else {
                WeeklyOverdueValue.setText(weekly);
            }


            if (monthly.length() == 1) {
                MonthlyOverdueValue.setText("0" + monthly);
            } else {
                MonthlyOverdueValue.setText(monthly);
            }

            if (quarterly.length() == 1) {
                QuarterlyOverdueValue.setText("0" + quarterly);
            } else {
                QuarterlyOverdueValue.setText(quarterly);
            }


            if (semiAnnual.length() == 1) {
                SemiAnnualOverdueValue.setText("0" + semiAnnual);
            } else {
                SemiAnnualOverdueValue.setText(semiAnnual);
            }


            if (annual.length() == 1) {
                AnnualOverdueValue.setText("0" + annual);
            } else {
                AnnualOverdueValue.setText(annual);
            }

            if (FiveSixAndTwelveDue.length() == 1) {
                YearsOverdueValue.setText("0" + FiveSixAndTwelveDue);
            } else {
                YearsOverdueValue.setText(FiveSixAndTwelveDue);
            }


        }

        ///fb due
        inspectionDue = DataManager.getInstance().getInspectionDue();

        if (null != inspectionDue) {
            String weekly, monthly, quarterly, semiAnnual, annual, FiveSixAndTwelveDue;
            weekly = "" + inspectionDue.getWeekly();
            monthly = "" + inspectionDue.getMonthly();
            quarterly = "" + inspectionDue.getQuarterly();
            semiAnnual = "" + inspectionDue.getSemiAnnual();
            annual = "" + inspectionDue.getAnnual();
            int fiveSixTwelweSum = inspectionDue.getFiveYears() + inspectionDue.getSixYears() + inspectionDue.getTwelveYears() + inspectionDue.getTenYears();
            FiveSixAndTwelveDue = "" + fiveSixTwelweSum;

            if (weekly.length() == 1) {
                tvWeeklyDueValue.setText("0" + weekly);
            } else {
                tvWeeklyDueValue.setText(weekly);
            }


            if (monthly.length() == 1) {
                MonthlyDueValue.setText("0" + monthly);
            } else {
                MonthlyDueValue.setText(monthly);
            }

            if (quarterly.length() == 1) {
                QuarterlyDueValue.setText("0" + quarterly);
            } else {
                QuarterlyDueValue.setText(quarterly);
            }


            if (semiAnnual.length() == 1) {
                SemiAnnualDueValue.setText("0" + semiAnnual);
            } else {
                SemiAnnualDueValue.setText(semiAnnual);
            }


            if (annual.length() == 1) {
                AnnualDueValue.setText("0" + annual);
            } else {
                AnnualDueValue.setText(annual);
            }

            if (FiveSixAndTwelveDue.length() == 1) {
                YearsDueValue.setText("0" + FiveSixAndTwelveDue);
            } else {
                YearsDueValue.setText(FiveSixAndTwelveDue);
            }

        }

        maintenanceDue =  DataManager.getInstance().getDashboardStatsToolhawk();

        if (null != maintenanceDue) {
            String totalOverdue, totalCheckout, maintenanceOverdue, maintDue;
            totalOverdue = "" + maintenanceDue.getTotalOverDue();
            totalCheckout = "" + maintenanceDue.getTotalCheckOut();
            maintenanceOverdue = "" + maintenanceDue.getMaintenanceOverDue();
            maintDue = "" + maintenanceDue.getMaintenanceDue();

            if (totalOverdue.length() == 1) {
                thOverdueValue.setText("0" + totalOverdue);
            } else {
                thOverdueValue.setText(totalOverdue);
            }


            if (totalCheckout.length() == 1) {
                thCheckoutValue.setText("0" + totalCheckout);
            } else {
                thCheckoutValue.setText(totalCheckout);
            }

            if (maintenanceOverdue.length() == 1) {
                thMaintOverdueValue.setText("0" + maintenanceOverdue);
            } else {
                thMaintOverdueValue.setText(maintenanceOverdue);
            }

            if (maintDue.length() == 1) {
                thMaintdueValue.setText("0" + maintDue);
            } else {
                thMaintdueValue.setText(maintDue);
            }


        }

    }

    private void initObj() {
        ca = new CommonActions(getActivity());
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
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
                    //BaseActivity.refreshMainViewByNew(new ToolhawkDashboardFragment());
                    BaseActivity.refreshMainViewByNew(new ToolhawkDashboardFragmentNew());
                    break;
                }

                case R.id.ivForwardArrowFb: {
                    if (DataManager.getInstance().isServiceCompany()) {
                        BaseActivity.refreshMainViewByNew(new CustomerFragment());
                    } else {
                        BaseActivity.refreshMainViewByNew(new FirebugDashboardFragment());
                        if (null != DataManager.getInstance().getParentCompany()) {
                            EventBus.getDefault().post(DataManager.getInstance().getParentCompany().getCode());
                        }

                    }
                    break;
                }
            }
        }

    };

}
