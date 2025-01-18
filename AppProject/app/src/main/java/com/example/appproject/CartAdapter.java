package com.example.appproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.Interface.ItemClickListner;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartModel> cartItems;
    private ItemClickListner clickListner;

    public CartAdapter(Context context, List<CartModel> cartItems, ItemClickListner clickListner) {
        this.context = context;
        this.cartItems = cartItems;
        this.clickListner = clickListner;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartModel cartItem = cartItems.get(position);

        // Bind the data to the views in the layout

        holder.productName.setText(cartItem.getProductName());
        holder.productPrice.setText(cartItem.getProductPrice());
        holder.productDescription.setText(cartItem.getProductDescription());
        holder.removeItemButton.setTag(position);
        holder.quantity.setText(cartItem.getQuantity());
        holder.productsubType.setText(cartItem.getSub_t());


        holder.removeItemButton.setOnClickListener(view -> {
            clickListner.onClick(view, position, false);
        });

        // Set the product image (you can use Glide or Picasso for loading images)
        //Glide.with(context).load(cartItem.getProductImage()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription,productsubType, quantity;
        //ImageView productImage;
        ImageView removeItemButton;


        public CartViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.cartTYPE);
            productsubType = itemView.findViewById(R.id.cartSOUS_TYPE);
            quantity = itemView.findViewById(R.id.cartQUANTITe);
            productPrice = itemView.findViewById(R.id.cartPRICE);
            productDescription = itemView.findViewById(R.id.cartDESCRIPTION);
            //productImage = itemView.findViewById(R.id.cartIMAGE);
            removeItemButton = itemView.findViewById(R.id.cartdelete);
        }
    }
}
