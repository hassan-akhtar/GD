package com.ets.gd.ToolHawk.EquipmentInfo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ets.gd.Adapters.NoteAdapter;
import com.ets.gd.Adapters.NoteAdapterToolHawk;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.FireBug.ViewInformation.AddNoteFragment;
import com.ets.gd.FireBug.ViewInformation.AssetInformationFragment;
import com.ets.gd.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNoteTH;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ToolHawkNotesActivity extends AppCompatActivity {

    View rootView;
    public static List<EquipmentNoteTH> lstNotes = new ArrayList<EquipmentNoteTH>();
    FloatingActionButton fbAddNote;
    NoteAdapterToolHawk mAdapter;
    RecyclerView rvNotes;
    ToolhawkEquipment toolhawkEquipment;
    SharedPreferencesManager sharedPreferencesManager;
    String eqCode, actionType;
    TextView tbTitleTop, tbTitleBottom;
    ImageView ivBack, ivTick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_hawk_notes);


        initViews();
        initObj();
        initListeners();

    }

    private void initViews() {
        fbAddNote = (FloatingActionButton) findViewById(R.id.thAddNote);
        rvNotes = (RecyclerView) findViewById(R.id.rvNotes);
        ivTick = (ImageView) findViewById(R.id.ivTick);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tbTitleTop = (TextView) findViewById(R.id.tbTitleTop);
        tbTitleBottom = (TextView) findViewById(R.id.tbTitleBottom);
        tbTitleTop.setText("ToolHawk");
        tbTitleBottom.setText("Add/View Note");
        eqCode = getIntent().getStringExtra("eqCode");
        actionType = getIntent().getStringExtra("actionType");
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(ToolHawkNotesActivity.this);
        ViewAssetInformationActivity.currentFragment = new AddNoteFragment();

        //  if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
        setViewForViewAsset();
        //  } else {
        //    setViewForVAddAsset();
        //}

    }


    void setViewForViewAsset() {
        lstNotes.clear();
        toolhawkEquipment = DataManager.getInstance().getToolhawkEquipment(eqCode);
        if (null!=toolhawkEquipment) {
            lstNotes = DataManager.getInstance().getAllTHNotes(toolhawkEquipment.getCode());
        }

        for (int i = 0; i < lstNotes.size(); i++) {
            if (null != lstNotes.get(i).getModifiedTime()) {
                String title = lstNotes.get(i).getModifiedTime();
                if (title!=null && title.contains("T")) {
                    String[] separated = title.split("T");
                    String[] newFormat = separated[0].split("-");
                    title = "" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0];
                }
                lstNotes.get(i).setModifiedTime(title);
            }

        }
        mAdapter = new NoteAdapterToolHawk(lstNotes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ToolHawkNotesActivity.this);
        rvNotes.setLayoutManager(mLayoutManager);
        rvNotes.setItemAnimator(new DefaultItemAnimator());
        rvNotes.setAdapter(mAdapter);

    }


    void setViewForVAddAsset() {
        lstNotes.clear();
        mAdapter = new NoteAdapterToolHawk(lstNotes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ToolHawkNotesActivity.this);
        rvNotes.setLayoutManager(mLayoutManager);
        rvNotes.setItemAnimator(new DefaultItemAnimator());
        rvNotes.setAdapter(mAdapter);


    }


    private void initListeners() {
        fbAddNote.setOnClickListener(mGlobal_OnClickListener);
        ivTick.setOnClickListener(mGlobal_OnClickListener);
        ivBack.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.thAddNote: {
                    LayoutInflater li = LayoutInflater.from(ToolHawkNotesActivity.this);
                    View dialogView = li.inflate(R.layout.dialog_add_note, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            ToolHawkNotesActivity.this);
                    alertDialogBuilder.setTitle("Add Note");
                    alertDialogBuilder.setView(dialogView);
                    final EditText title = (EditText) dialogView
                            .findViewById(R.id.etTitle);
                    title.setVisibility(View.GONE);
                    final EditText desc = (EditText) dialogView
                            .findViewById(R.id.etNote);
                    if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        desc.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("ADD",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            if (!"".equals(desc.getText().toString().trim())) {
                                                int ID = 0;

                                                if (null != toolhawkEquipment && 0 != toolhawkEquipment.getID()) {
                                                    ID = toolhawkEquipment.getID();
                                                }

                                                EquipmentNoteTH note = new EquipmentNoteTH();
                                                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                                                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                                                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                                note.setModifiedTime(sdf.format(new Date()).toString());
                                                note.setEquipmentID(ID);
                                                note.setNote(desc.getText().toString().trim());
                                                note.setModifiedBy(sharedPreferencesManager.getString(SharedPreferencesManager.LOGGED_IN_USER_ID));
                                                lstNotes.add(note);

                                            } else {
                                                Toast.makeText(ToolHawkNotesActivity.this, "Please enter a note and try again", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    break;
                }

                case R.id.ivTick: {
                    if (0!=lstNotes.size()) {
                        int ID = 0;

                        if (null != toolhawkEquipment && 0 != toolhawkEquipment.getID()) {
                            ID = toolhawkEquipment.getID();
                        }
                       DataManager.getInstance().deleteAllTHNotes(eqCode);
                        DataManager.getInstance().addUpdateTHAssetNote(ID, eqCode, sharedPreferencesManager.getInt(SharedPreferencesManager.AFTER_SYNC_CUSTOMER_ID), lstNotes);
                        mAdapter.notifyDataSetChanged();
                        if ("viewAsset".equals(actionType)) {
                            showToast("Asset's Note(s) Updated");
                        } else {
                            showToast("Asset's Note(s) Added");
                        }
                    }else{
                        showToast("Please add Note(s) first!");
                    }
                    break;
                }


                case R.id.ivBack: {
                    finish();
                    break;
                }

            }
        }

    };

    void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}
