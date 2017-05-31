package com.ets.gd.ToolHawk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.Models.JobNumber;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class JobNumberAdapter extends RecyclerView.Adapter<JobNumberAdapter.MyViewHolder> {

    private List<JobNumber> jobNumberList = new ArrayList<JobNumber>();
    Context mContext;
    MyViewHolder myViewHolder;

    public JobNumberAdapter(Context context, List<JobNumber> assetList) {
        this.jobNumberList = assetList;
        this.mContext = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.department_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        myViewHolder = holder;
        JobNumber jobNumber = jobNumberList.get(position);
        holder.tvTitle.setText(""+jobNumber.getCode());
        holder.tvDesc.setText(""+jobNumber.getName());

    }


    @Override
    public int getItemCount() {
            return jobNumberList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        }
    }
}
