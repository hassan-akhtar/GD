package com.ets.gd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ets.gd.Adapters.CustomerAdapter;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;

public class CustomerFragment extends Fragment {

    private View rootView;
    private RecyclerView rvCustomers;
    private CustomerAdapter customerAdapter;
    CommonActions ca;

    public CustomerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customer, container, false);
        initViews();
        initObj();
        initListeners();
        return rootView;
    }

    private void initViews() {
        rvCustomers = (RecyclerView) rootView.findViewById(R.id.rvCustomers);
    }

    private void initObj() {
        ca = new CommonActions(getActivity());
    }

    private void initListeners() {
    }

}
