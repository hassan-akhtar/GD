package com.ets.gd.Activities.FireBug.UnitInspection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.R;

public class UnitInspectionActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener, CheckBox.OnCheckedChangeListener {

    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvSave, tvReplace, tvCancel, tvAssetName, tvAssetOtherInfo;
    ImageView ivBack, ivTick, ivChangeCompany;
    Spinner spInspType, spInspectionResult;
    String compName, tag, loc, desp, deviceType;
    LinearLayout rlBottomsheet;
    TextView etStatusCode;
    RelativeLayout rlCodes;
    int posInspType, posInspectionResult;
    CheckBox cbBracket, cbNozzel, cbDamaged, cbOperational, cbHose, cbRecharge, cbAccessible, cbTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_inspection);
        initViews();
        initObj();
        initListeners();
    }


    private void initViews() {
        cbBracket = (CheckBox) findViewById(R.id.cbBracket);
        cbNozzel = (CheckBox) findViewById(R.id.cbNozzel);
        cbDamaged = (CheckBox) findViewById(R.id.cbDamaged);
        cbOperational = (CheckBox) findViewById(R.id.cbOperational);
        cbHose = (CheckBox) findViewById(R.id.cbHose);
        cbRecharge = (CheckBox) findViewById(R.id.cbRecharge);
        cbAccessible = (CheckBox) findViewById(R.id.cbAccessible);
        cbTag = (CheckBox) findViewById(R.id.cbTag);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvAssetName = (TextView) findViewById(R.id.tvAssetName);
        etStatusCode = (TextView) findViewById(R.id.etStatusCode);
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
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReplaceCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
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
        cbBracket.setOnCheckedChangeListener(this);
        cbNozzel.setOnCheckedChangeListener(this);
        cbDamaged.setOnCheckedChangeListener(this);
        cbOperational.setOnCheckedChangeListener(this);
        cbHose.setOnCheckedChangeListener(this);
        cbRecharge.setOnCheckedChangeListener(this);
        cbAccessible.setOnCheckedChangeListener(this);
        cbTag.setOnCheckedChangeListener(this);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.tvSave: {
                    if (checkValidation()) {
                        showToast("Inspection completed Successfully");
                        sendMessage("finish");
                        finish();
                    }
                    break;
                }

                case R.id.etStatusCode: {
                    if (rlCodes.getVisibility() == View.GONE) {
                        rlCodes.setVisibility(View.VISIBLE);
                    } else {
                        rlCodes.setVisibility(View.GONE);
                    }
                    break;
                }

                case R.id.tvReplace: {
                    if (checkValidation()) {
                        Intent in = new Intent(UnitInspectionActivity.this, ReplaceAssetActivity.class);
                        in.putExtra("taskType", "Inspect Assets");
                        in.putExtra("compName", compName);
                        in.putExtra("code", tag);
                        startActivity(in);
                    }
                    break;
                }

                case R.id.tvCancel: {
                    finish();
                    break;
                }

            }
        }

    };

    private boolean checkValidation() {
        if (0 == posInspType) {
            showToast("Please select Inspection Type");
        } else if ("".equals(etStatusCode.getText().toString().trim())) {
            showToast("Please select status Code(s)");
        } else if (0 == posInspectionResult) {
            showToast("Please select Inspection Result");
        } else {
            return true;
        }
        return false;
    }

    private final BroadcastReceiver mReplaceCompleteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");

            if (message.startsWith("fin")) {
                finish();
            }

        }
    };


    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("move-complete");
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int viewID = buttonView.getId();
        switch (viewID) {
            case R.id.cbBracket: {

                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Bracket");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Bracket")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Bracket");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Bracket")) {
                        currentText = currentText.replace(",Bracket", "");
                    } else if (currentText.contains("Bracket")) {
                        currentText = currentText.replace("Bracket", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;

            case R.id.cbNozzel: {
                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Nozzel");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Nozzel")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Nozzel");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Nozzel")) {
                        currentText = currentText = currentText.replace(",Nozzel", "");
                    } else if (currentText.contains("Nozzel")) {
                        currentText = currentText = currentText.replace("Nozzel", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;


            case R.id.cbDamaged: {
                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Damaged");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Damaged")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Damaged");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Damaged")) {
                        currentText = currentText.replace(",Damaged", "");
                    } else if (currentText.contains("Damaged")) {
                        currentText = currentText.replace("Damaged", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;

            case R.id.cbOperational: {
                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Operational");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Operational")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Operational");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Operational")) {
                        currentText = currentText.replace(",Operational", "");
                    } else if (currentText.contains("Operational")) {
                        currentText = currentText.replace("Operational", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;

            case R.id.cbHose: {
                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Hose");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Hose")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Hose");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Hose")) {
                        currentText = currentText.replace(",Hose", "");
                    } else if (currentText.contains("Hose")) {
                        currentText = currentText.replace("Hose", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;

            case R.id.cbRecharge: {
                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Recharge");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Recharge")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Recharge");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Recharge")) {
                        currentText = currentText.replace(",Recharge", "");
                    } else if (currentText.contains("Recharge")) {
                        currentText = currentText.replace("Recharge", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;

            case R.id.cbAccessible: {
                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Accessible");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Accessible")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Accessible");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Accessible")) {
                        currentText = currentText.replace(",Accessible", "");
                    } else if (currentText.contains("Accessible")) {
                        currentText = currentText.replace("Accessible", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;

            case R.id.cbTag: {
                if (isChecked) {
                    if (etStatusCode.getText().toString().trim().toLowerCase().equals("")) {
                        etStatusCode.setText("Tag");
                    } else {
                        if (!etStatusCode.getText().toString().trim().contains("Tag")) {
                            etStatusCode.setText(etStatusCode.getText().toString().trim() + "," + "Tag");
                        }
                    }
                } else {
                    String currentText = etStatusCode.getText().toString().trim();
                    if (currentText.contains(",Tag")) {
                        currentText = currentText.replace(",Tag", "");
                    } else if (currentText.contains("Tag")) {
                        currentText = currentText.replace("Tag", "");
                    }
                    etStatusCode.setText(currentText);
                }
            }
            break;
        }
    }
}
