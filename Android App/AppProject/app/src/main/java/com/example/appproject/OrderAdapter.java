package com.example.appproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appproject.Interface.ItemClickListner;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> { //new

    private Context context;
    private List<OrderModel> orders;

    public OrderAdapter(Context context, List<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_command, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orders.get(position);

        holder.orderId.setText("Order ID: " + order.getOrderId());
        holder.orderState.setText("State: " + order.getState());
        holder.orderMessage.setText("Message: " + order.getMessage());

        holder.deleteButton.setOnClickListener(v -> {
            String orderId = String.valueOf(order.getOrderId()); // Get the order ID
            String clientId = ((OrderActivity) context).clientId; // Get the client ID from the context (OrderActivity)
            if (order.getState().equals("Awaiting Approval...")){
                ((OrderActivity) context).removeOrderFromClient(clientId, orderId, position);
            } else {
                Toast.makeText(context, "Failed to remove order", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, orderState, orderMessage;
        ImageView deleteButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_number);
            orderState = itemView.findViewById(R.id.order_state);
            orderMessage = itemView.findViewById(R.id.order_message);
            deleteButton = itemView.findViewById(R.id.delete_button9);
        }
    }
}
