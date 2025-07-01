package com.example.foodorderingadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.AppCompatButton;

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
        holder.customerName.setText(order.getCustomerName());
        holder.quantity.setText(String.valueOf(order.getQuantity()));
        holder.foodImage.setImageResource(order.getFoodImageResId());
        holder.buttonAction.setText(order.getActionText());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView customerName, quantity;
        AppCompatButton buttonAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food);
            customerName = itemView.findViewById(R.id.text_customer_name);
            quantity = itemView.findViewById(R.id.quantity);
            buttonAction = itemView.findViewById(R.id.button_action);
        }
    }
}
