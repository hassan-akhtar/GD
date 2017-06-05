package com.ets.gd.ToolHawk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class MoveAdapter extends RecyclerView.Adapter<MoveAdapter.MyViewHolder> {

    private List<com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber> jobNumberList = new ArrayList<com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber>();
    private List<ToolhawkEquipment> equipmentList = new ArrayList<ToolhawkEquipment>();
    private List<ETSLocations> etsLocationsList = new ArrayList<ETSLocations>();
    Context mContext;
    String type;
    MyViewHolder myViewHolder;

    public MoveAdapter(Context context, List<com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber> assetList, String type) {
        this.jobNumberList = assetList;
        this.type = type;
        this.mContext = context;
    }

    public MoveAdapter(Context context, String type, List<ToolhawkEquipment> assetList) {
        this.equipmentList = assetList;
        this.type = type;
        this.mContext = context;
    }


    public MoveAdapter(List<ETSLocations> assetList, String type, Context context) {
        this.etsLocationsList = assetList;
        this.type = type;
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

        if (type.toLowerCase().startsWith("job")) {
            JobNumber jobNumber = jobNumberList.get(position);
            holder.tvTitle.setText("" + jobNumber.getCode());
            if (null!=jobNumber.getDescription()) {
                holder.tvDesc.setText("" + jobNumber.getDescription());
            } else {
                holder.tvDesc.setText("N/A");
            }
        } else if (type.toLowerCase().startsWith("loc")) {
            ETSLocations etsLocations = etsLocationsList.get(position);
            holder.tvTitle.setText("" + etsLocations.getCode());
            if (null!=etsLocations.getDescription()) {
                holder.tvDesc.setText("" + etsLocations.getDescription());
            } else {
                holder.tvDesc.setText("N/A");
            }
        } else if (type.toLowerCase().startsWith("asset")) {
            ToolhawkEquipment toolhawkEquipment = equipmentList.get(position);
            holder.tvTitle.setText("" + toolhawkEquipment.getCode());
            holder.txtDesc.setText("Manufacturer:");
            if (null!=toolhawkEquipment.getManufacturer()) {
                holder.tvDesc.setText("" + toolhawkEquipment.getManufacturer().getCode());
            }else{
                holder.tvDesc.setText("N/A");
            }
        }
    }


    @Override
    public int getItemCount() {
        if (type.toLowerCase().startsWith("job")) {
            return jobNumberList.size();
        } else if (type.toLowerCase().startsWith("loc")) {
            return etsLocationsList.size();
        } else if (type.toLowerCase().startsWith("asset")) {
            return equipmentList.size();
        }else {
            return 0;
        }

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc, txtDesc;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            txtDesc = (TextView) view.findViewById(R.id.tvConstructionDescp);
        }
    }
}
