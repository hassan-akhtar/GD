package com.ets.gd.ToolHawk.CheckOut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;
import com.ets.gd.ToolHawk.Move.MoveActivity;

public class CheckoutToActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvDepartment;
    String departmentName, taskName;
    ImageView ivBack, ivTick;
    RelativeLayout rlJob, rlUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_to);

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
        rlUser = (RelativeLayout) findViewById(R.id.rlUser);

        taskName = getIntent().getStringExtra("taskName");
        departmentName = getIntent().getStringExtra("departmentName");
        tvDepartment.setText("" + departmentName);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskName);

    }

    private void initObj() {
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlJob.setOnClickListener(mGlobal_OnClickListener);
        rlUser.setOnClickListener(mGlobal_OnClickListener);
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
                    showToast("job");
//                    Intent in = new Intent(CheckoutToActivity.this, ToolhawkScanActivityWithList.class);
//                    in.putExtra("taskType", taskName);
//                    in.putExtra("department", departmentName);
//                    in.putExtra("scanType", "Job Number");
//                    startActivity(in);

                    break;
                }

                case R.id.rlUser: {
                    showToast("user");
//                    Intent in = new Intent(CheckoutToActivity.this, ToolhawkScanActivityWithList.class);
//                    in.putExtra("taskType", taskName);
//                    in.putExtra("department", departmentName);
//                    in.putExtra("scanType", "Location");
//                    startActivity(in);
                    break;
                }

            }
        }

    };


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        return false;
    }
}

