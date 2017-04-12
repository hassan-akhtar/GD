package com.ets.gd.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ets.gd.Models.Asset;
import com.ets.gd.Models.Location;
import com.ets.gd.R;


import java.util.List;

/**
 * Created by hakhtar on 5/5/2016.
 */
public class ScannedAssetsAdapter extends RecyclerView.Adapter<ScannedAssetsAdapter.MyViewHolder> {

    private List<Asset> assetList;
    private List<Location> locList;
    String type = "";
    TextDrawable drawable;
    Context mContext;
    MyViewHolder myViewHolder;

    public ScannedAssetsAdapter(Context context, List<Asset> assetList) {
        this.assetList = assetList;
        this.mContext = context;
    }

    public ScannedAssetsAdapter(List<Location> locList, Context context, String type) {
        this.locList = locList;
        this.mContext = context;
        this.type = type;
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
        if ("".equals(type)) {
            Asset asset = assetList.get(position);

            holder.tvTag.setText("Tag: " + asset.getTagID());
            if (null!=asset.getLocation()) {
                holder.tvLocation.setText("Location: " + asset.getLocation().getLocationID());
            }

            holder.tvName.setText(asset.getManufacturers() + ", " + asset.getModel());
                drawable = TextDrawable.builder()
                        .beginConfig()
                        .endConfig()
                        .buildRound(asset.getManufacturers().substring(0, 1).toUpperCase(), mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.ivSelectableImage.setImageDrawable(drawable);


        } else {
            Location loc = locList.get(position);
            holder.tvTag.setText(loc.getDescription() + ",");
            holder.tvLocation.setText(loc.getPlace());

            holder.tvName.setText(loc.getLocationID());
            drawable = TextDrawable.builder()
                    .beginConfig()
                    .endConfig()
                    .buildRound(loc.getLocationID().substring(0, 1).toUpperCase(), mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.ivSelectableImage.setImageDrawable(drawable);
        }
    }


    @Override
    public int getItemCount() {
        if ("".equals(type)) {
            return assetList.size();
        } else {
            return locList.size();
        }
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
