package com.ets.gd.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.R;

public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.MyViewHolder>{
    String[] fbTasks;
    int[] fbTasksImages;
    private RecyclerView.ViewHolder viewHolder;

    public AssetsAdapter( String[] fbTasks, int[] fbTasksImages ) {
        this.fbTasks = fbTasks;
        this.fbTasksImages =fbTasksImages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_common_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.code.setText(fbTasks[position]);
        holder.ivIcon.setBackgroundResource(fbTasksImages[position]);
    }

    @Override
    public int getItemCount() {
        return fbTasks.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView code;
        private ImageView ivIcon;
        public MyViewHolder(View view) {
            super(view);
            code = (TextView) view.findViewById(R.id.tvTaskName);
            ivIcon =(ImageView) view.findViewById(R.id.ivIcon);
        }
    }
}
