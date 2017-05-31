package com.ets.gd.ToolHawk.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.Models.User;
import com.ets.gd.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList = new ArrayList<User>();
    Context mContext;
    MyViewHolder myViewHolder;

    public UserAdapter(Context context, List<User> assetList) {
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
        User user = userList.get(position);
        holder.tvTitle.setText(""+user.getCode());
        holder.tvDesc.setText(""+user.getName());

    }


    @Override
    public int getItemCount() {
            return userList.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        }
    }
}
