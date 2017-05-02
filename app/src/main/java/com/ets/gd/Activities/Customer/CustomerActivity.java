package com.ets.gd.Activities.Customer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Activities.FireBug.Move.LocationSelectionActivity;
import com.ets.gd.Activities.FireBug.Move.SelectLocationActivity;
import com.ets.gd.Activities.Other.BaseActivity;
import com.ets.gd.Adapters.CustomerAdapter;
import com.ets.gd.Constants.Constants;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.CustomerFragment;
import com.ets.gd.Fragments.FirebugDashboardFragment;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.Models.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.AllCustomers;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity implements MyCallBack {


    private RecyclerView rvCustomers;
    private CustomerAdapter customerAdapter;
    TextView companiesCount;
    private List<AllCustomers> customerList;
    CommonActions ca;
    ImageView ivBack, ivTick;
    TextView tbTitleBottom;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initViews();
        initObj();
        initListeners();
        getCustomersCall();
    }

    private void initViews() {
        rvCustomers = (RecyclerView) findViewById(R.id.rvCustomers);
        companiesCount = (TextView) findViewById(R.id.tvCompCount);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivTick.setVisibility(View.GONE);
    }

    private void initObj() {
        ca = new CommonActions(CustomerActivity.this);
        sharedPreferencesManager = new SharedPreferencesManager(CustomerActivity.this);
        customerList = new ArrayList<>();
        tbTitleBottom.setText("Select Company");
    }

    private void initListeners() {

        ivBack.setOnClickListener(mGlobal_OnClickListener);

        rvCustomers.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(CustomerActivity.this, rvCustomers, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent in = new Intent(CustomerActivity.this, SelectLocationActivity.class);
                sharedPreferencesManager.setInt(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_ID,customerList.get(position).getID());
                sharedPreferencesManager.setString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME,customerList.get(position).getCode());
                in.putExtra("location",customerList.get(position).getCode());
                startActivity(in);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    void getCustomersCall() {
       /* Customer customer = new Customer();
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
        companiesCount.setText(""+customerList.size());*/
        customerList = DataManager.getInstance().getAllCustomerList(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        customerAdapter = new CustomerAdapter(CustomerActivity.this,customerList);
        rvCustomers.setAdapter(customerAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomerActivity.this);
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
            Toast.makeText(CustomerActivity.this, R.string.error_404_msg, Toast.LENGTH_LONG).show();
        else if (1 == errorDTO.getCode())
            Toast.makeText(CustomerActivity.this, R.string.error_poor_con, Toast.LENGTH_LONG).show();
        else if (400 == errorDTO.getCode()) {
            new AlertDialog.Builder(CustomerActivity.this)
                    .setTitle(R.string.txt_login)
                    .setMessage(R.string.msg_login_failed)
                    .setNegativeButton(getString(R.string.txt_close), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else if (500 == errorDTO.getCode())
            Toast.makeText(CustomerActivity.this, R.string.error_404_msg, Toast.LENGTH_LONG).show();

        else
            Toast.makeText(CustomerActivity.this, R.string.error_con_timeout, Toast.LENGTH_LONG).show();
    }

}
