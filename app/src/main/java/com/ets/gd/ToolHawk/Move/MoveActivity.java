package com.ets.gd.ToolHawk.Move;

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

import com.ets.gd.R;

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
        rlLocation  = (RelativeLayout) findViewById(R.id.rlLocation);
        rlAssets = (RelativeLayout) findViewById(R.id.rlAssets);

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
                    showToast("JOb Number");
                    break;
                }

                case R.id.rlLocation: {
                    showToast("Loc");
                    break;
                }

                case R.id.rlAssets: {
                    showToast("Asset");
                    break;
                }
            }
        }

    };


    void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
    private boolean checkValidation() {
        return false;
    }
}

