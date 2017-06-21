package com.ets.gd.Inventory.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocations;
import com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber;
import com.ets.gd.NetworkLayer.ResponseDTOs.MobileUser;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class UserContainerAdapter extends RecyclerView.Adapter<UserContainerAdapter.MyViewHolder> {

    private List<ToolhawkEquipment> containerList = new ArrayList<ToolhawkEquipment>();
    private List<ETSLocations> userList = new ArrayList<ETSLocations>();
    Context mContext;
    String type;
    MyViewHolder myViewHolder;

    public UserContainerAdapter(Context context, List<ETSLocations> userList, String type) {
        this.userList = userList;
        this.type = type;
        this.mContext = context;
    }

    public UserContainerAdapter(Context context, String type, List<ToolhawkEquipment> assetList) {
        this.containerList = assetList;
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

        if (type.toLowerCase().startsWith("loc")) {
            ETSLocations user = userList.get(position);
            holder.tvTitle.setText("" + user.getCode());
            holder.txtDesc.setText("Description:");
            if (null!=user.getDescription()) {
                holder.tvDesc.setText("" + user.getDescription());
            } else {
                holder.tvDesc.setText("N/A");
            }
        } else if (type.toLowerCase().startsWith("con")) {
            ToolhawkEquipment eq = containerList.get(position);
            holder.tvTitle.setText("" + eq.getCode());
            holder.txtDesc.setText("Department:");
            if (null!=eq.getDepartment()) {
                holder.tvDesc.setText("" + eq.getDepartment().getCode());
            } else {
                holder.tvDesc.setText("N/A");
            }
        }
    }


    @Override
    public int getItemCount() {
        if (type.toLowerCase().startsWith("loc")) {
            return userList.size();
        } else if (type.toLowerCase().startsWith("con")) {
            return containerList.size();
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
