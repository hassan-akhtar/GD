package com.ets.gd.ToolHawk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.Models.ToolhawkAsset;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;


public class QuickCountAdapter extends RecyclerView.Adapter<QuickCountAdapter.MyViewHolder> {

    private List<ToolhawkEquipment> assetList = new ArrayList<ToolhawkEquipment>();
    Context mContext;
    String code;
    MyViewHolder myViewHolder;

    public QuickCountAdapter(Context context, List<ToolhawkEquipment> assetList) {
        this.assetList = assetList;
        this.mContext = context;
    }


    public QuickCountAdapter(Context context, List<ToolhawkEquipment> assetList, String code) {
        this.code = code;
        this.assetList = assetList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quick_count_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        myViewHolder = holder;
        ToolhawkEquipment asset = assetList.get(position);
        holder.tvName.setText("" + asset.getCode());
        holder.tvAssetCode.setVisibility(View.GONE);
        holder.txtAssetCode.setVisibility(View.GONE);
        if (null!=code && !"eq".equals(code)) {
            if (null!=asset.getETSLocation()) {
                holder.tvLocCode.setText("" + asset.getETSLocation().getCode());
            }else {
                holder.tvLocCode.setText("N/A" );
            }
        }else{
            if (null!=asset.getEquipmentLocationInfo()) {
                holder.tvLocCode.setText("" + asset.getEquipmentLocationInfo().getLocation());
            }else {
                holder.tvLocCode.setText("N/A" );
            }
        }
        if (asset.isContainer()) {
            holder.tvParent.setText("YES");
        } else {
            holder.tvParent.setText("NO");
        }

    }


    @Override
    public int getItemCount() {
        return assetList.size();

    }

    public void removeAt(int position) {
        assetList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, assetList.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAssetCode ,txtAssetCode, tvLocCode, tvParent;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAssetCode = (TextView) view.findViewById(R.id.tvAssetCode);
            txtAssetCode = (TextView) view.findViewById(R.id.txtAssetCode);
            tvLocCode = (TextView) view.findViewById(R.id.tvLocCode);
            tvParent = (TextView) view.findViewById(R.id.tvParent);
        }
    }
}
