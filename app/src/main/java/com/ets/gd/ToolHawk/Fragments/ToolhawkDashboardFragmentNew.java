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
    String[] thTasks = {"Equipment Info", "Quick Count", "Check Out", "Check In", "Move Assets", "Maintenance"}; //, "Transfer Assets"
    int[] thTasksImages = {R.drawable.ic_view_info, R.drawable.ic_quickcount, R.drawable.ic_checkout, R.drawable.ic_checkin, R.drawable.ic_move_op, R.drawable.ic_maintenance};//, R.drawable.ic_transfer
    RecyclerView rvTasks;
    AssetsAdapter adapter;
    Context mContext;
    SharedPreferencesManager sharedPreferencesManager;
    private List<PermissionType> rolePermissions = new ArrayList<>();

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
        adapter = new AssetsAdapter(thTasks, thTasksImages);
        BaseActivity.currentFragment = new ToolhawkDashboardFragmentNew();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvTasks.setLayoutManager(mLayoutManager);
        rvTasks.setItemAnimator(new DefaultItemAnimator());
        rvTasks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mContext = this.getActivity();
    }

    private void initListeners() {
        fabItemAddItem.setOnClickListener(mGlobal_OnClickListener);
        itemAddLocation.setOnClickListener(mGlobal_OnClickListener);
        rvTasks.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvTasks, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (thTasks[position].toLowerCase().startsWith("eq")) {
                    if (rolePermissions.contains("AddEdit")) {
                        ToolhawkScanActivity(position);
                    } else {
                        showToast("you don't have permission to Equipment Info");
                    }

                }

                if (thTasks[position].toLowerCase().startsWith("tra")) {
                    if (rolePermissions.contains("Transfer")) {
                        ToolhawkScanActivity(position);
                    } else {
                        showToast("you don't have permission to Transfer");
                    }

                }
                if (thTasks[position].toLowerCase().startsWith("qu")) {
                    ToolhawkScanActivity(position);

                }
                if (thTasks[position].toLowerCase().startsWith("ma")) {
                    ToolhawkScanActivity(position);

                }
                if (thTasks[position].toLowerCase().startsWith("mo")) {
                    if (rolePermissions.contains("Move")) {
                        gotoToolhawkDepartmentActivity(position);
                    } else {
                        showToast("you don't have permission to Move");
                    }

                }
                if (thTasks[position].toLowerCase().startsWith("check in")) {
                    if (rolePermissions.contains("Checkin")) {
                        gotoToolhawkDepartmentActivity(position);
                    } else {
                        showToast("you don't have permission to Checkin");
                    }

                }

                if (thTasks[position].toLowerCase().startsWith("check out")) {
                    if (rolePermissions.contains("Checkout")) {
                        gotoToolhawkDepartmentActivity(position);
                    } else {
                        showToast("you don't have permission to Checkout");
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
