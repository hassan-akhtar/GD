package com.ets.gd.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder>{
    List<EquipmentNote> lvNotes = new ArrayList<EquipmentNote>();

    public NoteAdapter(List<EquipmentNote> lvNotes ) {
        this.lvNotes = lvNotes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.noteTitle.setText(lvNotes.get(position).getModifiedTime());
        holder.noteDescription.setText(lvNotes.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return lvNotes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView noteTitle, noteDescription;
        public MyViewHolder(View view) {
            super(view);
            noteTitle = (TextView) view.findViewById(R.id.noteTitle);
            noteDescription =(TextView) view.findViewById(R.id.noteDescription);
        }
    }
}
