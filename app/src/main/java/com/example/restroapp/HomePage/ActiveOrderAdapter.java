package com.example.restroapp.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restroapp.R;

import java.util.List;

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.MyViewHolder> {

    private Context mContext;
    private List<ActiveOrder> mData;
    private  ContinueButton continueButton;

    public ActiveOrderAdapter(Context mContext, List<ActiveOrder> mData,ContinueButton continueButton) {
        this.mContext = mContext;
        this.mData = mData;
        this.continueButton = continueButton;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.active_order_info, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ActiveOrder activeOrder=mData.get(position);
        holder.name.setText(activeOrder.getOrderedBy());
        holder.Order_Id.setText(activeOrder.getOrderId());
        holder.price.setText(activeOrder.getTotal());
        holder.o_type.setText(activeOrder.getOrderType());
        holder.continue_btn.setOnClickListener(v -> {
            continueButton.ContinueButton(activeOrder.getMobileNumber(),activeOrder.getOrderedBy(),activeOrder.getOrderId());
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView Order_Id;
        TextView price,o_type;
        Button continue_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.o_name);
            Order_Id = itemView.findViewById(R.id.order_id);
            price = itemView.findViewById(R.id.o_price);
            o_type = itemView.findViewById(R.id.o_type);
            continue_btn = itemView.findViewById(R.id.continue_btn);
        }
    }

public  interface ContinueButton{
        void ContinueButton(String num,String name,String orderId);
}

}
