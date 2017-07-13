package com.ets.gd.ToolHawk.Fragments;

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
import android.widget.Toast;

import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Adapters.AssetsAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.NetworkLayer.ResponseDTOs.PermissionType;
import com.ets.gd.NetworkLayer.ResponseDTOs.SortOrderToolHawk;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkDepartmentActivity;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkScanActivity;
import com.ets.gd.ToolHawk.EquipmentInfo.EquipmentInfoActivity;
import com.ets.gd.ToolHawk.EquipmentInfo.ToolhawkLocationActivity;
import com.ets.gd.Utils.SharedPreferencesManager;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;


public class ToolhawkDashboardFragmentNew extends Fragment {

    View rootView;
    private static FloatingActionMenu fab;
    private static FloatingActionButton fabItemAddItem, itemAddLocation;
    String[] thTasks;
    int[] thTasksImages;
    RecyclerView rvTasks;
    AssetsAdapter adapter;
    Context mContext;
    SharedPreferencesManager sharedPreferencesManager;
    private List<PermissionType> rolePermissions = new ArrayList<>();
    List<SortOrderToolHawk> sortOrderToolHawkList = new ArrayList<>();

    public ToolhawkDashboardFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_toolhawk_dashboard_new, container, false);
        initViews();
        initObj();
        initListeners();

        return rootView;

    }

    private void initViews() {
        rvTasks = (RecyclerView) rootView.findViewById(R.id.lvTasks);
        fab = (FloatingActionMenu) rootView.findViewById(R.id.fab);
        fabItemAddItem = (FloatingActionButton) rootView.findViewById(R.id.itemAddAsset);
        itemAddLocation = (FloatingActionButton) rootView.findViewById(R.id.itemAddLocation);
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        rolePermissions = DataManager.getInstance().getRolePermissionsByUserName(sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_USERNAME));
        setupSortOrder();
        adapter = new AssetsAdapter(thTasks, thTasksImages);
        BaseActivity.currentFragment = new ToolhawkDashboardFragmentNew();
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

        boolean accessTHEq = false;
        for (int i = 0; i < rolePermissions.size(); i++) {
            if (rolePermissions.get(i).getValue().equals("ToolHawkEquipment")) {
                accessTHEq = true;
            }
        }
        if (!accessTHEq) {
            fab.setVisibility(View.GONE);
        }


    }


    private void setupSortOrder() {
        sortOrderToolHawkList = DataManager.getInstance().getAllToolHawkSortOrderList();
        if (null != sortOrderToolHawkList) {
            thTasks = new String[sortOrderToolHawkList.size()];
            thTasksImages = new int[sortOrderToolHawkList.size()];
            for (int i = 0; i < sortOrderToolHawkList.size(); i++) {
                thTasks[i] = sortOrderToolHawkList.get(i).getName();
                if (sortOrderToolHawkList.get(i).getName().toString().toLowerCase().startsWith("equ")) {
                    thTasksImages[i] = R.drawable.ic_view_info;
                } else if (sortOrderToolHawkList.get(i).getName().toString().toLowerCase().startsWith("qui")) {
                    thTasksImages[i] = R.drawable.ic_quickcount;
                } else if (sortOrderToolHawkList.get(i).getName().toString().toLowerCase().contains("out")) {
                    thTasksImages[i] = R.drawable.ic_checkout;
                } else if (sortOrderToolHawkList.get(i).getName().toString().toLowerCase().contains("in")) {
                    thTasksImages[i] = R.drawable.ic_checkin;
                } else if (sortOrderToolHawkList.get(i).getName().toString().toLowerCase().startsWith("mov")) {
                    thTasksImages[i] = R.drawable.ic_move_op;
                } else if (sortOrderToolHawkList.get(i).getName().toString().toLowerCase().startsWith("main")) {
                    thTasksImages[i] = R.drawable.ic_maintenance;
                }
            }
        }
    }

    private void initListeners() {
        fabItemAddItem.setOnClickListener(mGlobal_OnClickListener);
        itemAddLocation.setOnClickListener(mGlobal_OnClickListener);
        rvTasks.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvTasks, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                rolePermissions = DataManager.getInstance().getRolePermissionsByUserName(sharedPreferencesManager.getString(SharedPreferencesManager.CURRENT_USERNAME));
                if (thTasks[position].toLowerCase().startsWith("eq")) {
                    boolean accessViewInfo = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("AddEdit")) {
                            for (int j = 0; j < rolePermissions.size(); j++) {
                                if (rolePermissions.get(j).getValue().equals("ToolHawkEquipment")) {
                                    accessViewInfo = true;
                                }
                            }

                        }
                    }

                    if (accessViewInfo) {
                        ToolhawkScanActivity(position);
                    } else {
                        showToast("you don't have permission to Equipment Info/ToolHawk Equipment");
                    }

                }

                if (thTasks[position].toLowerCase().startsWith("tra")) {
                    boolean accessTransfer = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("Transfer")) {

                            for (int j = 0; j < rolePermissions.size(); j++) {
                                if (rolePermissions.get(j).getValue().equals("ToolHawkEquipment")) {
                                    accessTransfer = true;
                                }
                            }
                        }
                    }
                    if (accessTransfer) {
                        ToolhawkScanActivity(position);
                    } else {
                        showToast("you don't have permission to Transfer/ToolHawk Equipment");
                    }

                }
                if (thTasks[position].toLowerCase().startsWith("qu")) {
                    boolean accessTHEq = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("ToolHawkEquipment")) {
                            accessTHEq = true;
                        }
                    }
                    if (accessTHEq) {
                        ToolhawkScanActivity(position);
                    } else {
                        showToast("you don't have permission to ToolHawk Equipment");
                    }
                }
                if (thTasks[position].toLowerCase().startsWith("ma")) {
                    boolean accessTHEq = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("ToolHawkEquipment")) {
                            accessTHEq = true;
                        }
                    }
                    if (accessTHEq) {
                        ToolhawkScanActivity(position);
                    } else {
                        showToast("you don't have permission to ToolHawk Equipment");
                    }
                }
                if (thTasks[position].toLowerCase().startsWith("mo")) {
                    boolean accessMove = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("Move")) {
                            for (int j = 0; j < rolePermissions.size(); j++) {
                                if (rolePermissions.get(j).getValue().equals("ToolHawkEquipment")) {
                                    accessMove = true;
                                }
                            }

                        }
                    }
                    if (accessMove) {
                        gotoToolhawkDepartmentActivity(position);
                    } else {
                        showToast("you don't have permission to Move/ToolHawk Equipment");
                    }

                }
                if (thTasks[position].toLowerCase().startsWith("check in")) {
                    boolean accessCheckin = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("Checkin")) {
                            for (int j = 0; j < rolePermissions.size(); j++) {
                                if (rolePermissions.get(j).getValue().equals("ToolHawkEquipment")) {
                                    accessCheckin = true;
                                }
                            }

                        }
                    }
                    if (accessCheckin) {
                        gotoToolhawkDepartmentActivity(position);
                    } else {
                        showToast("you don't have permission to Check In/ToolHawk Equipment");
                    }

                }

                if (thTasks[position].toLowerCase().startsWith("check out")) {
                    boolean accessCheckout = false;
                    for (int i = 0; i < rolePermissions.size(); i++) {
                        if (rolePermissions.get(i).getValue().equals("Checkout")) {
                            for (int j = 0; j < rolePermissions.size(); j++) {
                                if (rolePermissions.get(j).getValue().equals("ToolHawkEquipment")) {
                                    accessCheckout = true;
                                }
                            }

                        }
                    }
                    if (accessCheckout) {
                        gotoToolhawkDepartmentActivity(position);
                    } else {
                        showToast("you don't have permission to Check Out/ToolHawk Equipment");
                    }

                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    void gotoToolhawkDepartmentActivity(int position) {
        Intent in = new Intent(getActivity(), CommonToolhawkDepartmentActivity.class);
        in.putExtra("taskType", thTasks[position]);
        startActivity(in);
    }


    void ToolhawkScanActivity(int position) {
        Intent in = new Intent(getActivity(), CommonToolhawkScanActivity.class);
        in.putExtra("taskType", thTasks[position]);
        startActivity(in);
    }

    void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.itemAddAsset: {
                    fab.close(true);
                    Intent in = new Intent(getActivity(), EquipmentInfoActivity.class);
                    in.putExtra("taskType", "add");
                    startActivity(in);
                    break;
                }

                case R.id.itemAddLocation: {
                    fab.close(true);
                    Intent in = new Intent(getActivity(), ToolhawkLocationActivity.class);
                    in.putExtra("taskType", "add");
                    startActivity(in);
                    break;
                }

            }
        }

    };


}
