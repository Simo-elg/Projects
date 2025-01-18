package com.example.appproject;// StockAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private List<Stock> stockItems;

    public StockAdapter(List<Stock> stockItems) {
        this.stockItems = stockItems;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock stockItem = stockItems.get(position);
        holder.typeTextView.setText(stockItem.getType());
        holder.subTypeTextView.setText(stockItem.getSubType());
        holder.descriptionTextView.setText(stockItem.getDescription());
        holder.quantityTextView.setText(String.valueOf(stockItem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    class StockViewHolder extends RecyclerView.ViewHolder {

        TextView typeTextView;
        TextView subTypeTextView;
        TextView descriptionTextView;
        TextView quantityTextView;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.textViewType);
            subTypeTextView = itemView.findViewById(R.id.textViewSubType);
            descriptionTextView = itemView.findViewById(R.id.textViewDescription);
            quantityTextView = itemView.findViewById(R.id.textViewQuantity);
        }
    }
}
