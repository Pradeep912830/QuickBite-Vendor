package com.example.foodorderingadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorderingadmin.Model.OrderItem;
import com.example.foodorderingadmin.Model.PendingOrder;
import com.example.foodorderingadmin.R;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private List<PendingOrder> orderItemList;
    private Context context;

    public OrderItemAdapter(Context context, List<PendingOrder> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodPrice, foodQuantity;
        AppCompatButton pendingButton;

        public ViewHolder(View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            pendingButton = itemView.findViewById(R.id.pendingButton);
            foodQuantity = itemView.findViewById(R.id.foodQuantity);
        }
    }

    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pending_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemAdapter.ViewHolder holder, int position) {
        PendingOrder item = orderItemList.get(position);

        holder.foodName.setText(item.getFoodName());
        holder.foodQuantity.setText(String.valueOf(item.getFoodQuantity()));
        holder.foodPrice.setText(String.valueOf(item.getFoodPrice()));
        holder.pendingButton.setText(item.getPendingButton());
        Glide.with(context).load(item.getFoodImage()).into(holder.foodImage);

    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }
}
