package com.ets.gd.Inventory.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.Material;
import com.ets.gd.Models.Note;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryScannedMaterialAdapter extends RecyclerView.Adapter<InventoryScannedMaterialAdapter.MyViewHolder> {

    private List<Material> materialList = new ArrayList<Material>();
    String type = "";
    TextDrawable drawable;
    Context mContext;
    MyViewHolder myViewHolder;

    public InventoryScannedMaterialAdapter(Context context, List<Material> materialList) {
        this.materialList = materialList;
        this.mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scanned_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        myViewHolder = holder;
        Material asset = materialList.get(position);
        holder.tvTag.setVisibility(View.GONE);
        holder.tvName.setText(""+asset.getName());
        if (asset.isLoc() && null!=DataManager.getInstance().getETSLocationByIDOnly(asset.getLocID())) {
            holder.tvQuantity.setText("Quantity: "+asset.getQuantity()+" Location:"+ DataManager.getInstance().getETSLocationByIDOnly(asset.getLocID()).getCode());
        } else if (!asset.isLoc() &&  null!=DataManager.getInstance().getToolhawkEquipmentByID(asset.getLocID())) {
            holder.tvQuantity.setText("Quantity: "+asset.getQuantity()+" Location:"+ DataManager.getInstance().getToolhawkEquipmentByID(asset.getLocID()).getCode());
        }else {
            holder.tvQuantity.setText("Quantity: "+asset.getQuantity());
        }


        drawable = TextDrawable.builder()
                        .beginConfig()
                        .endConfig()
                        .buildRound(asset.getName().substring(0, 1).toUpperCase(), mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.ivSelectableImage.setImageDrawable(drawable);
    }


    @Override
    public int getItemCount() {
            return materialList.size();
    }


    public void removeAt(int position) {
        materialList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, materialList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvTag, tvQuantity;
        private ImageView ivSelectableImage;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvQuantity = (TextView) view.findViewById(R.id.tvDesc);
            tvTag = (TextView) view.findViewById(R.id.tvPlace);
            ivSelectableImage = (ImageView) view.findViewById(R.id.ivSelectableImage);
        }
    }
}
