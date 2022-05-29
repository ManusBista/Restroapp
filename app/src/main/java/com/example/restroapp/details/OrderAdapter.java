package com.example.restroapp.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restroapp.HomePage.ActiveOrder;
import com.example.restroapp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<Order> mData;


    public OrderAdapter(Context mContext, List<Order> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.order_info, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getItemName());
        holder.quantity.setText(mData.get(position).getQuantity());
        holder.id.setText(mData.get(position).getItemId());
        holder.price.setText(mData.get(position).getTotal());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView quantity;
        TextView id;
        TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            quantity = itemView.findViewById(R.id.quantity);
            id = itemView.findViewById(R.id.id);
            price = itemView.findViewById(R.id.price);

        }
    }

}
