package com.ets.gd.FireBug.Fragments;


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

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Adapters.AssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Customer.CustomerFragment;
import com.ets.gd.FireBug.RouteInspection.RouteInspectionActivity;
import com.ets.gd.FireBug.Scan.CommonFirebugScanActivity;
import com.ets.gd.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.FireBug.ViewInformation.ViewLocationInformationActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.NetworkLayer.ResponseDTOs.PermissionType;
import com.ets.gd.NetworkLayer.ResponseDTOs.SortOrderFireBug;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class FirebugDashboardFragment extends Fragment {

    private static FloatingActionMenu fab;
    private static FloatingActionButton fabItemAddAsset, itemAddLocation;
    String[] fbTasksWithoutTransfer = {"View Information", "Move Assets", "Unit Inspection", "Route Inspection"};
    int[] fbTasksImagesWithoutTransfer = {R.drawable.ic_view_info, R.drawable.ic_move_op, R.drawable.ic_inspect_op, R.drawable.ic_inspect_op};
    String[] fbTasks;
    int[] fbTasksImages;
    View rootView;
    RecyclerView rvTasks;
    AssetsAdapter adapter;
    Context mContext;
    TextView tvCompanyValue;
    ImageView ivChangeCompany;
    SharedPreferencesManager sharedPreferencesManager;
    private List<PermissionType> rolePermissions = new ArrayList<>();
    List<SortOrderFireBug> sortOrderFireBugList = new ArrayList<>();

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
        rolePermissions = DataManager.getInstance().getRolePermissionsByUserName(sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_USERNAME));
        ivChangeCompany.setVisibility(View.VISIBLE);
        setupSortOrder();
        adapter = new AssetsAdapter(fbTasks, fbTasksImages);
        BaseActivity.currentFragment = new FirebugDashboardFragment();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvTasks.setLayoutManager(mLayoutManager);
        rvTasks.setItemAnimator(new DefaultItemAnimator());
        rvTasks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mContext = this.getActivity();

        boolean accessViewInfo = false;
        for (int i = 0; i < rolePermissions.size(); i++) {
            if (rolePermissions.get(i).getValue().equals("AddEdit")) {
                accessViewInfo = true;
            }
        }

        if (!accessViewInfo) {
            fab.setVisibility(View.GONE);
        }


        boolean accessFBEq = false;
        for (int i = 0; i < rolePermissions.size(); i++) {
            if (rolePermissions.get(i).getValue().equals("FireBugEquipment")) {
                accessFBEq = true;
            }
        }

        if (!accessFBEq) {
            fab.setVisibility(View.GONE);
        }


    }

    private void setupSortOrder() {
        sortOrderFireBugList = DataManager.getInstance().getAllFireBugSortOrderList();

        if (null != sortOrderFireBugList) {
            fbTasks = new String[sortOrderFireBugList.size()];
            fbTasksImages = new int[sortOrderFireBugList.size()];
            for (int i = 0; i < sortOrderFireBugList.size(); i++) {
                fbTasks[i] = sortOrderFireBugList.get(i).getName();
                if (sortOrderFireBugList.get(i).getName().toString().toLowerCase().startsWith("vie")) {
                    fbTasksImages[i] = R.drawable.ic_view_info;
                } else if (sortOrderFireBugList.get(i).getName().toString().toLowerCase().startsWith("mov")) {
                    fbTasksImages[i] = R.drawable.ic_move_op;
                } else if (sortOrderFireBugList.get(i).getName().toString().toLowerCase().startsWith("tra")) {
                    fbTasksImages[i] = R.drawable.ic_transfer;
                } else if (sortOrderFireBugList.get(i).getName().toString().toLowerCase().startsWith("uni")) {
                    fbTasksImages[i] = R.drawable.ic_inspect_op;
                } else if (sortOrderFireBugList.get(i).getName().toString().toLowerCase().startsWith("rou")) {
                    fbTasksImages[i] = R.drawable.ic_inspect_op;
                }
            }
        }
    }

    private void initListeners() {
        fabItemAddAsset.setOnClickListener(mGlobal_OnClickListener);
        itemAddLocation.setOnClickListener(mGlobal_OnClickListener);
        ivChangeCompany.setOnClickListener(mGlobal_OnClickListener);
        rvTasks.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvTasks, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                rolePermissions = DataManager.getInstance().getRolePermissionsByUserName(sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_USERNAME));
                if (fbTasks[position].toLowerCase().startsWith("ro")) {
                    boolean accessFBEq = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("FireBugEquipment")) {
                            accessFBEq = true;
                        }
                    }

                    if (accessFBEq) {
                        Intent in = new Intent(getActivity(), RouteInspectionActivity.class);
                        in.putExtra("taskType", fbTasks[position]);
                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                        startActivity(in);
                    } else {
                        showToast("you don't have permission to FireBug Equipment");
                    }

                } else if (fbTasks[position].toLowerCase().startsWith("uni")) {
                    boolean accessFBEq = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("FireBugEquipment")) {
                            accessFBEq = true;
                        }
                    }

                    if (accessFBEq) {
                        Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
                        in.putExtra("taskType", "Inspect Assets");
                        in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                        startActivity(in);
                    } else {
                        showToast("you don't have permission to FireBug Equipment");
                    }

                } else {

                    if (fbTasks[position].toLowerCase().startsWith("view")) {
                        boolean accessViewInfo = false;
                        for (int i = 0; i < rolePermissions.size(); i++) {
                            if (rolePermissions.get(i).getValue().equals("AddEdit")) {
                                for (int j = 0; j < rolePermissions.size(); j++) {
                                    if (rolePermissions.get(j).getValue().equals("FireBugEquipment")) {
                                        accessViewInfo = true;
                                    }
                                }
                            }
                        }

                        if (accessViewInfo) {
                            Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
                            in.putExtra("taskType", fbTasks[position]);
                            in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                            startActivity(in);
                        } else {
                            showToast("you don't have permission to View Information/FireBug Equipment");
                        }
                    }

                    if (fbTasks[position].toLowerCase().startsWith("mov")) {
                        boolean accessMove = false;
                        for (int i = 0; i < rolePermissions.size(); i++) {
                            if (rolePermissions.get(i).getValue().equals("Move")) {

                                for (int j = 0; j < rolePermissions.size(); j++) {
                                    if (rolePermissions.get(j).getValue().equals("FireBugEquipment")) {
                                        accessMove = true;
                                    }
                                }

                            }
                        }
                        if (accessMove) {
                            Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
                            in.putExtra("taskType", fbTasks[position]);
                            in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                            startActivity(in);
                        } else {
                            showToast("you don't have permission to Move/FireBug Equipment");
                        }
                    }

                    if (fbTasks[position].toLowerCase().startsWith("tran")) {
                        boolean accessTransfer = false;
                        for (int i = 0; i < rolePermissions.size(); i++) {
                            if (rolePermissions.get(i).getValue().equals("Transfer")) {
                                for (int j = 0; j < rolePermissions.size(); j++) {
                                    if (rolePermissions.get(j).getValue().equals("FireBugEquipment")) {
                                        accessTransfer = true;
                                    }
                                }
                            }
                        }
                        if (accessTransfer) {
                            Intent in = new Intent(getActivity(), CommonFirebugScanActivity.class);
                            in.putExtra("taskType", fbTasks[position]);
                            in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                            startActivity(in);
                        } else {
                            showToast("you don't have permission to Transfer/FireBug Equipment");
                        }
                    }

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

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
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
                    in.putExtra("compName", tvCompanyValue.getText().toString().trim());
                    startActivity(in);
                    break;
                }

                case R.id.itemAddLocation: {
                    fab.close(true);
                    Intent in = new Intent(getActivity(), ViewLocationInformationActivity.class);
                    in.putExtra("action", "addLoc");
                    in.putExtra("customerName", tvCompanyValue.getText().toString());
                    startActivity(in);
                    break;
                }


            }
        }

    };


}
