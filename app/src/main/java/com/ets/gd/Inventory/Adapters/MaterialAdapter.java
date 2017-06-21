package com.ets.gd.Inventory.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.Department;
import com.ets.gd.NetworkLayer.ResponseDTOs.Inventory;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MyViewHolder> {

    private List<Inventory> locList = new ArrayList<Inventory>();
    Context mContext;
    MyViewHolder myViewHolder;

    public MaterialAdapter(Context context, List<Inventory> locList) {
        this.locList = locList;
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
        Inventory inv = locList.get(position);
        if (null!=DataManager.getInstance().getETSLocationByIDOnly(inv.getLocationID())) {
            holder.tvTitle.setText(""+ DataManager.getInstance().getETSLocationByIDOnly(inv.getLocationID()).getCode());
        }else if (null!=DataManager.getInstance().getToolhawkEquipmentByID(inv.getEquipmentID())) {
            holder.tvTitle.setText(""+ DataManager.getInstance().getToolhawkEquipmentByID(inv.getEquipmentID()).getCode());
        } else{
            holder.tvTitle.setText("N/A");
        }
        holder.tvDesc.setText(""+inv.getQuantity());
        holder.tvConstructionDescp.setText("Quantity:");

    }


    @Override
    public int getItemCount() {
            return locList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc, tvConstructionDescp;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvConstructionDescp = (TextView) view.findViewById(R.id.tvConstructionDescp);
        }
    }
}
