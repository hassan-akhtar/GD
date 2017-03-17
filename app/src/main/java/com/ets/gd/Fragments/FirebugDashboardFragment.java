package com.ets.gd.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.BaseActivity;
import com.ets.gd.Adapters.AssetsAdapter;
import com.ets.gd.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class FirebugDashboardFragment extends Fragment {

    private static FloatingActionMenu fab;
    private static FloatingActionButton fabItemAddAsset;
    String[] fbTasks = {"View Information", "Move Assets", "Transfer Assets", "Unit Inspection", "Route Inspection"};
    int[] fbTasksImages = {R.drawable.ic_transfer, R.drawable.ic_transfer, R.drawable.ic_transfer, R.drawable.ic_transfer, R.drawable.ic_transfer};
    View rootView;
    RecyclerView rvTasks;
    AssetsAdapter adapter;
    Context mContext;
    TextView tvCompanyValue;
    ImageView ivChangeCompany;
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
    }

    private void initObj() {
        adapter = new AssetsAdapter(fbTasks,fbTasksImages);
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
        ivChangeCompany.setOnClickListener(mGlobal_OnClickListener);
        rvTasks.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvTasks, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                showToast(fbTasks[position]);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
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
            }
        }

    };

}
