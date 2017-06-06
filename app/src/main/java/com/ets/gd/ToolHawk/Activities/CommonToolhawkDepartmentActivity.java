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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
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


    TextView  tbTitleTop, tbTitleBottom;
    String taskType;
    ImageView ivBack, ivTick;
    private List<Department> depList = new ArrayList<Department>();
    DepartmentAdapter mAdapter;
    RecyclerView rvDepartments;

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
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        rvDepartments  = (RecyclerView) findViewById(R.id.rvDepartments);

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

        rvDepartments.addOnItemTouchListener(new FragmentDrawer.RecyclerTouchListener(CommonToolhawkDepartmentActivity.this, rvDepartments, new FragmentDrawer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(tbTitleBottom.getText().toString().toLowerCase().startsWith("mov")){
                    Intent in = new Intent(CommonToolhawkDepartmentActivity.this, MoveActivity.class);
                    in.putExtra("taskName", tbTitleBottom.getText().toString());
                    in.putExtra("departmentName", depList.get(position).getCode());
                    startActivity(in);

                }else if(tbTitleBottom.getText().toString().toLowerCase().startsWith("check out")){
                    Intent in = new Intent(CommonToolhawkDepartmentActivity.this, CheckoutToActivity.class);
                    in.putExtra("taskName", tbTitleBottom.getText().toString());
                    in.putExtra("departmentName", depList.get(position).getCode());
                    startActivity(in);

                }else if(tbTitleBottom.getText().toString().toLowerCase().startsWith("check in")){
                    Intent in = new Intent(CommonToolhawkDepartmentActivity.this, UserActivity.class);
                    in.putExtra("taskType", tbTitleBottom.getText().toString());
                    in.putExtra("department", depList.get(position).getCode());
                    startActivity(in);

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    void showToast(String msg) {
        Toast.makeText(CommonToolhawkDepartmentActivity.this, msg, Toast.LENGTH_LONG).show();
    }


    private void setupDummyDepList() {

        depList = DataManager.getInstance().getAllDepartments();

        mAdapter = new DepartmentAdapter(CommonToolhawkDepartmentActivity.this,depList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CommonToolhawkDepartmentActivity.this);
        rvDepartments.setLayoutManager(mLayoutManager);
        rvDepartments.setItemAnimator(new DefaultItemAnimator());
        rvDepartments.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    private void setupView() {
        ivTick.setVisibility(View.GONE);
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
