package com.ets.gd.ToolHawk.Maintenance;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.ViewInformation.AssetInformationFragment;
import com.ets.gd.FireBug.ViewInformation.InspectionDatesFragment;
import com.ets.gd.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.NetworkLayer.RequestDTOs.EquipmentMaintenance;
import com.ets.gd.NetworkLayer.ResponseDTOs.Action;
import com.ets.gd.NetworkLayer.ResponseDTOs.Category;
import com.ets.gd.NetworkLayer.ResponseDTOs.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncCustomer;
import com.ets.gd.R;
import com.ets.gd.Utils.DatePickerFragmentEditText;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    TextView tbTitleTop, tbTitleBottom, tvAssetID, tvSave, tvCancel;
    String assetID;
    ImageView ivBack, ivTick;
    CheckBox cbUpdateServiceDate;
    Spinner spCategory, spAction;
    EditText etPrice, etMaintDate, etNote;
    int posAction, posCategory;
    List<Action> lstMaintenanceAction = new ArrayList<Action>();
    List<Category> lstCategory = new ArrayList<Category>();
    String[] categories;
    String[] actions;

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
        tvSave = (TextView) findViewById(R.id.tvTransfer);
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
        etMaintDate.setText("MM/DD/YYYY");
        etMaintDate.setTextColor(Color.GRAY);

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

        lstMaintenanceAction = DataManager.getInstance().getAllActions();
        lstCategory = DataManager.getInstance().getAllCategory();


        int sizeAction = lstMaintenanceAction.size() + 1;
        actions = new String[sizeAction];

        for (int i = 0; i < lstMaintenanceAction.size(); i++) {
            actions[i + 1] = lstMaintenanceAction.get(i).getCode();
        }
        actions[0] = "Please select an Action";
        ArrayAdapter<String> dataAdapterCustomer = new ArrayAdapter<String>(MaintenanceActivity.this, android.R.layout.simple_spinner_item, actions);
        dataAdapterCustomer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAction.setAdapter(dataAdapterCustomer);


        int sizeCategory = lstCategory.size() + 1;
        categories = new String[sizeCategory];

        for (int i = 0; i < lstCategory.size(); i++) {
            categories[i + 1] = lstCategory.get(i).getCode();
        }
        categories[0] = "Please select a Category";
        ArrayAdapter<String> dataAdapterCategory = new ArrayAdapter<String>(MaintenanceActivity.this, android.R.layout.simple_spinner_item, categories);
        dataAdapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(dataAdapterCategory);

    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvSave.setOnClickListener(mGlobal_OnClickListener);
        tvCancel.setOnClickListener(mGlobal_OnClickListener);
        etMaintDate.setOnClickListener(mGlobal_OnClickListener);
        spAction.setOnItemSelectedListener(this);
        spCategory.setOnItemSelectedListener(this);


        etMaintDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    hideKeyboard();
                    InspectionDatesFragment.viewID = v.getId();
                    DialogFragment newFragment = new DatePickerFragmentEditText();
                    newFragment.show(getFragmentManager(), "Date Picker");
                    etMaintDate.setTextColor(Color.BLACK);
                } else {

                }


            }
        });

    }

    private void setupView() {
        ivTick.setVisibility(View.GONE);
    }


    public void hideKeyboard() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                etPrice.getWindowToken(), 0);

        InputMethodManager imm2 = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm2.hideSoftInputFromWindow(
                etMaintDate.getWindowToken(), 0);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    finish();
                    break;
                }


                case R.id.etMaintDate: {
                    hideKeyboard();
                    InspectionDatesFragment.viewID = v.getId();
                    DialogFragment newFragment = new DatePickerFragmentEditText();
                    newFragment.show(getFragmentManager(), "Date Picker");
                    etMaintDate.setTextColor(Color.BLACK);
                    break;
                }

                case R.id.tvTransfer: {
                    if (checkValidation()) {
                        EquipmentMaintenance equipmentMaintenance = new EquipmentMaintenance();

                        equipmentMaintenance.setID(0);

                        if (null != DataManager.getInstance().getToolhawkEquipment(assetID)) {
                            equipmentMaintenance.setEquipmentID(DataManager.getInstance().getToolhawkEquipment(assetID).getID());
                        }

                        if (null != DataManager.getInstance().getCategory(spCategory.getItemAtPosition(posCategory).toString())) {
                            equipmentMaintenance.setMaintenanceCategoryID(DataManager.getInstance().getCategory(spCategory.getItemAtPosition(posCategory).toString()).getID());
                        }


                        if (null != DataManager.getInstance().getAction(spAction.getItemAtPosition(posAction).toString())) {
                            equipmentMaintenance.setMaintenanceActionID(DataManager.getInstance().getAction(spAction.getItemAtPosition(posAction).toString()).getID());
                        }


                        equipmentMaintenance.setCost("" + etPrice.getText().toString());
                        equipmentMaintenance.setMaintenanceDate("" + etMaintDate.getText().toString());
                        equipmentMaintenance.setNote("" + etNote.getText().toString());
                        if (cbUpdateServiceDate.isChecked()) {
                            equipmentMaintenance.setUpdateLastServiceDate(true);
                        } else {
                            equipmentMaintenance.setUpdateLastServiceDate(false);
                        }

                        DataManager.getInstance().saveResultMaintenance(equipmentMaintenance);
                        showToast("Equipment Maintenance Complete!");
                        sendMessage("finish");
                        finish();
                    }
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


    private void sendMessage(String msg) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("move-complete");
        intent.putExtra("message", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    private boolean checkValidation() {
        if (0 == posCategory) {
            showToast("Please select a Category");
        } else if (0 == posAction) {
            showToast("Please select an Action");
        } else if ("".equals(etPrice.getText().toString().trim())) {
            showToast("Please enter unit cost");
        } else if ("".equals(etMaintDate.getText().toString().trim())) {
            showToast("Please select Manufacturer date");
        } else {
            return true;
        }

        return false;
    }


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewID = parent.getId();
        switch (viewID) {
            case R.id.spCategory: {
                posCategory = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                try {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.spAction: {
                posAction = position;
                String strSelectedState = parent.getItemAtPosition(position).toString();
                try {
                    if (0 == position) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
