package com.ets.gd.ToolHawk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.Models.User;
import com.ets.gd.NetworkLayer.ResponseDTOs.MobileUser;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<MobileUser> userList = new ArrayList<MobileUser>();
    Context mContext;
    MyViewHolder myViewHolder;

    public UserAdapter(Context context, List<MobileUser> assetList) {
        this.userList = assetList;
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
        MobileUser user = userList.get(position);
        holder.tvTitle.setText(""+user.getFirstName()+" "+user.getLastName());
        holder.tvConstructionDescp.setText("Username: " );
        holder.tvDesc.setText(""+user.getUserName());

    }


    @Override
    public int getItemCount() {
            return userList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc, tvConstructionDescp;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvConstructionDescp = (TextView) view.findViewById(R.id.tvConstructionDescp);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        }
    }
}
