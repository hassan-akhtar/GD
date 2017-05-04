package com.ets.gd.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Activities.FireBug.ViewInformation.ViewLocationInformationActivity;
import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Activities.Scan.CommonFirebugScanActivity;
import com.ets.gd.Activities.FireBug.RouteInspection.RouteInspectionActivity;
import com.ets.gd.Adapters.AssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.RouteLocation;
import com.ets.gd.Models.Routes;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;


public class FirebugDashboardFragment extends Fragment {

    private static FloatingActionMenu fab;
    private static FloatingActionButton fabItemAddAsset, itemAddLocation;
    String[] fbTasksWithoutTransfer = {"View Information", "Move Assets", "Unit Inspection", "Route Inspection"};
    int[] fbTasksImagesWithoutTransfer = {R.drawable.ic_view_info, R.drawable.ic_move_op, R.drawable.ic_inspect_op, R.drawable.ic_inspect_op};
    String[] fbTasks = {"View Information", "Move Assets", "Transfer Assets", "Unit Inspection", "Route Inspection"};
    int[] fbTasksImages = {R.drawable.ic_view_info, R.drawable.ic_move_op, R.drawable.ic_transfer, R.drawable.ic_inspect_op, R.drawable.ic_inspect_op};
    View rootView;
    RecyclerView rvTasks;
    AssetsAdapter adapter;
    Context mContext;
    TextView tvCompanyValue;
    ImageView ivChangeCompany;
    SharedPreferencesManager sharedPreferencesManager;

    public FirebugDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_firebug_dashboard, container, false);
        initViews();
        initObj();
        initListeners();

        return rootView;
    }



    private void initViews() {
        rvTasks = (RecyclerView) rootView.findViewById(R.id.lvTasks);
        tvCompanyValue = (TextView) rootView.findViewById(R.id.tvCompanyValue);
        ivChangeCompany = (ImageView) rootView.findViewById(R.id.ivChangeCompany);
        fab = (FloatingActionMenu) rootView.findViewById(R.id.fab);
        fabItemAddAsset = (FloatingActionButton) rootView.findViewById(R.id.itemAddAsset);
        itemAddLocation = (FloatingActionButton) rootView.findViewById(R.id.itemAddLocation);
    }

    private void initObj() {

        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
//        if (!DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).isServiceCompany()) {
//            ivChangeCompany.setVisibility(View.GONE);
//            adapter = new AssetsAdapter(fbTasksWithoutTransfer, fbTasksImagesWithoutTransfer);
//        }else if( DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).isServiceCompany() &&
//                0==DataManager.getInstance().getAllCustomerList(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).size()){
//            ivChangeCompany.setVisibility(View.GONE);
//            adapter = new AssetsAdapter(fbTasksWithoutTransfer, fbTasksImagesWithoutTransfer);
      //  } else {
            ivChangeCompany.setVisibility(View.VISIBLE);
            adapter = new AssetsAdapter(fbTasks, fbTasksImages);
      //  }

        BaseActivity.currentFragment = new FirebugDashboardFragment();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvTasks.setLayoutManager(mLayoutManager);
        rvTasks.setItemAnimator(new DefaultItemAnimator());
        rvTasks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mContext = this.getActivity();



    }

    private void initListeners() {
        fabItemAddAsset.setOnClickListener(mGlobal_OnClickListener);
        itemAddLocation.setOnClickListener(mGlobal_OnClickListener);
        ivChangeCompany.setOnClickListener(mGlobal_OnClickListener);
        rvTasks.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvTasks, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {


//                if (!DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).isServiceCompany()) {
//                    if (fbTasksWithoutTransfer[position].toLowerCase().startsWith("ro")) {
//                        showToast("" + fbTasksWithoutTransfer[position]);
//
//                    } else if (fbTasksWithoutTransfer[position].toLowerCase().startsWith("uni")) {
//                        Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
//                        in.putExtra("taskType", "Inspect Assets");
//                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
//                        startActivity(in);
//
//                    } else {
//                        Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
//                        in.putExtra("taskType", fbTasksWithoutTransfer[position]);
//                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
//                        startActivity(in);
//                    }
//                }else if( DataManager.getInstance().getSyncGetResponseDTO(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).isServiceCompany() &&
//                        0==DataManager.getInstance().getAllCustomerList(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID)).size()){
//                    if (fbTasksWithoutTransfer[position].toLowerCase().startsWith("ro")) {
//                        showToast("" + fbTasksWithoutTransfer[position]);
//
//                    } else if (fbTasksWithoutTransfer[position].toLowerCase().startsWith("uni")) {
//                        Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
//                        in.putExtra("taskType", "Inspect Assets");
//                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
//                        startActivity(in);
//
//                    } else {
//                        Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
//                        in.putExtra("taskType", fbTasksWithoutTransfer[position]);
//                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
//                        startActivity(in);
//                    }
//                } else {
                    if (fbTasks[position].toLowerCase().startsWith("ro")) {
                        showToast("" + fbTasks[position]);

                    } else if (fbTasks[position].toLowerCase().startsWith("uni")) {
                        Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
                        in.putExtra("taskType", "Inspect Assets");
                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                        startActivity(in);

                    } else {
                        Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
                        in.putExtra("taskType", fbTasks[position]);
                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                        startActivity(in);
                    }
               // }



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventonMainThread(String companyName) {
        tvCompanyValue.setText(companyName);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivChangeCompany: {
                    BaseActivity.refreshMainViewByNew(new CustomerFragment());
                    break;
                }

                case R.id.itemAddAsset: {
                    fab.close(true);
                    Intent in = new Intent(getActivity(), ViewAssetInformationActivity.class);
                    in.putExtra("action", "addAsset");
                    in.putExtra("compName",  tvCompanyValue.getText().toString().trim());
                    startActivity(in);
                    break;
                }

                case R.id.itemAddLocation: {
                    fab.close(true);
                    Intent in = new Intent(getActivity(), ViewLocationInformationActivity.class);
                    in.putExtra("action", "addLoc");
                    startActivity(in);
                    break;
                }


            }
        }

    };

}
