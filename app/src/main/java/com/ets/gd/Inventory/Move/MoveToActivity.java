package com.ets.gd.Inventory.Move;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Activities.ToolhawkScanActivityWithList;

public class MoveToActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvMaterial;
    String materialName, taskName;
    ImageView ivBack, ivTick;
    RelativeLayout rlJob, rlLocation;
    boolean isMultiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_move);

        initViews();
        initObj();
        initListeners();
        setupView();

    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvMaterial = (TextView) findViewById(R.id.tvMaterial);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);

        rlJob = (RelativeLayout) findViewById(R.id.rlJob);
        rlLocation = (RelativeLayout) findViewById(R.id.rlLocation);

        taskName = getIntent().getStringExtra("taskType");
        materialName = getIntent().getStringExtra("materialName");
        isMultiple = getIntent().getBooleanExtra("isMultiple", false);
        tvMaterial.setText("" + materialName);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskName);

        if(isMultiple){
            tvMaterial.setText(""+materialName+",...");
        }else{
            tvMaterial.setText(" "+materialName);
        }

    }

    private void initObj() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        rlJob.setOnClickListener(mGlobal_OnClickListener);
        rlLocation.setOnClickListener(mGlobal_OnClickListener);
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

                    Intent in = new Intent(MoveToActivity.this, InventoryScanActivityWithList.class);
                    in.putExtra("taskType", taskName);
                    in.putExtra("scanType", "User");
                    in.putExtra("material", materialName);
                    in.putExtra("isMultiple", isMultiple);
                    startActivity(in);
                    break;
                }

                case R.id.rlLocation: {

                    Intent in = new Intent(MoveToActivity.this, InventoryScanActivityWithList.class);
                    in.putExtra("taskType", taskName);
                    in.putExtra("scanType", "Container");
                    in.putExtra("material", materialName);
                    in.putExtra("isMultiple", isMultiple);
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

