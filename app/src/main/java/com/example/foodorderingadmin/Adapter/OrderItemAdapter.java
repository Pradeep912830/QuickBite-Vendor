package com.example.foodorderingadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Model.OrderItem;
import com.example.foodorderingadmin.R;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private List<OrderItem> orderItemList;
    private Context context;

    public OrderItemAdapter(Context context, List<OrderItem> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, payment, received;
        ImageButton btnSuccessful;

        public ViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            payment = itemView.findViewById(R.id.payment);
            received = itemView.findViewById(R.id.received);
            btnSuccessful = itemView.findViewById(R.id.btnSuccessful);
        }
    }

    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivered_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemAdapter.ViewHolder holder, int position) {
        OrderItem item = orderItemList.get(position);

        holder.foodName.setText(item.getCustomerName());
        holder.payment.setText(item.getPaymentType());
        holder.received.setText(item.getStatus());

        holder.btnSuccessful.setOnClickListener(v -> {
            Toast.makeText(context, item.getCustomerName() + " marked as successful", Toast.LENGTH_SHORT).show();
            // Optionally change status or remove item
        });
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }
}
