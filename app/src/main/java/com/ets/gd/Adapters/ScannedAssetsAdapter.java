package com.ets.gd.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ets.gd.Models.Asset;
import com.ets.gd.R;


import java.util.List;

/**
 * Created by hakhtar on 5/5/2016.
 */
public class ScannedAssetsAdapter extends RecyclerView.Adapter<ScannedAssetsAdapter.MyViewHolder> {

    private List<Asset> assetList;
    TextDrawable drawable;
    Context mContext;

    public ScannedAssetsAdapter(Context context, List<Asset> assetList) {
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
        Asset asset = assetList.get(position);

        holder.tvTag.setText("Tag: "+asset.getTag());
        holder.tvLocation.setText("Location: "+asset.getLocation());

        if ("".equals(asset.getCode())) {
            holder.tvName.setText(asset.getName());
            drawable = TextDrawable.builder()
                    .beginConfig()
                    .endConfig()
                    .buildRound(asset.getName().substring(0, 1).toUpperCase(), mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.ivSelectableImage.setImageDrawable(drawable);
        } else {
            holder.tvName.setText(asset.getCode() + ", " + asset.getName());
            drawable = TextDrawable.builder()
                    .beginConfig()
                    .endConfig()
                    .buildRound(asset.getCode().substring(0, 1).toUpperCase() + asset.getName().substring(0, 1).toUpperCase(), mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.ivSelectableImage.setImageDrawable(drawable);
        }

        holder.ivSelectableImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return assetList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvTag, tvLocation;
        private ImageView ivSelectableImage;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTag = (TextView) view.findViewById(R.id.tvTag);
            tvLocation = (TextView) view.findViewById(R.id.tvLocation);
            ivSelectableImage = (ImageView) view.findViewById(R.id.ivSelectableImage);
        }
    }
}
