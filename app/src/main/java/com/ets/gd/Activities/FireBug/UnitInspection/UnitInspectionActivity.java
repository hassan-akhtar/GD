package com.ets.gd.Activities.FireBug.UnitInspection;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Models.StateVO;
import com.ets.gd.R;

import java.util.ArrayList;

public class UnitInspectionActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvSave, tvReplace, tvCancel, tvAssetName, tvAssetOtherInfo;
    ImageView ivBack, ivTick, ivChangeCompany;
    Spinner spInspType, spInspectionResult;
    String compName, tag, loc, desp, deviceType;
    LinearLayout rlBottomsheet;
    EditText etStatusCode;
    RelativeLayout rlCodes;
    int posInspType, posInspectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_inspection);

        initViews();
        initObj();
        initListeners();

    }


    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvAssetName = (TextView) findViewById(R.id.tvAssetName);
        etStatusCode = (EditText) findViewById(R.id.etStatusCode);
        tvAssetOtherInfo = (TextView) findViewById(R.id.tvAssetOtherInfo);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvReplace = (TextView) findViewById(R.id.tvReplace);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        spInspType = (Spinner) findViewById(R.id.spInspType);
        rlBottomsheet = (LinearLayout) findViewById(R.id.rlBottomsheet);
        rlCodes = (RelativeLayout) findViewById(R.id.rlCodes);
        spInspectionResult = (Spinner) findViewById(R.id.spInspectionResult);
        tbTitleBottom.setText("Inspect Assets");
        ivTick.setVisibility(View.GONE);
        ivChangeCompany.setVisibility(View.GONE);
        tvReplace.setVisibility(View.GONE);

    }

    private void initObj() {
        compName = getIntent().getStringExtra("compName");
        tag = getIntent().getStringExtra("tag");
        loc = getIntent().getStringExtra("loc");
        desp = getIntent().getStringExtra("desp");
        deviceType = getIntent().getStringExtra("deviceType");

        tvCompanyValue.setText("" + compName);
        tvAssetName.setText("" + tag);
        tvAssetOtherInfo.setText("" + desp + ", " + deviceType + ", " + loc);


        ArrayAdapter<String> dataAdapterInspType = new ArrayAdapter<String>(UnitInspectionActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inspTypes));
        dataAdapterInspType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInspType.setAdapter(dataAdapterInspType);

        ArrayAdapter<String> dataAdapterInspectionResult = new ArrayAdapter<String>(UnitInspectionActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inspResults));
        dataAdapterInspectionResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInspectionResult.setAdapter(dataAdapterInspectionResult);

    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvSave.setOnClickListener(mGlobal_OnClickListener);
        tvReplace.setOnClickListener(mGlobal_OnClickListener);
        tvCancel.setOnClickListener(mGlobal_OnClickListener);
        etStatusCode.setOnClickListener(mGlobal_OnClickListener);
        spInspType.setOnItemSelectedListener(this);
        spInspectionResult.setOnItemSelectedListener(this);


    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.tvSave: {
                    break;
                }

                case R.id.etStatusCode: {
                    hideKeyboard();
                    if (rlCodes.getVisibility()==View.GONE) {
                        rlCodes.setVisibility(View.VISIBLE);
                    } else {
                        rlCodes.setVisibility(View.GONE);
                    }
                    break;
                }

                case R.id.tvReplace: {
                    break;
                }

                case R.id.tvCancel: {
                    finish();
                    break;
                }

            }
        }

    };


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etStatusCode.getWindowToken(), 0);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spInspType: {
                posInspType = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                if (0 == position) {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            break;

            case R.id.spInspectionResult: {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER_VERTICAL;

                posInspectionResult = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();

                if ("fail".equals(strSelectedState.toLowerCase())) {
                    tvReplace.setVisibility(View.VISIBLE);
                    lp.weight = 1;
                    tvReplace.setLayoutParams(lp);
                    tvSave.setLayoutParams(lp);
                    tvCancel.setLayoutParams(lp);

                } else {
                    tvReplace.setVisibility(View.GONE);
                    lp.weight = 0;
                    tvReplace.setLayoutParams(lp);
                    lp.weight = 1.5f;
                    tvSave.setLayoutParams(lp);
                    tvCancel.setLayoutParams(lp);
                }
                if (0 == position) {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.DKGRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
