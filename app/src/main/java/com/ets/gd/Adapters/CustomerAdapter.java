package com.ets.gd.Adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.ets.gd.Activities.BaseActivity;
import com.ets.gd.Models.Customer;
import com.ets.gd.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>
{
    private LayoutInflater layoutInflater;
    private List<Customer> customerList;
    private TextDrawable drawable;
    private final Context mContext;
    private ViewHolder viewHolder;
    @Override
    public int getItemCount() {
        return this.customerList.size();
    }

    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.activity_customer_list_item, parent, false);

        // Return a new holder instance
        viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        final Customer customer = this.customerList.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.customer_name_tv;
        textView.setText(customer.getCode().substring(0,1).toUpperCase()+customer.getCode().substring(1,customer.getCode().length()).toUpperCase());
        ImageView customerImg = holder.customer_img;
        drawable = TextDrawable.builder()
                .beginConfig()
                .textColor( mContext.getResources().getColor(R.color.blueDark))
                .bold()
                .bold()
                .endConfig()
                .buildRound(customer.getCode().substring(0,1).toUpperCase(), mContext.getResources().getColor(R.color.blueGrey));
        customerImg.setImageDrawable(drawable);
        holder.customerItemMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("onClick: ",customer.getCode()+ "  "+ customer.getCode().substring(0,1).toUpperCase());
               // Toast.makeText(mContext,customer.getCode()+ "  "+ customer.getCode().substring(0,1).toUpperCase(),Toast.LENGTH_LONG).show();
              // BaseActivity.refreshMainViewByNew(new ScanFragment());
            }
        });
    }

    public Object getItemAt(int position)
    {
        return this.customerList.get(position);
    }
    // Pass in the contact array into the constructor
    public CustomerAdapter(Context context, List<Customer> customers) {
        this.customerList = customers;
        List<Customer> customerListCopy = customers;
        mContext = context;
        Log.i("CustomerAdapter: ","Initialized");
        Log.i("Adapter List: ",this.customerList.toString());
        Log.i("Adapter List Copy: ", customerListCopy.toString());
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Inner class for view holder or more precisely a row view

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public final TextView customer_name_tv;
        public final ImageView customer_img;
        final ConstraintLayout customerItemMainView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            customer_name_tv = (TextView) itemView.findViewById(R.id.activity_customer_name_tv);
            customer_img = (ImageView) itemView.findViewById(R.id.activity_customer_img);
            customerItemMainView = (ConstraintLayout) itemView.findViewById(R.id.customerItemMainView);
        }
    }


}