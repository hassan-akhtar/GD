package com.ets.gd.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ets.gd.DataManager.DataManager;
import com.ets.gd.Models.RouteInspRecord;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteAsset;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class RouteAssetAdapter extends RecyclerView.Adapter<RouteAssetAdapter.MyViewHolder> {

    private List<FireBugEquipment> assetList = new ArrayList<FireBugEquipment>();
    List<RouteAsset> routeAssetList = new ArrayList<>();
    String type = "";
    TextDrawable drawable;
    Context mContext;
    MyViewHolder myViewHolder;

    public RouteAssetAdapter(Context context, List<FireBugEquipment> assetList,  List<RouteAsset> routeAssetList) {
        this.routeAssetList=routeAssetList;
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
        if ("".equals(type)) {
            FireBugEquipment asset = assetList.get(position);

            holder.tvName.setText("" + asset.getCode());
            if (null != asset.getLocation()) {
                holder.tvLocation.setText("Location: " + asset.getLocation().getCode());
            }

            RouteInspRecord routeInspRecord = DataManager.getInstance().getRouteInspRecord(routeAssetList.get(position).getID());
            if (null!=routeInspRecord) {
                holder.ivInspected.setVisibility(View.VISIBLE);
            }


            if (null != asset.getManufacturers() ) {
                holder.tvTag.setText("Mfg: " + asset.getModel().getCode());
            }  else {
                holder.tvName.setText("Mfg: " +"N/A");
            }
            holder.ivSelectableImage.setVisibility(View.GONE);
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
        private ImageView ivSelectableImage, ivInspected;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTag = (TextView) view.findViewById(R.id.tvDesc);
            tvLocation = (TextView) view.findViewById(R.id.tvPlace);
            ivSelectableImage = (ImageView) view.findViewById(R.id.ivSelectableImage);
            ivInspected = (ImageView) view.findViewById(R.id.ivInspect);
        }
    }
}
