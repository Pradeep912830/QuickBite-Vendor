package com.example.foodorderingadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingadmin.Model.FoodItem;
import com.example.foodorderingadmin.R;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    private List<FoodItem> foodItemList;
    Context contex;

    public FoodItemAdapter(Context context , List<FoodItem> foodItemList) {
        this.contex = context;
        this.foodItemList = foodItemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, desciption, foodPrice, foodQuantity;
        ImageButton buttonIncrease, buttonDecrease, buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            desciption = itemView.findViewById(R.id.food_description);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodQuantity = itemView.findViewById(R.id.food_quantity);
            buttonIncrease = itemView.findViewById(R.id.button_increase);
            buttonDecrease = itemView.findViewById(R.id.button_decrease);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }
    }

    @Override
    public FoodItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodItemAdapter.ViewHolder holder, int position) {
        FoodItem item = foodItemList.get(position);


        Glide.with(holder.foodImage.getContext())
                .load(item.getImageUrl())
                .placeholder(R.drawable.food) // default image
                .into(holder.foodImage);

        holder.foodName.setText(item.getName());
        holder.desciption.setText(item.getDescription());
        holder.foodPrice.setText(item.getPrice());
        holder.foodQuantity.setText(String.valueOf(item.getQuantity()));


        holder.buttonIncrease.setOnClickListener(v -> {
            int qty = item.getQuantity();
            item.setQuantity(qty + 1);
            notifyItemChanged(position);
        });

        holder.buttonDecrease.setOnClickListener(v -> {
            int qty = item.getQuantity();
            if (qty > 1) {
                item.setQuantity(qty - 1);
                notifyItemChanged(position);
            }
        });

        holder.buttonDelete.setOnClickListener(v -> {
            foodItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, foodItemList.size());
        });
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }
}
