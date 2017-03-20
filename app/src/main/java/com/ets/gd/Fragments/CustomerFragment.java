package com.ets.gd.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.BaseActivity;
import com.ets.gd.Adapters.CustomerAdapter;
import com.ets.gd.Constants.Constants;
import com.ets.gd.Models.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment implements MyCallBack {

    private View rootView;
    private RecyclerView rvCustomers;
    private CustomerAdapter customerAdapter;
    TextView companiesCount;
    private List<Customer> customerList;

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
        getCustomersCall();
        return rootView;
    }

    private void initViews() {
        rvCustomers = (RecyclerView) rootView.findViewById(R.id.rvCustomers);
        companiesCount  = (TextView) rootView.findViewById(R.id.tvCompCount);
    }

    private void initObj() {
        ca = new CommonActions(getActivity());
        customerList = new ArrayList<>();

        BaseActivity.currentFragment = new CustomerFragment();
    }

    private void initListeners() {

        rvCustomers.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(getActivity(), rvCustomers, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                BaseActivity.refreshMainViewByNew(new FirebugDashboardFragment());
                EventBus.getDefault().post(customerList.get(position).getCode());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    void getCustomersCall() {
        Customer customer = new Customer();
        customer.setCode("Bow wow Animal Hospital");
        customerList.add(customer);
        customer = new Customer();
        customer.setCode("DSI");
        customerList.add(customer);
        customer = new Customer();
        customer.setCode("Hiltop Daycare");
        customerList.add(customer);
        customer = new Customer();
        customer.setCode("Spring Valley");
        customerList.add(customer);
        customer = new Customer();
        customer.setCode("Bruce and Co. Traders");
        customerList.add(customer);
        companiesCount.setText(""+customerList.size());
        customerAdapter = new CustomerAdapter(getActivity(),customerList);
        rvCustomers.setAdapter(customerAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvCustomers.setLayoutManager(mLayoutManager);
        rvCustomers.setItemAnimator(new DefaultItemAnimator());
        customerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onSuccess(ResponseDTO responseDTO) {

        switch (responseDTO.getCallBackId()) {

            case Constants.RESPONSE_CUSTOMERS:
                if (responseDTO != null) {

                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onFailure(ResponseDTO errorDTO) {
        CommonActions.DismissesDialog();
        if (404 == errorDTO.getCode())
            Toast.makeText(getActivity(), R.string.error_404_msg, Toast.LENGTH_LONG).show();
        else if (1 == errorDTO.getCode())
            Toast.makeText(getActivity(), R.string.error_poor_con, Toast.LENGTH_LONG).show();
        else if (400 == errorDTO.getCode()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.txt_login)
                    .setMessage(R.string.msg_login_failed)
                    .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else if (500 == errorDTO.getCode())
            Toast.makeText(getActivity(), R.string.error_404_msg, Toast.LENGTH_LONG).show();

        else
            Toast.makeText(getActivity(), R.string.error_con_timeout, Toast.LENGTH_LONG).show();
    }
}
