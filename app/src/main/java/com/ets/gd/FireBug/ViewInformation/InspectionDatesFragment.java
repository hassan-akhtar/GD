package com.ets.gd.FireBug.ViewInformation;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ets.gd.DataManager.DataManager;
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
            if (null != InspectionDates && 0 != InspectionDates.size()) {

                boolean containsSlash = false;

                for(int i = 0 ;i>InspectionDates.size();i++){
                    if (null != InspectionDates.get(i).getDueDate()) {
                        if(InspectionDates.get(i).getDueDate().contains("/")){
                            containsSlash=true;
                            break;
                        }
                    }
                }

                if (!containsSlash) {
                    try {
                        if (null != InspectionDates.get(0).getDueDate()) {
                            String[] separated = InspectionDates.get(0).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvDaily.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else if (null != InspectionDates.get(0).getDueDate() && InspectionDates.get(0).getDueDate().contains("/")) {
                            tvDaily.setText("" + InspectionDates.get(0).getDueDate());
                        } else {
                            tvDaily.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvDaily.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }

                    try {
                        if (null != InspectionDates.get(1).getDueDate()) {
                            String[] separated = InspectionDates.get(1).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvWeekly.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvWeekly.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvWeekly.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }

                    try {
                        if (null != InspectionDates.get(2).getDueDate()) {
                            String[] separated = InspectionDates.get(2).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvMonthly.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvMonthly.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvMonthly.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }


                    try {
                        if (null != InspectionDates.get(3).getDueDate()) {
                            String[] separated = InspectionDates.get(3).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvQuarterly.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvQuarterly.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvQuarterly.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }


                    try {
                        if (null != InspectionDates.get(4).getDueDate()) {
                            String[] separated = InspectionDates.get(4).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvSemiAnnual.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvSemiAnnual.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvSemiAnnual.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }


                    try {
                        if (null != InspectionDates.get(5).getDueDate()) {
                            String[] separated = InspectionDates.get(5).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvAnnual.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvAnnual.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvAnnual.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }

                    try {
                        if (null != InspectionDates.get(6).getDueDate()) {
                            String[] separated = InspectionDates.get(6).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvFiveYears.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvFiveYears.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvFiveYears.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }


                    try {
                        if (null != InspectionDates.get(7).getDueDate()) {
                            String[] separated = InspectionDates.get(7).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvSixYears.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvSixYears.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvSixYears.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }


                    try {
                        if (null != InspectionDates.get(8).getDueDate()) {
                            String[] separated = InspectionDates.get(8).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvTenYears.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvTenYears.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvTenYears.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }

                    try {
                        if (null != InspectionDates.get(9).getDueDate()) {
                            String[] separated = InspectionDates.get(9).getDueDate().split("T");
                            String[] newFormat = separated[0].split("-");
                            tvTwelveYears.setText("" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0]);
                        } else {
                            tvTwelveYears.setText("MM/DD/YYYY");
                        }
                    } catch (Exception e) {
                        tvTwelveYears.setText("MM/DD/YYYY");
                        e.printStackTrace();
                    }
                } else {

                    fireBugEquipment = DataManager.getInstance().getEquipment(AssetInformationFragment.tvTagID.getText().toString());
                    if (null != fireBugEquipment) {
                        InspectionDates = fireBugEquipment.getInspectionDates();
                        if (null != InspectionDates && 0 != InspectionDates.size()) {

                            for (int i = 0; i < InspectionDates.size(); i++) {
                                if (null != InspectionDates.get(i).getDueDate() && !"MM/DD/YYY".equals(InspectionDates.get(i).getDueDate())) {


                                    if (InspectionDates.get(i).getInspectionType().equals("Daily")) {
                                        tvDaily.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("Weekly")) {
                                        tvWeekly.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("Monthly")) {
                                        tvMonthly.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("Quarterly")) {
                                        tvQuarterly.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("6 Months")) {
                                        tvSemiAnnual.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("Annual")) {
                                        tvAnnual.setText("" + InspectionDates.get(i).getDueDate());
                                    }else if (InspectionDates.get(i).getInspectionType().equals("5 Years")) {
                                        tvFiveYears.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("6 Years")) {
                                        tvSixYears.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("10 Years")) {
                                        tvTenYears.setText("" + InspectionDates.get(i).getDueDate());
                                    } else if (InspectionDates.get(i).getInspectionType().equals("12 Years")) {
                                        tvTwelveYears.setText("" + InspectionDates.get(i).getDueDate());
                                    }
                                }
                            }

                        }
                    }
                }


            }
        }

    }


    void setViewForVAddAsset() {


        fireBugEquipment = DataManager.getInstance().getEquipment(AssetInformationFragment.tvTagID.getText().toString());
        if (null != fireBugEquipment) {
            InspectionDates = fireBugEquipment.getInspectionDates();
            if (null != InspectionDates && 0 != InspectionDates.size()) {

                for (int i = 0; i < InspectionDates.size(); i++) {
                    if (InspectionDates.get(i).getInspectionType().equals("Daily")) {
                        tvDaily.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("Weekly")) {
                        tvWeekly.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("Monthly")) {
                        tvMonthly.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("Quarterly")) {
                        tvQuarterly.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("6 Months")) {
                        tvSemiAnnual.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("Annual")) {
                        tvAnnual.setText("" + InspectionDates.get(i).getDueDate());
                    }else if (InspectionDates.get(i).getInspectionType().equals("5 Years")) {
                        tvFiveYears.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("6 Years")) {
                        tvSixYears.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("10 Years")) {
                        tvTenYears.setText("" + InspectionDates.get(i).getDueDate());
                    } else if (InspectionDates.get(i).getInspectionType().equals("12 Years")) {
                        tvTwelveYears.setText("" + InspectionDates.get(i).getDueDate());
                    }
                }

            }
        }
/*        tvDaily.setText("MM/DD/YYYY");
        tvWeekly.setText("MM/DD/YYYY");
        tvMonthly.setText("MM/DD/YYYY");
        tvQuarterly.setText("MM/DD/YYYY");
        tvSemiAnnual.setText("MM/DD/YYYY");
        tvAnnual.setText("MM/DD/YYYY");
        tvFiveYears.setText("MM/DD/YYYY");
        tvSixYears.setText("MM/DD/YYYY");
        tvTenYears.setText("MM/DD/YYYY");
        tvTwelveYears.setText("MM/DD/YYYY");*/

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
