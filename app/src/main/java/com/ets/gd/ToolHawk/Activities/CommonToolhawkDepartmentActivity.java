package com.ets.gd.ToolHawk.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ets.gd.R;

public class CommonToolhawkDepartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_toolhawk_department);
        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {
    }

    private void initObj() {
    }

    private void initListeners() {
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
//                case R.id.btnLogin: {
//
//                    break;
//                }
            }
        }

    };


    private boolean checkValidation() {
        return false;
    }
}
