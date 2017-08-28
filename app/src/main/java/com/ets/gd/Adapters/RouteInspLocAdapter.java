package com.ets.gd.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.DataManager.DataManager;
import com.ets.gd.NetworkLayer.ResponseDTOs.RouteLocation;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class RouteInspLocAdapter extends RecyclerView.Adapter<RouteInspLocAdapter.MyViewHolder> {
    List<RouteLocation> locList = new ArrayList<RouteLocation>();
    Context mContext;

    public RouteInspLocAdapter(Context mContext, List<RouteLocation> routesList) {
        this.locList = routesList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_route_insp_loc_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (position == locList.size() - 1) {
            holder.ivCount.setBackground(mContext.getResources().getDrawable(R.drawable.ic_loc_item_last));
        } else {
            holder.ivCount.setBackground(mContext.getResources().getDrawable(R.drawable.ic_loc_item));
        }
        holder.tvName.setText(DataManager.getInstance().getLocationByID(locList.get(position).getLocationID()).getCode());
        holder.tvDesc.setText(DataManager.getInstance().getLocationByID(locList.get(position).getLocationID()).getDescription() + ",");
        int pos = position + 1;
        holder.tvNumber.setText("" + pos);
        holder.tvLocCount.setText("" + locList.get(position).getRouteAssets().size());
        holder.tvPlace.setText(DataManager.getInstance().getLocationByID(locList.get(position).getLocationID()).getSite().getCode());
        if (1 == locList.get(position).getIsCompleted()) {
            holder.ivTick.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return locList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber, tvName, tvDesc, tvPlace, tvLocCount,ivTick;
        private ImageView ivCount;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvNumber = (TextView) view.findViewById(R.id.tvNumber);
            tvPlace = (TextView) view.findViewById(R.id.tvPlace);
            tvLocCount = (TextView) view.findViewById(R.id.tvLocCount);
            ivCount = (ImageView) view.findViewById(R.id.ivCount);
            ivTick = (TextView) view.findViewById(R.id.ivTick);
        }
    }
}
