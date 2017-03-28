package com.ets.gd.Activities.FireBug.ViewInformation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.AddNoteFragment;
import com.ets.gd.Fragments.AssetInformationFragment;
import com.ets.gd.Fragments.AssetLocationFragment;
import com.ets.gd.Fragments.InspectionDatesFragment;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.InspectionDates;
import com.ets.gd.Models.Location;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAssetInformationActivity extends AppCompatActivity {
    ImageView ivBack, ivChangeCompany, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static Fragment currentFragment;
    public static String actionType, barCodeID;
    boolean isAssetAdded = false;
    static String tagID = "";
    Asset asset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_asset_information);

        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    private void initObj() {
        setAsset(DataManager.getInstance().getAsset(barCodeID));
        actionType = getIntent().getStringExtra("action");
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();


        if ("viewAsset".equals(actionType)) {
            ivTick.setVisibility(View.GONE);
        } else {
            ivTick.setVisibility(View.VISIBLE);
        }
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {


                    if (0 == tabLayout.getSelectedTabPosition()) {
                        if (checkValidationAddAsset()) {
                            if (!DataManager.getInstance().doesAssetExist(AssetInformationFragment.tvTagID.getText().toString().trim())) {
                                DataManager.getInstance().AddAssetInfo(new Asset(AssetInformationFragment.tvTagID.getText().toString().trim(),
                                        AssetInformationFragment.spDeviceType.getItemAtPosition(AssetInformationFragment.posDeviceType).toString(),
                                        AssetInformationFragment.spManufacturer.getItemAtPosition(AssetInformationFragment.posManufacturer).toString(),
                                        AssetInformationFragment.spModel.getItemAtPosition(AssetInformationFragment.posModel).toString(),
                                        AssetInformationFragment.tvSrNo.getText().toString().trim(),
                                        AssetInformationFragment.tvMfgDate.getText().toString().trim(),
                                        AssetInformationFragment.spVendor.getItemAtPosition(AssetInformationFragment.posVendor).toString(),
                                        AssetInformationFragment.spAgent.getItemAtPosition(AssetInformationFragment.posAgent).toString()
                                ));
                                showToast("Asset Added");
                                tagID = AssetInformationFragment.tvTagID.getText().toString().trim();
                                isAssetAdded = true;
                            } else {
                                showToast("Asset Already Added!");
                            }
                        }
                    } else if (1 == tabLayout.getSelectedTabPosition()) {
                        if (isAssetAdded) {
                            if (checkValidationAddAssetLocation()) {
                                DataManager.getInstance().addUpdateAssetLocation(tagID, new Location(AssetLocationFragment.tvLocationID.getText().toString().trim(),
                                        AssetLocationFragment.tvDescprition.getText().toString().trim(), AssetLocationFragment.spSite.getItemAtPosition(AssetLocationFragment.posSite).toString(),
                                        AssetLocationFragment.spBuilding.getItemAtPosition(AssetLocationFragment.posBuilding).toString(), ""));
                                showToast("Asset's Location Updated");
                            }
                        } else {
                            showToast("Please add asset first");
                        }

                    } else if (2 == tabLayout.getSelectedTabPosition()) {
                        if (isAssetAdded) {
                            if (checkValidationAddAssetNote()) {
                                DataManager.getInstance().addUpdateAssetNote(tagID, AddNoteFragment.lstNotes);
                                showToast("Asset's Note(s) Updated");
                            }
                        } else {
                            showToast("Please add asset first");
                        }
                    } else if (3 == tabLayout.getSelectedTabPosition()) {
                        if (isAssetAdded) {
                            if (checkValidationAddAssetInspectionDates()) {
                                DataManager.getInstance().addUpdateAssetInspecDates(tagID, new InspectionDates(
                                        InspectionDatesFragment.tvDaily.getText().toString().trim(),
                                        InspectionDatesFragment.tvWeekly.getText().toString().trim(),
                                        InspectionDatesFragment.tvQuarterly.getText().toString().trim(),
                                        InspectionDatesFragment.tvMonthly.getText().toString().trim(),
                                        InspectionDatesFragment.tvSemiAnnual.getText().toString().trim(),
                                        InspectionDatesFragment.tvAnnual.getText().toString().trim(),
                                        InspectionDatesFragment.tvFiveYears.getText().toString().trim(),
                                        InspectionDatesFragment.tvSixYears.getText().toString().trim(),
                                        InspectionDatesFragment.tvTenYears.getText().toString().trim(),
                                        InspectionDatesFragment.tvTwelveYears.getText().toString().trim()

                                ));
                                showToast("Asset's Inspection Dates Updated");
                            }
                        } else {
                            showToast("Please add asset first");
                        }


                    }
                    break;
                }

            }
        }

    };


    private boolean checkValidationAddAsset() {
        if ("".equals(AssetInformationFragment.tvTagID.getText().toString().trim())) {
            showToast("Please enter Tag ID");
        } else if (0 == AssetInformationFragment.posDeviceType) {
            showToast("Please select a Device type");
        } else if (0 == AssetInformationFragment.posManufacturer) {
            showToast("Please select a Manufacturer");
        } else if (0 == AssetInformationFragment.posModel) {
            showToast("Please select a Model");
        } else if ("".equals(AssetInformationFragment.tvSrNo.getText().toString().trim())) {
            showToast("Please enter Serial Number");
        } else if ("".equals(AssetInformationFragment.tvMfgDate.getText().toString().trim())) {
            showToast("Please select Manufacturer date");
        } else if (0 == AssetInformationFragment.posVendor) {
            showToast("Please select a Vendor");
        } else if (0 == AssetInformationFragment.posAgent) {
            showToast("Please select a Agent");
        } else {
            return true;
        }

        return false;
    }


    private boolean checkValidationAddAssetLocation() {
        if ("".equals(AssetLocationFragment.tvLocationID.getText().toString().trim())) {
            showToast("Please enter Location ID");
        } else if (0 == AssetLocationFragment.posSite) {
            showToast("Please select a site");
        } else if (0 == AssetLocationFragment.posBuilding) {
            showToast("Please select a building");
        } else {
            return true;
        }

        return false;
    }


    private boolean checkValidationAddAssetNote() {
        if (0 == AddNoteFragment.lstNotes.size()) {
            showToast("Please add a note first");
        } else {
            return true;
        }

        return false;
    }

    private boolean checkValidationAddAssetInspectionDates() {
        if ("DD/MM/YY".equals(InspectionDatesFragment.tvDaily.getText().toString().trim())) {
            showToast("Please Select Daily Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvWeekly.getText().toString().trim())) {
            showToast("Please Select Weekly Inspection date");
        }else if ("DD/MM/YY".equals(InspectionDatesFragment.tvQuarterly.getText().toString().trim())) {
            showToast("Please Select Quarterly Inspection date");
        }else if ("DD/MM/YY".equals(InspectionDatesFragment.tvMonthly.getText().toString().trim())) {
            showToast("Please Select Monthly Inspection date");
        }else if ("DD/MM/YY".equals(InspectionDatesFragment.tvSemiAnnual.getText().toString().trim())) {
            showToast("Please Select Semi Annual Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvAnnual.getText().toString().trim())) {
            showToast("Please Select Annual Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvFiveYears.getText().toString().trim())) {
            showToast("Please Select 5 years Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvSixYears.getText().toString().trim())) {
            showToast("Please Select 6 years Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvTenYears.getText().toString().trim())) {
            showToast("Please Select 20 years Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvTwelveYears.getText().toString().trim())) {
            showToast("Please Select 12 years Inspection date");
        } else {
            return true;
        }

        return false;
    }


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Information");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.view_info_grey, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Location");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.location_grey, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Notes");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notes_grey, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Insp. Dates");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.inspect_grey, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AssetInformationFragment(), "Information");
        adapter.addFragment(new AssetLocationFragment(), "Asset Location");
        adapter.addFragment(new AddNoteFragment(), "Notes");
        adapter.addFragment(new InspectionDatesFragment(), "Inspection Dates");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
