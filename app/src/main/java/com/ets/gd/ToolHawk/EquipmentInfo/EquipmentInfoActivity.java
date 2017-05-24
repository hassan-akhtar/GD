package com.ets.gd.ToolHawk.EquipmentInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.Models.Department;
import com.ets.gd.R;
import com.ets.gd.ToolHawk.Adapters.DepartmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInfoActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom;
    String taskType;
    ImageView ivBack, ivTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_info);
        initViews();
        initObj();
        initListeners();
        setupView();
        hideKeyboard();
    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);

        taskType = getIntent().getStringExtra("taskType");
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Equipment Info");



    }

    private void initObj() {
    }



    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
    }

    private void setupView() {
        if(taskType.startsWith("vie")){
            ivTick.setVisibility(View.GONE);
        }else{
            ivTick.setVisibility(View.VISIBLE);
        }

    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
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
            }
        }

    };

    private boolean checkValidation() {
        return false;
    }
}
