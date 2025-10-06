package com.example.foodorderingadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.foodorderingadmin.DetailsItemActivity;
import com.example.foodorderingadmin.Model.PendingOrder;
import com.example.foodorderingadmin.R;

import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.ViewHolder> {

    private Context context;
    private List<PendingOrder> orderList;

    public PendingOrderAdapter(Context context, List<PendingOrder> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PendingOrder order = orderList.get(position);
        holder.foodName.setText(order.getFoodName());
        holder.foodQuantity.setText(String.valueOf(order.getFoodQuantity()));
        holder.foodPrice.setText(String.valueOf(order.getFoodPrice()));
        holder.pendingButton.setText(order.getPendingButton());
        Glide.with(context).load(order.getFoodImage()).into(holder.foodImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsItemActivity.class);
            intent.putExtra("userId", order.getUserId());
            intent.putExtra("orderId", order.getOrderId());
            intent.putExtra("itemId", order.getItemId());
            intent.putExtra("accepted", "pendingValue");
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodPrice, foodQuantity;
        AppCompatButton pendingButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            pendingButton = itemView.findViewById(R.id.pendingButton);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
        }
    }
}
