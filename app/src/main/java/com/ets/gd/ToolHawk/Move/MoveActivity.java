package com.ets.gd.ToolHawk.Move;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.CommonToolhawkDepartmentActivity;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;

public class MoveActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvDepartment;
    String departmentName, taskName;
    ImageView ivBack, ivTick;
    RelativeLayout rlJob, rlLocation, rlAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

        initViews();
        initObj();
        initListeners();
        setupView();

    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);

        rlJob = (RelativeLayout) findViewById(R.id.rlJob);
        rlLocation = (RelativeLayout) findViewById(R.id.rlLocation);
        rlAssets = (RelativeLayout) findViewById(R.id.rlAssets);

        taskName = getIntent().getStringExtra("taskName");
        departmentName = getIntent().getStringExtra("departmentName");
        tvDepartment.setText("" + departmentName);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskName);

    }

    private void initObj() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlJob.setOnClickListener(mGlobal_OnClickListener);
        rlLocation.setOnClickListener(mGlobal_OnClickListener);
        rlAssets.setOnClickListener(mGlobal_OnClickListener);
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

                case R.id.ivTick: {

                    break;
                }

                case R.id.rlJob: {

                    if (0 != DataManager.getInstance().getAllJobNumberList().size()) {
                        Intent in = new Intent(MoveActivity.this, ToolhawkScanActivityWithList.class);
                        in.putExtra("taskType", taskName);
                        in.putExtra("department", departmentName);
                        in.putExtra("scanType", "Job Number");
                        startActivity(in);
                    }else{
                        showToast("No Job number Found in "+departmentName);
                    }
                    break;
                }

                case R.id.rlLocation: {

                    int cusID = 0;
                    if (null != DataManager.getInstance().getDepartmentByCode(departmentName)) {
                        cusID = DataManager.getInstance().getDepartmentByCode(departmentName).getCustomerID();
                    }
                    if (0!=DataManager.getInstance().getAllCustomerETSLocations(cusID).size()) {
                        Intent in = new Intent(MoveActivity.this, ToolhawkScanActivityWithList.class);
                        in.putExtra("taskType", taskName);
                        in.putExtra("department", departmentName);
                        in.putExtra("scanType", "Location");
                        startActivity(in);
                    } else {
                        showToast("No Location(s) Found in "+departmentName);
                    }
                    break;
                }

                case R.id.rlAssets: {

                    int cusID = 0;
                    if (null != DataManager.getInstance().getDepartmentByCode(departmentName)) {
                        cusID = DataManager.getInstance().getDepartmentByCode(departmentName).getID();
                    }
                    if (0!=DataManager.getInstance().getAllDepToolhawkEquipment(cusID).size()) {
                        Intent in = new Intent(MoveActivity.this, ToolhawkScanActivityWithList.class);
                        in.putExtra("taskType", taskName);
                        in.putExtra("department", departmentName);
                        in.putExtra("scanType", "Asset");
                        startActivity(in);
                    } else {
                        showToast("No Asset(s) Found in "+departmentName);
                    }
                    break;
                }
            }
        }

    };


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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

