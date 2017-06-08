package com.ets.gd.ToolHawk.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
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

import com.ets.gd.Adapters.CustomerAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.Customer.CustomerActivity;
import com.ets.gd.Fragments.FragmentDrawer;
import com.ets.gd.NetworkLayer.ResponseDTOs.Department;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Adapters.DepartmentAdapter;
import com.ets.gd.ToolHawk.CheckInOut.CheckoutToActivity;
import com.ets.gd.ToolHawk.CheckInOut.UserActivity;
import com.ets.gd.ToolHawk.Move.MoveActivity;

import java.util.ArrayList;
import java.util.List;

public class CommonToolhawkDepartmentActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom;
    String taskType;
    ImageView ivBack, ivTick;
    private List<Department> depList = new ArrayList<Department>();
    private List<Department> depFilteredList = new ArrayList<Department>();
    ImageView ivCross;
    DepartmentAdapter mAdapter;
    RecyclerView rvDepartments;
    EditText etSearch;
    RelativeLayout rlSearchView, rlHeader;
    boolean isSearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_toolhawk_department);
        initViews();
        initObj();
        initListeners();
        setupView();
        setupDummyDepList();
    }


    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        etSearch = (EditText) findViewById(R.id.etSearch);
        rlSearchView = (RelativeLayout) findViewById(R.id.rlSearchView);
        rlHeader = (RelativeLayout) findViewById(R.id.rlHeader);
        ivCross = (ImageView) findViewById(R.id.ivCross);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        rvDepartments = (RecyclerView) findViewById(R.id.rvDepartments);

        taskType = getIntent().getStringExtra("taskType");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskType);
    }

    private void initObj() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        ivCross.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);


        rvDepartments.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(CommonToolhawkDepartmentActivity.this, rvDepartments, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                hideKeyboard();


                if (tbTitleBottom.getText().toString().toLowerCase().startsWith("mov")) {
                    Intent in = new Intent(CommonToolhawkDepartmentActivity.this, MoveActivity.class);
                    in.putExtra("taskName", tbTitleBottom.getText().toString());
                    if (!isSearching) {
                        in.putExtra("departmentName", depList.get(position).getCode());
                    } else {
                        in.putExtra("departmentName", depFilteredList.get(position).getCode());
                    }
                    startActivity(in);

                } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("check out")) {
                    Intent in = new Intent(CommonToolhawkDepartmentActivity.this, CheckoutToActivity.class);
                    in.putExtra("taskName", tbTitleBottom.getText().toString());
                    if (!isSearching) {
                        in.putExtra("departmentName", depList.get(position).getCode());
                    } else {
                        in.putExtra("departmentName", depFilteredList.get(position).getCode());
                    }
                    startActivity(in);

                } else if (tbTitleBottom.getText().toString().toLowerCase().startsWith("check in")) {
                    Intent in = new Intent(CommonToolhawkDepartmentActivity.this, UserActivity.class);
                    in.putExtra("taskType", tbTitleBottom.getText().toString());
                    if (!isSearching) {
                        in.putExtra("departmentName", depList.get(position).getCode());
                    } else {
                        in.putExtra("departmentName", depFilteredList.get(position).getCode());
                    }
                    startActivity(in);

                }

                depFilteredList.clear();
                rlHeader.setVisibility(View.VISIBLE);
                rlSearchView.setVisibility(View.GONE);
                etSearch.setText("");
                mAdapter = new DepartmentAdapter(CommonToolhawkDepartmentActivity.this, depList);
                rvDepartments.setAdapter(mAdapter);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CommonToolhawkDepartmentActivity.this);
                rvDepartments.setLayoutManager(mLayoutManager);
                rvDepartments.setItemAnimator(new DefaultItemAnimator());
                mAdapter.notifyDataSetChanged();
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
                    depFilteredList.clear();
                    mAdapter = new DepartmentAdapter(CommonToolhawkDepartmentActivity.this, depList);
                    rvDepartments.setAdapter(mAdapter);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CommonToolhawkDepartmentActivity.this);
                    rvDepartments.setLayoutManager(mLayoutManager);
                    rvDepartments.setItemAnimator(new DefaultItemAnimator());
                    mAdapter.notifyDataSetChanged();
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


    public  void updateAdapterForSearchKey(String query){
        query = query.toString().toLowerCase();

        depFilteredList.clear();
        for (int i = 0; i < depList.size(); i++) {

            final String name = depList.get(i).getCode().toLowerCase();
            if (name.startsWith(query)) {
                depFilteredList.add(depList.get(i));
            }
        }

        if (0 == depFilteredList.size()) {
            mAdapter = new DepartmentAdapter(CommonToolhawkDepartmentActivity.this, depFilteredList);
            rvDepartments.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();  // data set changed
        } else {
            rvDepartments.setLayoutManager(new LinearLayoutManager(CommonToolhawkDepartmentActivity.this));
            mAdapter = new DepartmentAdapter(CommonToolhawkDepartmentActivity.this, depFilteredList);
            rvDepartments.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();  // data set changed
        }

    }

    void showToast(String msg) {
        Toast.makeText(CommonToolhawkDepartmentActivity.this, msg, Toast.LENGTH_LONG).show();
    }


    private void setupDummyDepList() {

        depList = DataManager.getInstance().getAllDepartments();

        mAdapter = new DepartmentAdapter(CommonToolhawkDepartmentActivity.this, depList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CommonToolhawkDepartmentActivity.this);
        rvDepartments.setLayoutManager(mLayoutManager);
        rvDepartments.setItemAnimator(new DefaultItemAnimator());
        rvDepartments.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void setupView() {
        // ivTick.setVisibility(View.GONE);
        ivTick.setImageDrawable(getResources().getDrawable(R.drawable.green_search_glass));
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
                    depFilteredList.clear();
                    rlHeader.setVisibility(View.VISIBLE);
                    rlSearchView.setVisibility(View.GONE);
                    etSearch.setText("");
                    mAdapter = new DepartmentAdapter(CommonToolhawkDepartmentActivity.this, depList);
                    rvDepartments.setAdapter(mAdapter);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CommonToolhawkDepartmentActivity.this);
                    rvDepartments.setLayoutManager(mLayoutManager);
                    rvDepartments.setItemAnimator(new DefaultItemAnimator());
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }

    };

    public void showKeyboard() {
        etSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etSearch.getWindowToken(), 0);
    }

    private final BroadcastReceiver mMoveCompleteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");

            if (message.startsWith("fin")) {
                finish();
            }

        }
    };

    private boolean checkValidation() {
        return false;
    }
}
