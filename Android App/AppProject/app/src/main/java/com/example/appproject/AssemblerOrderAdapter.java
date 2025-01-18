package com.example.appproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.Interface.ItemClickListner;


import java.util.List;

public class AssemblerOrderAdapter extends RecyclerView.Adapter<AssemblerOrderAdapter.AssemblerOrderViewHolder> {
    private Context context;
    private List<AssemblerOrderModel> order;
    private ItemClickListner clickListner;
    private DDB ddb;
    static String idTemp;

    public AssemblerOrderAdapter(Context context, List<AssemblerOrderModel> order, ItemClickListner clickListner){
        this.context = context;
        this.order = order;
        this.clickListner = clickListner;
        ddb = new DDB(context);
    }

    public AssemblerOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.order_assembler, parent, false);
        return new AssemblerOrderViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public void onBindViewHolder(AssemblerOrderViewHolder holder, int position){
        AssemblerOrderModel orders = order.get(position);

        holder.orderId.setText("Order ID: " + orders.getOrderId());
        holder.orderStatus.setText(orders.getStatus());

        // Onclick listener to see the details of a command
        holder.order.setOnClickListener(v -> {
            String orderid = String.valueOf(orders.getOrderId());
            Intent intent = new Intent(context, AssemblerOrderViewAll.class);
            intent.putExtra("order id", String.valueOf(orderid));
            context.startActivity(intent);
        });

        holder.acceptOrderImage.setOnClickListener(v -> {
            ddb.updateOrderStatebyId(orders.getOrderId(),"Order Accepted","Pc assembly has started");
            holder.orderStatus.setText("Order Accepted");
        });
        holder.rejectOrderImage.setOnClickListener(v -> {
            ddb.updateOrderStatebyId(orders.getOrderId(),"Order Rejected...", "Check for unavailable items selected");
            holder.orderStatus.setText("Order Rejected...");
        });
        holder.awaitingChangeImage.setOnClickListener(v -> {
            ddb.updateOrderStatebyId(orders.getOrderId(),"Awaiting Approval...","...");
            holder.orderStatus.setText("Awaiting Approval...");
        });
        holder.sendPcImage.setOnClickListener(v -> {
            ddb.updateOrderStatebyId(orders.getOrderId(),"Order Sent!","PC Assembly completed");
            holder.orderStatus.setText("Order Sent!");
        });

    }


    public static class AssemblerOrderViewHolder extends RecyclerView.ViewHolder{
        TextView orderId;
        TextView orderStatus;
        LinearLayout order;
        ImageView acceptOrderImage, rejectOrderImage, awaitingChangeImage, sendPcImage;

        public AssemblerOrderViewHolder(View view){
            super(view);
            orderId = view.findViewById(R.id.order_number);
            orderStatus = view.findViewById(R.id.order_status);
            order = view.findViewById(R.id.order);

            // Image views
            acceptOrderImage = view.findViewById(R.id.accept_order);
            rejectOrderImage = view.findViewById(R.id.reject_order);
            awaitingChangeImage = view.findViewById(R.id.awaiting_change);
            sendPcImage = view.findViewById(R.id.send_pc);
        }
    }
}
