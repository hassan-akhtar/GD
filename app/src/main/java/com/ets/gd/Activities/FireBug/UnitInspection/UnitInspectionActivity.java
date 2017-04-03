package com.ets.gd.Activities.FireBug.UnitInspection;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Adapters.SpinnerAdapter;
import com.ets.gd.Models.StateVO;
import com.ets.gd.R;

import java.util.ArrayList;

public class UnitInspectionActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvSave, tvReplace, tvCancel, tvAssetName, tvAssetOtherInfo;
    ImageView ivBack, ivTick, ivChangeCompany;
    Spinner spInspType, spStatusCode, spInspectionResult;
    String compName, tag, loc, desp, deviceType;
    LinearLayout rlBottomsheet;
    ArrayList<StateVO> listVOs = new ArrayList<>();
    int posInspType, posStatusCode, posInspectionResult;

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
        tvAssetOtherInfo = (TextView) findViewById(R.id.tvAssetOtherInfo);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvReplace = (TextView) findViewById(R.id.tvReplace);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        spInspType = (Spinner) findViewById(R.id.spInspType);
        rlBottomsheet = (LinearLayout) findViewById(R.id.rlBottomsheet);
        spStatusCode = (Spinner) findViewById(R.id.spStatusCode);
        spInspectionResult = (Spinner) findViewById(R.id.spInspectionResult);
        tbTitleBottom.setText("Inspect Assets");
        ivTick.setVisibility(View.GONE);
        ivChangeCompany.setVisibility(View.GONE);

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


        final String[] select_qualification = {
                "Status Code", "Bracket", "Nozzle", "Damaged", "Operational",
                "Hose", "Recharge", "Not Accessible", "Tag"};


        for (int i = 0; i < select_qualification.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        SpinnerAdapter myAdapter = new SpinnerAdapter(getApplicationContext(), 0,
                listVOs);
        spStatusCode.setAdapter(myAdapter);


        ArrayAdapter<String> dataAdapterInspectionResult = new ArrayAdapter<String>(UnitInspectionActivity.this,android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.inspResults));
        dataAdapterInspectionResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInspectionResult.setAdapter(dataAdapterInspectionResult);

    }

    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvSave.setOnClickListener(mGlobal_OnClickListener);
        tvReplace.setOnClickListener(mGlobal_OnClickListener);
        tvCancel.setOnClickListener(mGlobal_OnClickListener);
        spInspType.setOnItemSelectedListener(this);
        spStatusCode.setOnItemSelectedListener(this);
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

            case R.id.spStatusCode: {
                posStatusCode = position;
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
                posInspectionResult = position;
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

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
