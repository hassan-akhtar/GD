package com.ets.gd.ToolHawk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.ToolhawkEquipment;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hakhtar on 5/5/2016.
 */
public class ScannedAssetsToolhawkAdapter extends RecyclerView.Adapter<ScannedAssetsToolhawkAdapter.MyViewHolder> {

    private List<ToolhawkEquipment> assetList = new ArrayList<ToolhawkEquipment>();
    String type = "";
    TextDrawable drawable;
    Context mContext;
    MyViewHolder myViewHolder;

    public ScannedAssetsToolhawkAdapter(Context context, List<ToolhawkEquipment> assetList) {
        this.assetList = assetList;
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
        ToolhawkEquipment asset = assetList.get(position);

        if (null!=asset.getDepartment()) {
            holder.tvTag.setText("Dep: " + asset.getDepartment().getCode());
        } else {
            holder.tvTag.setText("Dep: N/A");
        }

        if(null!=asset.getCode()) {
                holder.tvName.setText(asset.getCode());
            }else  {
                holder.tvName.setText("N/A");
            }


        if(null!=asset.getETSLocation()) {
            holder.tvLocation.setText("Location: " +asset.getETSLocation().getCode());
        }else  {
            holder.tvLocation.setText("Location: " +"N/A");
        }

            if(null!=asset.getCode()) {
                drawable = TextDrawable.builder()
                        .beginConfig()
                        .endConfig()
                        .buildRound(asset.getCode().substring(0, 1).toUpperCase(), mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.ivSelectableImage.setImageDrawable(drawable);
            }else{
                drawable = TextDrawable.builder()
                        .beginConfig()
                        .endConfig()
                        .buildRound("N/A", mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.ivSelectableImage.setImageDrawable(drawable);
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
        private TextView tvName, tvTag, tvLocation;
        private ImageView ivSelectableImage;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTag = (TextView) view.findViewById(R.id.tvDesc);
            tvLocation = (TextView) view.findViewById(R.id.tvPlace);
            ivSelectableImage = (ImageView) view.findViewById(R.id.ivSelectableImage);
        }
    }
}
