package com.ets.gd.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ets.gd.Models.Customer;
import com.ets.gd.NetworkLayer.ResponseDTOs.AllCustomers;
import com.ets.gd.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder>{
    private List<AllCustomers> customerList;
    private final Context mContext;
    private RecyclerView.ViewHolder viewHolder;

    public CustomerAdapter( Context context, List<AllCustomers> customerList) {
        this.customerList = customerList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_customer_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AllCustomers camper = customerList.get(position);
        holder.code.setText(camper.getCode());


    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView code;

        public MyViewHolder(View view) {
            super(view);
            code = (TextView) view.findViewById(R.id.tvCustomerName);
        }
    }
}
