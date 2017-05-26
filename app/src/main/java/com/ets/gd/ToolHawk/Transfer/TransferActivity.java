package com.ets.gd.ToolHawk.Transfer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;

public class TransferActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvDepartment, tvEquip, tvTransfer, tvCancel;
    String departmentName, eqName, taskName;
    ImageView ivBack, ivTick;
    Spinner spDep, spLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        initViews();
        initObj();
        initListeners();
        setupView();
    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvDepartment = (TextView) findViewById(R.id.tvDepartment);
        tvTransfer = (TextView) findViewById(R.id.tvTransfer);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvEquip = (TextView) findViewById(R.id.tvEquip);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        spDep = (Spinner) findViewById(R.id.spDep);
        spLoc = (Spinner) findViewById(R.id.spLoc);
        taskName = getIntent().getStringExtra("taskName");
        eqName = getIntent().getStringExtra("eqName");
        tvEquip.setText("" + eqName);
        departmentName = getIntent().getStringExtra("departmentName");
        tvDepartment.setText("" + departmentName);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("" + taskName);

    }

    private void initObj() {
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvTransfer.setOnClickListener(mGlobal_OnClickListener);
        tvCancel.setOnClickListener(mGlobal_OnClickListener);
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

                case R.id.tvTransfer: {
                    showToast("Transfer");
                    break;
                }

                case R.id.tvCancel: {
                    finish();
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
