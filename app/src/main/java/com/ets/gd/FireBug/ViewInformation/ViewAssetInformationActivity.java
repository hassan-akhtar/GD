package com.ets.gd.FireBug.ViewInformation;

import android.content.Intent;
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
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.InspectionDates;
import com.ets.gd.Models.RealmSyncGetResponseDTO;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.FirebugEqSize;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.MyLocation;
import com.ets.gd.NetworkLayer.ResponseDTOs.Size;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class ViewAssetInformationActivity extends AppCompatActivity {
    ImageView ivBack, ivChangeCompany, ivTick, ivAdd;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static Fragment currentFragment;
    public static String actionType, barCodeID;
    public static boolean isAssetAdded = false;
    static String tagID = "", compName = "";
    Asset asset;
    RealmSyncGetResponseDTO realmSyncGetResponseDTO;
    public static List<EquipmentNote> newNotesList = new ArrayList<EquipmentNote>();
    Customer customer;
    FireBugEquipment fireBugEquipment;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_asset_information);

        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {
        newNotesList.clear();
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        ivAdd  = (ImageView) findViewById(R.id.ivAdd);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        compName = getIntent().getStringExtra("compName");
        tvCompanyValue.setText(compName);
        AssetLocationFragment.customerName = compName;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }


    public FireBugEquipment getEquipment() {
        return fireBugEquipment;
    }

    public void setEquipment(FireBugEquipment fireBugEquipment) {
        this.fireBugEquipment = fireBugEquipment;
    }


    private void initObj() {
        actionType = getIntent().getStringExtra("action");
        barCodeID = getIntent().getStringExtra("barCode");

        setEquipment(DataManager.getInstance().getEquipment(barCodeID));
        setAsset(DataManager.getInstance().getAsset(barCodeID));

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        sharedPreferencesManager = new SharedPreferencesManager(ViewAssetInformationActivity.this);

        if ("viewAsset".equals(actionType)) {
            isAssetAdded = true;
            tbTitleBottom.setText("View Asset");
        } else {
            isAssetAdded = false;
            tbTitleBottom.setText("Add Asset");
        }

        if (!DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).isServiceCompany()) {
            ivChangeCompany.setVisibility(View.GONE);
        } else if (DataManager.getInstance().isServiceCompany() &&
                0 == DataManager.getInstance().getAllCustomerList().size()) {
            ivChangeCompany.setVisibility(View.GONE);
        } else {
            ivChangeCompany.setVisibility(View.VISIBLE);
        }
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        ivAdd.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }
                case R.id.ivAdd: {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    break;
                }

                case R.id.ivTick: {


                    if (0 == tabLayout.getSelectedTabPosition()) {
                        if (checkValidationAddAsset()) {

                            if ("viewAsset".equals(actionType)) {
                                FireBugEquipment fireBugEquipmentt = null;
                                try {
                                    FirebugEqSize firebugEqSize = DataManager.getInstance().getAssetSize(AssetInformationFragment.spSize.getItemAtPosition(AssetInformationFragment.posSize).toString());
                                    Size  size = new Size();
                                    size.setID(firebugEqSize.getID());
                                    size.setCode(firebugEqSize.getCode());
                                    size.setDescription(firebugEqSize.getDescription());
                                    fireBugEquipmentt = new FireBugEquipment(
                                            size,
                                            fireBugEquipment.getID(),
                                            AssetInformationFragment.tvTagID.getText().toString(),
                                            AssetInformationFragment.tvSrNo.getText().toString().trim(),
                                            AssetInformationFragment.tvMfgDate.getText().toString().trim(),
                                            DataManager.getInstance().getAssetAgentType(AssetInformationFragment.spAgent.getItemAtPosition(AssetInformationFragment.posAgent).toString()),
                                            DataManager.getInstance().getAssetCustomer(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)),
                                            DataManager.getInstance().getAssetDeviceType(AssetInformationFragment.spDeviceType.getItemAtPosition(AssetInformationFragment.posDeviceType).toString()),
                                            DataManager.getInstance().getAssetLocation(AssetLocationFragment.spLocation.getItemAtPosition(AssetLocationFragment.posLoc).toString()),
                                            DataManager.getInstance().getAssetManufacturer(AssetInformationFragment.spManufacturer.getItemAtPosition(AssetInformationFragment.posManufacturer).toString()),
                                            DataManager.getInstance().getAssetVendorCode(AssetInformationFragment.spVendor.getItemAtPosition(AssetInformationFragment.posVendor).toString()),
                                            DataManager.getInstance().getAssetModel(AssetInformationFragment.spModel.getItemAtPosition(AssetInformationFragment.posModel).toString()),
                                            true, fireBugEquipment.isAdded());
                                    showToast("Asset Updated.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    showToast("Asset not Updated.");
                                }
                                DataManager.getInstance().addEquipment(fireBugEquipmentt);
                            } else {
                                tabLayout.getTabAt(1).select();
                                showToast("Please enter location for asset!");
                            }

                        }
                    } else if (1 == tabLayout.getSelectedTabPosition()) {

                        if ("viewAsset".equals(actionType)) {
                            showToast("Asset's Location can't be Updated");
                        } else {
                            if(checkValidationAddAsset())  {
                                List<FireBugEquipment> fireBugEquipmentList = DataManager.getInstance().getCompanyEquipments(DataManager.getInstance().getCustomerByCode(AssetLocationFragment.spCustomer.getSelectedItem().toString()).getID());
                                FireBugEquipment equipment = DataManager.getInstance().getEquipment(AssetInformationFragment.tvTagID.getText().toString());
                                boolean exists = false;

                          //  if (fireBugEquipment == DataManager.getInstance().getEquipment(AssetInformationFragment.tvTagID.getText().toString())) {
                                if (checkValidationAddAssetLocation()) {

                                    if (null!=equipment) {
                                        for(FireBugEquipment eq : fireBugEquipmentList){
                                            if(eq.getCode().equals(equipment.getCode())){
                                                exists = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!exists) {
                                    FireBugEquipment fireBugEquipment = new FireBugEquipment();
                                    try {
                                        customer = DataManager.getInstance().getCustomerByCode(AssetLocationFragment.spCustomer.getItemAtPosition(AssetLocationFragment.posCustomer).toString());
                                        Locations locations = DataManager.getInstance().getAssetLocations(AssetLocationFragment.spLocation.getItemAtPosition(AssetLocationFragment.posLoc).toString());
                                        MyLocation myLocation = new MyLocation(locations.getID(), locations.getCode(), locations.getDescription(), customer.getID(), locations.getSite().getID(), locations.getBuilding().getID());
                                        FirebugEqSize firebugEqSize = DataManager.getInstance().getAssetSize(AssetInformationFragment.spSize.getItemAtPosition(AssetInformationFragment.posSize).toString());
                                        Size  size = new Size();
                                        size.setID(firebugEqSize.getID());
                                        size.setCode(firebugEqSize.getCode());
                                        size.setDescription(firebugEqSize.getDescription());
                                        fireBugEquipment = new FireBugEquipment(
                                                size,
                                                0,
                                                AssetInformationFragment.tvTagID.getText().toString(),
                                                AssetInformationFragment.tvSrNo.getText().toString().trim(),
                                                AssetInformationFragment.tvMfgDate.getText().toString().trim(),
                                                DataManager.getInstance().getAssetAgentType(AssetInformationFragment.spAgent.getItemAtPosition(AssetInformationFragment.posAgent).toString()),
                                                customer,
                                                DataManager.getInstance().getAssetDeviceType(AssetInformationFragment.spDeviceType.getItemAtPosition(AssetInformationFragment.posDeviceType).toString()),
                                                myLocation,
                                                DataManager.getInstance().getAssetManufacturer(AssetInformationFragment.spManufacturer.getItemAtPosition(AssetInformationFragment.posManufacturer).toString()),
                                                DataManager.getInstance().getAssetVendorCode(AssetInformationFragment.spVendor.getItemAtPosition(AssetInformationFragment.posVendor).toString()),
                                                DataManager.getInstance().getAssetModel(AssetInformationFragment.spModel.getItemAtPosition(AssetInformationFragment.posModel).toString()),
                                                fireBugEquipment.isUpdated(), true);
                                        DataManager.getInstance().addEquipment(fireBugEquipment);
                                        showToast("Asset Added.");
                                        ivAdd.setVisibility(View.VISIBLE);
                                        showToast("You can now add notes and inspection dates for this asset.");
                                        isAssetAdded = true;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        showToast("Asset not saved..");
                                    }
                                }else{
                                        isAssetAdded = false;
                                        showToast("Asset with tag Id: " + AssetInformationFragment.tvTagID.getText().toString().trim() + " Already exists.");
                                }
                                }
                          //  } else {
                           //     showToast("Asset with tag Id: " + AssetInformationFragment.tvTagID.getText().toString().trim() + " Already exists.");
                           // }
                        }else{
                                tabLayout.getTabAt(0).select();
                            }
                    }


                    } else if (2 == tabLayout.getSelectedTabPosition()) {
                        if (isAssetAdded) {
                            if (checkValidationAddAssetNote()) {
                                int ID = 0;

                                if( null!=fireBugEquipment && 0!=fireBugEquipment.getID()){
                                    ID = fireBugEquipment.getID();
                                }


                                DataManager.getInstance().addUpdateAssetNote(ID,AssetInformationFragment.tvTagID.getText().toString(), sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID), newNotesList);
                                if ("viewAsset".equals(actionType)) {
                                    showToast("Asset's Note(s) Updated");
                                } else {
                                    showToast("Asset's Note(s) Added");
                                }

                            }
                        } else {
                            showToast("Please add asset first");
                        }
                    } else if (3 == tabLayout.getSelectedTabPosition()) {
                        if (isAssetAdded) {
                            if (checkValidationAddAssetInspectionDates()) {
                                if (!"viewAsset".equals(actionType)) {
                                    DataManager.getInstance().addUpdateAssetInspecDates(AssetInformationFragment.tvTagID.getText().toString(), new InspectionDates(
                                            InspectionDatesFragment.tvDaily.getText().toString().trim(),
                                            InspectionDatesFragment.tvWeekly.getText().toString().trim(),
                                            InspectionDatesFragment.tvMonthly.getText().toString().trim(),
                                            InspectionDatesFragment.tvQuarterly.getText().toString().trim(),
                                            InspectionDatesFragment.tvSemiAnnual.getText().toString().trim(),
                                            InspectionDatesFragment.tvAnnual.getText().toString().trim(),
                                            InspectionDatesFragment.tvFiveYears.getText().toString().trim(),
                                            InspectionDatesFragment.tvSixYears.getText().toString().trim(),
                                            InspectionDatesFragment.tvTenYears.getText().toString().trim(),
                                            InspectionDatesFragment.tvTwelveYears.getText().toString().trim()

                                    ));

                                    showToast("Asset's Inspection Date(s) Added.");
                                } else {
                                    showToast("You cannot update Asset's Inspection Date(s).");
                                }
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
        }
/*        else if ("MM/DD/YYYY".equals(AssetInformationFragment.tvMfgDate.getText().toString().trim())) {
            showToast("Please select Manufacturer date");
        } */

        else {
            return true;
        }

        return false;
    }


    private boolean checkValidationAddAssetLocation() {
        if (0 == AssetLocationFragment.posCustomer) {
            showToast("Please select company");
        } else if (0 == AssetLocationFragment.posLoc) {
            showToast("Please select Location ID");
        }  else {
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
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvQuarterly.getText().toString().trim())) {
            showToast("Please Select Quarterly Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvMonthly.getText().toString().trim())) {
            showToast("Please Select Monthly Inspection date");
        } else if ("DD/MM/YY".equals(InspectionDatesFragment.tvSemiAnnual.getText().toString().trim())) {
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
