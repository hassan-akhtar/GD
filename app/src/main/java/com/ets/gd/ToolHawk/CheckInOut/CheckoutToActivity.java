package com.ets.gd.ToolHawk.CheckInOut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;

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
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
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
                    Intent in = new Intent(CheckoutToActivity.this, JobNumberActivity.class);
                    in.putExtra("taskType", taskName);
                    in.putExtra("department", departmentName);
                    in.putExtra("isUser", false);
                    startActivity(in);

                    break;
                }

                case R.id.rlUser: {
                    Intent in = new Intent(CheckoutToActivity.this, UserActivity.class);
                    in.putExtra("taskType", taskName);
                    in.putExtra("department", departmentName);
                    startActivity(in);
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

