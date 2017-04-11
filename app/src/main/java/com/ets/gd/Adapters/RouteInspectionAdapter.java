package com.ets.gd.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ets.gd.Models.Routes;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class RouteInspectionAdapter extends RecyclerView.Adapter<RouteInspectionAdapter.MyViewHolder> {
    List<Routes> routesList = new ArrayList<Routes>();

    public RouteInspectionAdapter(List<Routes> routesList) {
        this.routesList = routesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_route_insp, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(routesList.get(position).getCode());
        holder.tvDesc.setText(routesList.get(position).getDesc());
        holder.tvRouteTypeValue.setText(routesList.get(position).getRouteType());
        holder.tvLocCount.setText(""+routesList.get(position).getRouteLocations().size());

        holder.tvAssetCount.setText(""+routesList.get(position).getRouteLocations().get(0).getRouteAssets().size());
    }

    @Override
    public int getItemCount() {
        return routesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDesc, tvRouteTypeValue, tvLocCount, tvAssetCount;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvRouteTypeValue = (TextView) view.findViewById(R.id.tvRouteTypeValue);
            tvLocCount = (TextView) view.findViewById(R.id.tvLocCount);
            tvAssetCount = (TextView) view.findViewById(R.id.tvAssetCount);
        }
    }
}
