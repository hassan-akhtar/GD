package com.ets.gd.FireBug.UnitInspection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
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

import com.ets.gd.Adapters.CheckBoxGroupView;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Interfaces.AssetReplaced;
import com.ets.gd.Models.Replace;
import com.ets.gd.NetworkLayer.RequestDTOs.UnitinspectionResult;
import com.ets.gd.NetworkLayer.RequestDTOs.InspectionStatusCodes;
import com.ets.gd.NetworkLayer.ResponseDTOs.DeviceTypeStatusCodes;
import com.ets.gd.NetworkLayer.ResponseDTOs.StatusCode;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import io.realm.RealmList;

public class UnitInspectionActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener, CheckBox.OnCheckedChangeListener, AssetReplaced {

    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvSave, tvReplace, tvCancel, tvAssetName, tvAssetOtherInfo;
    ImageView ivBack, ivTick, ivChangeCompany;
    Spinner spInspType, spInspectionResult;
    String compName, tag, loc, desp, deviceType, location;
    LinearLayout rlBottomsheet;
    TextView etStatusCode;
    RelativeLayout rlCodes;
    int posInspType, posInspectionResult, deviceTypeID, equipmentID;
    //CheckBox cbBracket, cbNozzel, cbDamaged, cbOperational, cbHose, cbRecharge, cbAccessible, cbTag;
    List<DeviceTypeStatusCodes> deviceTypeStatusCodes;
    List<String> statusCodesDescList = new ArrayList<String>();
    CheckBoxGroupView cbGroup;
    boolean isFail = false;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_inspection);
        initViews();
        initObj();
        initListeners();
    }


    private void initViews() {
/*        cbBracket = (CheckBox) findViewById(R.id.cbBracket);
        cbNozzel = (CheckBox) findViewById(R.id.cbNozzel);
        cbDamaged = (CheckBox) findViewById(R.id.cbDamaged);
        cbOperational = (CheckBox) findViewById(R.id.cbOperational);
        cbHose = (CheckBox) findViewById(R.id.cbHose);
        cbRecharge = (CheckBox) findViewById(R.id.cbRecharge);
        cbAccessible = (CheckBox) findViewById(R.id.cbAccessible);
        cbTag = (CheckBox) findViewById(R.id.cbTag);*/
        cbGroup = (CheckBoxGroupView) findViewById(R.id.cbGroup);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tvAssetName = (TextView) findViewById(R.id.tvLocName);
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


        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            spInspType.setBackgroundColor(Color.parseColor("#ffffff"));
            spInspectionResult.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }

    private void initObj() {
        ReplaceAssetActivity.assetReplaced = this;
        sharedPreferencesManager = new SharedPreferencesManager(UnitInspectionActivity.this);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMoveCompleteBroadcastReceiver,
                new IntentFilter("move-complete"));
        compName = getIntent().getStringExtra("compName");
        tag = getIntent().getStringExtra("tag");
        loc = getIntent().getStringExtra("loc");
        location = getIntent().getStringExtra("location");
        desp = getIntent().getStringExtra("desp");
        deviceTypeID = getIntent().getIntExtra("deviceTypeID", 0);
        deviceType = getIntent().getStringExtra("deviceType");
        equipmentID = getIntent().getIntExtra("equipmentID", 0);
        tvCompanyValue.setText("" + compName);
        tvAssetName.setText("" + tag);
        tvAssetOtherInfo.setText("" + desp + ", " + deviceType + ", " + location);

        deviceTypeStatusCodes = DataManager.getInstance().getDeviceStatusCodesList(deviceTypeID);
        ArrayAdapter<String> dataAdapterInspType = new ArrayAdapter<String>(UnitInspectionActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inspTypes));
        dataAdapterInspType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInspType.setAdapter(dataAdapterInspType);

        ArrayAdapter<String> dataAdapterInspectionResult = new ArrayAdapter<String>(UnitInspectionActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inspResults));
        dataAdapterInspectionResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInspectionResult.setAdapter(dataAdapterInspectionResult);

        setupStatusCodes();

    }

    private void setupStatusCodes() {


        ColorStateList colorStateList = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{

                        Color.WHITE //disabled
                        , Color.parseColor("#66bcb0") //enabled

                }
        );

        for (int i = 0; i < deviceTypeStatusCodes.size(); i++) {
            AppCompatCheckBox cb = new AppCompatCheckBox(this);
            // cb.setHighlightColor(getResources().getColor(R.color.colorAccent));
            // cb.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            cb.setSupportButtonTintList(colorStateList);
            cb.setTag(i);
            statusCodesDescList.add(deviceTypeStatusCodes.get(i).getStatusCode().getDescription());
            cb.setText(deviceTypeStatusCodes.get(i).getStatusCode().getDescription());
            cbGroup.put(cb);
        }
    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvSave.setOnClickListener(mGlobal_OnClickListener);
        tvReplace.setOnClickListener(mGlobal_OnClickListener);
        tvCancel.setOnClickListener(mGlobal_OnClickListener);
        etStatusCode.setOnClickListener(mGlobal_OnClickListener);
        spInspType.setOnItemSelectedListener(this);
        spInspectionResult.setOnItemSelectedListener(this);
       /* cbBracket.setOnCheckedChangeListener(this);
        cbNozzel.setOnCheckedChangeListener(this);
        cbDamaged.setOnCheckedChangeListener(this);
        cbOperational.setOnCheckedChangeListener(this);
        cbHose.setOnCheckedChangeListener(this);
        cbRecharge.setOnCheckedChangeListener(this);
        cbAccessible.setOnCheckedChangeListener(this);
        cbTag.setOnCheckedChangeListener(this);*/
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }

                case R.id.tvSave: {
                    if (checkValidation(isFail)) {

                        UnitinspectionResult inspectionResult = new UnitinspectionResult();
                        inspectionResult.setEquipmentID(equipmentID);
                        inspectionResult.setReplaced(false);
                        inspectionResult.setInspectionType(spInspType.getItemAtPosition(posInspType).toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                        String timeStamp = sdf.format(new Date()).toString();
                        inspectionResult.setInspectionDate(timeStamp);
                        inspectionResult.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
                        boolean result = false;
                        if (spInspectionResult.getItemAtPosition(posInspectionResult).toString().toLowerCase().startsWith("p")) {
                            result = true;
                        }
                        inspectionResult.setResult(result);

                        RealmList<InspectionStatusCodes> inspectionStatusCodes = new RealmList<InspectionStatusCodes>();

                        if (0 < cbGroup.getCheckedIds().size()) {

                            List<Object> lst = cbGroup.getCheckedIds();
                            lst.clear();
                            lst = cbGroup.getCheckedIds();

                            for (int i = 0; i < lst.size(); i++) {
                                int pos = Integer.parseInt(lst.get(i).toString());
                                StatusCode statusCode = new StatusCode();
                                statusCode = DataManager.getInstance().getStatusCodeID(statusCodesDescList.get(pos));
                                inspectionStatusCodes.add(new InspectionStatusCodes(statusCode.getID()));

                            }
                        }
                        inspectionResult.setInspectionStatusCodes(inspectionStatusCodes);

                        DataManager.getInstance().saveUnitInspectionResults(inspectionResult);
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
                    if (checkValidation(isFail)) {
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

    private boolean checkValidation(boolean isFail) {
        if (isFail) {
            if (0 == posInspType) {
                showToast("Please select Inspection Type");
            } else if (0 == posInspectionResult) {
                showToast("Please select Inspection Result");
            } else if (0 == cbGroup.getCheckedIds().size()) {
                showToast("Please select status Code(s)");
            } else {
                return true;
            }
            return false;
        } else {
            if (0 == posInspType) {
                showToast("Please select Inspection Type");
            } else if (0 == posInspectionResult) {
                showToast("Please select Inspection Result");
            } else {
                return true;
            }
            return false;
        }
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

    void saveInspectionAfterReplace(String replaceType, int newLocID, int newEquipID) {
        UnitinspectionResult inspectionResult = new UnitinspectionResult();
        inspectionResult.setEquipmentID(equipmentID);
        inspectionResult.setReplaced(true);
        inspectionResult.setInspectionType(spInspType.getItemAtPosition(posInspType).toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeStamp = sdf.format(new Date()).toString();
        inspectionResult.setInspectionDate(timeStamp);
        inspectionResult.setUserId(sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID));
        boolean result = false;
        if (spInspectionResult.getItemAtPosition(posInspectionResult).toString().toLowerCase().startsWith("p")) {
            result = true;
        }
        inspectionResult.setResult(result);

        RealmList<InspectionStatusCodes> inspectionStatusCodes = new RealmList<InspectionStatusCodes>();

        if (0 < cbGroup.getCheckedIds().size()) {

            List<Object> lst = cbGroup.getCheckedIds();
            lst.clear();
            lst = cbGroup.getCheckedIds();

            for (int i = 0; i < lst.size(); i++) {
                int pos = Integer.parseInt(lst.get(i).toString());
                StatusCode statusCode = new StatusCode();
                statusCode = DataManager.getInstance().getStatusCodeID(statusCodesDescList.get(pos));
                inspectionStatusCodes.add(new InspectionStatusCodes(statusCode.getID()));

            }
        }
        inspectionResult.setNewLocationID(newLocID);
        inspectionResult.setNewEquipmentID(newEquipID);
        inspectionResult.setReplaceType(replaceType);
        inspectionResult.setInspectionStatusCodes(inspectionStatusCodes);

        DataManager.getInstance().saveUnitInspectionResults(inspectionResult);
        showToast("Inspection completed Successfully");
        sendMessage("finish");
        finish();
    }


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
                tvReplace.setVisibility(View.GONE);
                lp.weight = 0;
                tvReplace.setLayoutParams(lp);
                lp.weight = 1.5f;
                tvSave.setLayoutParams(lp);
                tvCancel.setLayoutParams(lp);
                posInspectionResult = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();

                if ("fail".equals(strSelectedState.toLowerCase())) {
                    isFail = true;
                    tvReplace.setVisibility(View.VISIBLE);
                    lp.weight = 1;
                    tvReplace.setLayoutParams(lp);
                    tvSave.setLayoutParams(lp);
                    tvCancel.setLayoutParams(lp);

                } else {
                    isFail = false;
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


    }

    @Override
    public void AssetReplaced(Replace replace) {
        String message = replace.getMessage();
        String replaceType = replace.getReplaceType();
        int newLocID = replace.getNewLocID();
        int newEqipID = replace.getNewEqipID();

        if (message.startsWith("rep")) {
            saveInspectionAfterReplace(replaceType, newLocID, newEqipID);
        }
    }
}
