package com.ets.gd.FireBug.Customer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.FireBug.Move.SelectLocationActivity;
import com.ets.gd.Adapters.CustomerAdapter;
import com.ets.gd.Constants.Constants;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.NetworkLayer.ResponseDTOs.AllCustomers;
import com.ets.gd.NetworkLayer.ResponseDTOs.ResponseDTO;
import com.ets.gd.NetworkLayer.Service.MyCallBack;
import com.ets.gd.R;
import com.ets.gd.Utils.CommonActions;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity implements MyCallBack {


    private RecyclerView rvCustomers;
    private CustomerAdapter customerAdapter;
    TextView companiesCount;
    ImageView ivCross;
    EditText etSearch;
    private List<AllCustomers> customerList;
    private List<AllCustomers> customerFilteredList = new ArrayList<AllCustomers>();
    CommonActions ca;
    RelativeLayout rlHeader, rlSearchView;
    ImageView ivBack, ivTick;
    TextView tbTitleBottom;
    boolean isSearching = false;
    SharedPreferencesManager sharedPreferencesManager;
    String compName;

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
        rlHeader = (RelativeLayout) findViewById(R.id.rlHeader);
        ivCross = (ImageView) findViewById(R.id.ivCross);
        etSearch = (EditText) findViewById(R.id.etSearch);
        rlSearchView = (RelativeLayout) findViewById(R.id.rlSearchView);
        companiesCount = (TextView) findViewById(R.id.tvCompCount);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
       // ivTick.setVisibility(View.GONE);
        ivTick.setImageDrawable(getResources().getDrawable(R.drawable.green_search_glass));
    }

    private void initObj() {
        ca = new CommonActions(CustomerActivity.this);
        sharedPreferencesManager = new SharedPreferencesManager(CustomerActivity.this);
        customerList = new ArrayList<>();
        tbTitleBottom.setText("Select Company");
        compName = getIntent().getStringExtra("compName");
    }

    private void initListeners() {

        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivCross.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);

        rvCustomers.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(CustomerActivity.this, rvCustomers, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (isSearching) {
                    if (!customerFilteredList.get(position).getCode().toLowerCase().equals(compName.toLowerCase())) {
                        Intent in = new Intent(CustomerActivity.this, SelectLocationActivity.class);
                        sharedPreferencesManager.setInt(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_ID, customerFilteredList.get(position).getID());
                        sharedPreferencesManager.setString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME, customerFilteredList.get(position).getCode());
                        in.putExtra("compName", customerFilteredList.get(position).getCode());
                        in.putExtra("cusID", customerFilteredList.get(position).getID());
                        in.putExtra("type", "transfer");
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(CustomerActivity.this, "You can not transfer Asset to same company", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CustomerActivity.this, "Please select any other company", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (!customerList.get(position).getCode().toLowerCase().equals(compName.toLowerCase())) {
                        Intent in = new Intent(CustomerActivity.this, SelectLocationActivity.class);
                        sharedPreferencesManager.setInt(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_ID, customerList.get(position).getID());
                        sharedPreferencesManager.setString(SharedPreferencesManager.CURRENT_TRANSFER_CUSTOMER_NAME, customerList.get(position).getCode());
                        in.putExtra("compName", customerList.get(position).getCode());
                        in.putExtra("cusID", customerList.get(position).getID());
                        in.putExtra("type", "transfer");
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(CustomerActivity.this, "You can not transfer Asset to same company", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CustomerActivity.this, "Please select any other company", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if("".equals(s.toString())){
                    isSearching=false;
                    customerFilteredList.clear();
                    companiesCount.setText("" + customerList.size());
                    customerAdapter = new CustomerAdapter(CustomerActivity.this, customerList);
                    rvCustomers.setAdapter(customerAdapter);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomerActivity.this);
                    rvCustomers.setLayoutManager(mLayoutManager);
                    rvCustomers.setItemAnimator(new DefaultItemAnimator());
                    customerAdapter.notifyDataSetChanged();
                }else{
                    isSearching = true;
                    updateAdapterForSearchKey(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etSearch.getWindowToken(), 0);
    }

    public void showKeyboard() {
        etSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public  void updateAdapterForSearchKey(String query){
        query = query.toString().toLowerCase();
        
            customerFilteredList.clear();
            for (int i = 0; i < customerList.size(); i++) {

                final String name = customerList.get(i).getCode().toLowerCase();
                if (name.startsWith(query)) {
                    customerFilteredList.add(customerList.get(i));
                }
            }

            if (0 == customerFilteredList.size()) {
                companiesCount.setText("0");
                customerAdapter = new CustomerAdapter(CustomerActivity.this, customerFilteredList);
                rvCustomers.setAdapter(customerAdapter);
                customerAdapter.notifyDataSetChanged();  // data set changed
            } else {
                companiesCount.setText(""+customerFilteredList.size());
                rvCustomers.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
                customerAdapter = new CustomerAdapter(CustomerActivity.this, customerFilteredList);
                rvCustomers.setAdapter(customerAdapter);
                customerAdapter.notifyDataSetChanged();  // data set changed
            }
        
    }
    
    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.ivTick: {
                    showKeyboard();
                    rlHeader.setVisibility(View.INVISIBLE);
                    rlSearchView.setVisibility(View.VISIBLE);
                    etSearch.setText("");
                    break;
                }


                case R.id.ivCross: {
                    hideKeyboard();
                    customerFilteredList.clear();
                    rlHeader.setVisibility(View.VISIBLE);
                    rlSearchView.setVisibility(View.GONE);
                    etSearch.setText("");
                    companiesCount.setText("" + customerList.size());
                    customerAdapter = new CustomerAdapter(CustomerActivity.this, customerList);
                    rvCustomers.setAdapter(customerAdapter);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CustomerActivity.this);
                    rvCustomers.setLayoutManager(mLayoutManager);
                    rvCustomers.setItemAnimator(new DefaultItemAnimator());
                    customerAdapter.notifyDataSetChanged();
                    break;
                }

            }
        }

    };


    void getCustomersCall() {

        customerList = DataManager.getInstance().getAllCustomerList();
        companiesCount.setText("" + customerList.size());
        customerAdapter = new CustomerAdapter(CustomerActivity.this, customerList);
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
