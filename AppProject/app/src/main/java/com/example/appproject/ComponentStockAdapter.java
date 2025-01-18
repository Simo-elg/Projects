package com.example.appproject;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComponentStockAdapter extends RecyclerView.Adapter<ComponentStockAdapter.ViewHolder> {

    private final List<Pair<String, Integer>> componentsWithStock;
    private final Context context;

    public ComponentStockAdapter(Context context, List<Pair<String, Integer>> componentsWithStock) {
        this.context = context;
        this.componentsWithStock = componentsWithStock;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_component_stock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, Integer> item = componentsWithStock.get(position);
        holder.tvDescription.setText("Description : " + item.first);
        holder.tvQuantity.setText("Quantity: " + item.second);

        // Load and apply the custom font
        Typeface alataFont = ResourcesCompat.getFont(holder.itemView.getContext(), R.font.alata);
        holder.tvDescription.setTypeface(alataFont);
        holder.tvQuantity.setTypeface(alataFont);
    }

    @Override
    public int getItemCount() {
        return componentsWithStock.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tv_component_description);
            tvQuantity = itemView.findViewById(R.id.tv_component_quantity);
        }
    }
}
