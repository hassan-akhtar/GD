package com.ets.gd.ToolHawk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.Models.ToolhawkAsset;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;


public class QuickCountAdapter extends RecyclerView.Adapter<QuickCountAdapter.MyViewHolder> {

    private List<ToolhawkAsset> assetList = new ArrayList<ToolhawkAsset>();
    Context mContext;
    MyViewHolder myViewHolder;

    public QuickCountAdapter(Context context, List<ToolhawkAsset> assetList) {
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
        ToolhawkAsset asset = assetList.get(position);
        holder.tvName.setText("" + asset.getName());
        holder.tvAssetCode.setText("" + asset.getCode());
        holder.tvLocCode.setText("" + asset.getLoc());
        if (asset.isParent()) {
            holder.tvParent.setText("YES");
        } else {
            holder.tvParent.setText("NO");
        }

    }


    @Override
    public int getItemCount() {
        return assetList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAssetCode, tvLocCode, tvParent;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAssetCode = (TextView) view.findViewById(R.id.tvAssetCode);
            tvLocCode = (TextView) view.findViewById(R.id.tvLocCode);
            tvParent = (TextView) view.findViewById(R.id.tvParent);
        }
    }
}
