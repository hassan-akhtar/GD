package com.ets.gd.Inventory.Fragments;


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
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Inventory.Activities.CommonMaterialScanActivity;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;


public class InventoryDashboardFragment extends Fragment {


    View rootView;
    String[] thTasks = {"Move", "Issue", "Receive"};
    int[] thTasksImages = {R.drawable.ic_move_op, R.drawable.ic_issue, R.drawable.ic_receive,};
    RecyclerView rvTasks;
    AssetsAdapter adapter;
    Context mContext;
    SharedPreferencesManager sharedPreferencesManager;

    public InventoryDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_inventory_dashboard, container, false);

        initViews();
        initObj();
        initListeners();

        return rootView;
    }


    private void initViews() {
        rvTasks = (RecyclerView) rootView.findViewById(R.id.lvTasks);
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        adapter = new AssetsAdapter(thTasks, thTasksImages);
        BaseActivity.currentFragment = new InventoryDashboardFragment();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvTasks.setLayoutManager(mLayoutManager);
        rvTasks.setItemAnimator(new DefaultItemAnimator());
        rvTasks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mContext = this.getActivity();
    }


    private void initListeners() {
        rvTasks.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvTasks, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //showToast("" + thTasks[position]);
                if (thTasks[position].toLowerCase().startsWith("mo")) {

                    Intent in = new Intent(getActivity(), CommonMaterialScanActivity.class);
                    in.putExtra("taskType",thTasks[position]);
                    startActivity(in);
                } else  if (thTasks[position].toLowerCase().startsWith("iss")) {

                    Intent in = new Intent(getActivity(), CommonMaterialScanActivity.class);
                    in.putExtra("taskType",thTasks[position]);
                    startActivity(in);
                } else  if (thTasks[position].toLowerCase().startsWith("rec")) {
                    Intent in = new Intent(getActivity(), CommonMaterialScanActivity.class);
                    in.putExtra("taskType",thTasks[position]);
                    startActivity(in);

                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

}
