package com.ets.gd.Fragments;


import android.content.DialogInterface;
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

import com.ets.gd.Activities.FireBug.ViewInformation.ViewAssetInformationActivity;
import com.ets.gd.Adapters.NoteAdapter;
import com.ets.gd.Models.Note;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;


public class AddNoteFragment extends Fragment {

    View rootView;
    List<Note> lstNotes = new ArrayList<Note>();
    FloatingActionButton fbAddNote;
    NoteAdapter mAdapter;
    RecyclerView rvNotes;
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
        rvNotes  = (RecyclerView) rootView.findViewById(R.id.rvNotes);
    }

    private void initObj() {
        ViewAssetInformationActivity.currentFragment = new AddNoteFragment();
        Note note = new Note();
        note.setNoteTitle("Already Entered Note 1");
        note.setNoteDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        lstNotes.add(note);
        note.setNoteTitle("Already Entered Note 2");
        note.setNoteDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        lstNotes.add(note);




        if ("viewAsset".equals(ViewAssetInformationActivity.actionType)) {
            setViewForViewAsset();
        } else {
            setViewForVAddAsset();
        }

    }


    void setViewForViewAsset() {

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
                    final EditText desc = (EditText) dialogView
                            .findViewById(R.id.etNote);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("ADD",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            Note note = new Note();
                                            note.setNoteTitle(title.getText().toString().trim());
                                            note.setNoteDescription(desc.getText().toString().trim());
                                            lstNotes.add(note);
                                            mAdapter.notifyDataSetChanged();
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
