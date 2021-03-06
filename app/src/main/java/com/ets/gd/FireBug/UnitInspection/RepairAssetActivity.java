package com.ets.gd.FireBug.UnitInspection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class RepairAssetActivity extends AppCompatActivity {


    ImageView ivBack, ivChangeCompany, ivTick;
    TextView tbTitleTop, tbTitleBottom, tvCompanyValue, tvTagID, tvRepair, tvSpare;
    String compName, tagId, repairSelection;
    List<Locations> allRepairs = new ArrayList<Locations>();
    List<Locations> allSpares = new ArrayList<Locations>();
    List<FireBugEquipment> customerRepairs = new ArrayList<FireBugEquipment>();
    List<FireBugEquipment> customerSpares = new ArrayList<FireBugEquipment>();
    List<FireBugEquipment> allCustomerAssets = new ArrayList<FireBugEquipment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_asset);
        initViews();
        initObj();
        initListeners();

    }


    private void initViews() {

        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivChangeCompany = (ImageView) findViewById(R.id.ivChangeCompany);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tvCompanyValue = (TextView) findViewById(R.id.tvCompanyValue);
        tvRepair = (TextView) findViewById(R.id.tvRepair);
        tvSpare = (TextView) findViewById(R.id.tvSpare);
        tvTagID = (TextView) findViewById(R.id.tvEquipmentCode);
        ivChangeCompany.setVisibility(View.GONE);
        ivTick.setVisibility(View.GONE);


    }

    private void initObj() {
        tbTitleBottom.setText("Replace Asset");
        compName = getIntent().getStringExtra("compName");
        tagId = getIntent().getStringExtra("tagCode");
        tvCompanyValue.setText("" + compName);
        tvTagID.setText("" + tagId);
        allCustomerAssets = DataManager.getInstance().getAllCompanyAssets(DataManager.getInstance().getCustomerByCode(tvCompanyValue.getText().toString()).getID());
        allRepairs = DataManager.getInstance().getAllRepairLocations();
        allSpares = DataManager.getInstance().getAllSpareLocations();


        if (null!=allCustomerAssets && 0!=allCustomerAssets.size() && null!=allRepairs && 0!=allRepairs.size() ) {
            for (int i = 0; i < allCustomerAssets.size(); i++) {
                for (int j = 0; j < allRepairs.size(); j++) {
                    if (null!=allCustomerAssets.get(i).getLocation()) {
                        if (allCustomerAssets.get(i).getLocation().getCode().toLowerCase().equals(allRepairs.get(j).getCode().toLowerCase())) {
                            customerRepairs.add(allCustomerAssets.get(i));
                            break;
                        }
                    }
                }
            }

        }

        if (null!=allCustomerAssets && 0!=allCustomerAssets.size() && null!=allSpares && 0!=allSpares.size() ) {
            for (int i = 0; i < allCustomerAssets.size(); i++) {
                for (int j = 0; j < allSpares.size(); j++) {
                    if (null!=allCustomerAssets.get(i).getLocation()) {
                        if (allCustomerAssets.get(i).getLocation().getCode().toLowerCase().equals(allSpares.get(j).getCode().toLowerCase())) {
                            customerSpares.add(allCustomerAssets.get(i));
                            break;
                        }
                    }
                }
            }

        }

        tvRepair.setText("Repairs "+"("+customerRepairs.size()+")");
        tvSpare.setText("Spares "+"("+customerSpares.size()+")");
    }


    private void initListeners() {
        ivBack.setOnClickListener(mGlobal_OnClickListener);
        tvRepair.setOnClickListener(mGlobal_OnClickListener);
        tvSpare.setOnClickListener(mGlobal_OnClickListener);

    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.ivBack: {
                    RepairAssetActivity.this.finish();
                    break;
                }

                case R.id.tvRepair: {
                    ReplaceAssetActivity.replaceType = "Repairs";


                    if (null!=allRepairs && 0!=allRepairs.size() && null!=allCustomerAssets && 0!=allCustomerAssets.size()) {

                        if (null!=customerRepairs && 0!=customerRepairs.size()) {
                            repairSelection = "Repairs";
                            SelectAssetActivity.assetList.clear();
                            SelectAssetActivity.assetList =customerRepairs;
                            Intent in = new Intent(RepairAssetActivity.this,SelectAssetActivity.class);
                            in.putExtra("compName",tvCompanyValue.getText().toString().trim());
                            in.putExtra("repairSelection",repairSelection);
                            startActivity(in);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"No Asset(s) found in " +tvCompanyValue.getText().toString()+" Repairs.",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"No Asset(s) found in " +tvCompanyValue.getText().toString()+" Repairs.",Toast.LENGTH_SHORT).show();
                    }

                    break;
                }


                case R.id.tvSpare: {
                    ReplaceAssetActivity.replaceType = "Spares";
                    if (null!=allSpares && 0!=allSpares.size() && null!=allCustomerAssets && 0!=allCustomerAssets.size()) {


                        if (null!=customerSpares && 0!=customerSpares.size()) {
                            repairSelection = "Spares";
                            SelectAssetActivity.assetList.clear();
                            SelectAssetActivity.assetList =customerSpares;
                            Intent in = new Intent(RepairAssetActivity.this,SelectAssetActivity.class);
                            in.putExtra("compName",tvCompanyValue.getText().toString().trim());
                            in.putExtra("repairSelection",repairSelection);
                            startActivity(in);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"No Asset(s) found in " +tvCompanyValue.getText().toString()+" Spares.",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"No Asset(s) found in " +tvCompanyValue.getText().toString()+" Spares.",Toast.LENGTH_SHORT).show();
                    }

                    break;
                }

            }
        }

    };
}
