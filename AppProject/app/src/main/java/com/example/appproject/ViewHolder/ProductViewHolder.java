package com.example.appproject.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.Interface.ItemClickListner;
import com.example.appproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductType, txtProductPrice;
    public ImageView imageView;
    private ItemClickListner listner;
    public FloatingActionButton addElem;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductType = itemView.findViewById(R.id.product_type);
        txtProductPrice = itemView.findViewById(R.id.product_price);
        addElem = itemView.findViewById(R.id.floatingActionButton2);

        // Attache l'écouteur de clic à l'élément de vue
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        if (listner != null) {
            listner.onClick(view, getAdapterPosition(), false);
        }
    }
}
