package com.ets.gd.Activities.FireBugViewInformation;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.Fragments.AddNoteFragment;
import com.ets.gd.Fragments.AssetInformationFragment;
import com.ets.gd.Fragments.AssetLocationFragment;
import com.ets.gd.Fragments.InspectionDatesFragment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAssetInformationActivity extends AppCompatActivity {
    ImageView ivBack, ivChangeCompany;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


    }

    private void initObj() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }
            }
        }

    };

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
