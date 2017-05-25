package com.ets.gd.ToolHawk.Maintenance;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ets.gd.R;

public class MaintenanceActivity extends AppCompatActivity {


    TextView tbTitleTop, tbTitleBottom, tvAssetID, tvSave, tvCancel;
    String assetID;
    ImageView ivBack, ivTick;
    CheckBox cbUpdateServiceDate;
    Spinner spCategory, spAction;
    EditText etPrice, etMaintDate, etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        initViews();
        initObj();
        initListeners();
        setupView();
        hideKeyboard();

    }

    private void initViews() {

        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvAssetID = (TextView) findViewById(R.id.tvAssetID);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spAction = (Spinner) findViewById(R.id.spAction);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etMaintDate = (EditText) findViewById(R.id.etMaintDate);
        etNote = (EditText) findViewById(R.id.etNote);
        cbUpdateServiceDate = (CheckBox) findViewById(R.id.cbUpdateServiceDate);
        assetID = getIntent().getStringExtra("assetID");
        tvAssetID.setText("" + assetID);
        tbTitleTop.setText("Toolhawk");
        tbTitleBottom.setText("Maintenance");


/*        ColorStateList colorStateList = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{

                        Color.WHITE //disabled
                        , Color.parseColor("#66bcb0") //enabled

                }
        );

        cbUpdateServiceDate.setSupportButtonTintList(colorStateList);*/
    }

    private void initObj() {
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvSave.setOnClickListener(mGlobal_OnClickListener);
        tvCancel.setOnClickListener(mGlobal_OnClickListener);
    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
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

                case R.id.tvSave: {
                    finish();
                    break;
                }
                case R.id.tvCancel: {
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
