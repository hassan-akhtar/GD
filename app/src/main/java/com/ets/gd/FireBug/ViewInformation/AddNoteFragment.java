package com.ets.gd.FireBug.ViewInformation;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ets.gd.Adapters.NoteAdapter;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.R;
import com.ets.gd.Utils.SharedPreferencesManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class AddNoteFragment extends Fragment {

    View rootView;
    public static List<EquipmentNote> lstNotes = new ArrayList<EquipmentNote>();
    FloatingActionButton fbAddNote;
    NoteAdapter mAdapter;
    RecyclerView rvNotes;
    FireBugEquipment fireBugEquipment;
    SharedPreferencesManager sharedPreferencesManager;

    public AddNoteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_note, container, false);


        initViews();
        initObj();
        initListeners();

        return rootView;

    }

    private void initViews() {
        fbAddNote = (FloatingActionButton) rootView.findViewById(R.id.fbAddNote);
        rvNotes = (RecyclerView) rootView.findViewById(R.id.rvNotes);
    }

    private void initObj() {
        sharedPreferencesManager = new SharedPreferencesManager(getActivity());
        ViewAssetInformationActivity.currentFragment = new AddNoteFragment();

        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {
        lstNotes.clear();
        fireBugEquipment = ((ViewAssetInformationActivity) getActivity()).getEquipment();
        lstNotes = DataManager.getInstance().getAllNotes(fireBugEquipment.getCode());

        for(int i=0 ; i<lstNotes.size();i++){
            if (null!=lstNotes.get(i).getModifiedTime()) {
                String title = lstNotes.get(i).getModifiedTime();
                String[] separated = title.split("T");
                String[] newFormat = separated[0].split("-");
                title = "" + newFormat[1] + "/" + newFormat[2] + "/" + newFormat[0];
                lstNotes.get(i).setModifiedTime(title);
            }

        }
        mAdapter = new NoteAdapter(lstNotes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvNotes.setLayoutManager(mLayoutManager);
        rvNotes.setItemAnimator(new DefaultItemAnimator());
        rvNotes.setAdapter(mAdapter);

    }


    void setViewForVAddAsset() {
        lstNotes.clear();
        mAdapter = new NoteAdapter(lstNotes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvNotes.setLayoutManager(mLayoutManager);
        rvNotes.setItemAnimator(new DefaultItemAnimator());
        rvNotes.setAdapter(mAdapter);


    }



    private void initListeners() {
        fbAddNote.setOnClickListener(mGlobal_OnClickListener);
    }

    final View.OnClickListener mGlobal_OnClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.fbAddNote: {
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View dialogView = li.inflate(R.layout.dialog_add_note, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
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
                                            if (ViewAssetInformationActivity.isAssetAdded) {
                                                if (!"".equals(desc.getText().toString().trim())) {
                                                    int ID = 0;

                                                    if( null!=fireBugEquipment && 0!=fireBugEquipment.getID()){
                                                        ID = fireBugEquipment.getID();
                                                    }

                                                    EquipmentNote note = new EquipmentNote();
                                                   // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                                                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                                                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                                    note.setModifiedTime(sdf.format(new Date()).toString());
                                                    note.setEquipmentID(ID);
                                                    note.setNote(desc.getText().toString().trim());
                                                    note.setModifiedBy(sharedPreferencesManager.getString(SharedPreferencesManager.LOGGED_IN_USER_ID));
                                                    lstNotes.add(note);
                                                    ViewAssetInformationActivity.newNotesList.add(note);
                                                    mAdapter.notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(getActivity(), "Please enter a note and try again", Toast.LENGTH_LONG).show();
                                                }
                                            }else{
                                                Toast.makeText(getActivity(), "Please add an asset first", Toast.LENGTH_LONG).show();
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
            }
        }

    };

}
