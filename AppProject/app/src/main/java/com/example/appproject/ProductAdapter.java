package com.example.appproject;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.ViewHolder.ProductViewHolder;
import com.example.appproject.Products;
import com.example.appproject.Interface.ItemClickListner;
import com.example.appproject.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private Context context;
    private List<Products> productList;
    private ItemClickListner clickListener;

    public ProductAdapter(Context context, List<Products> productList, ItemClickListner clickListener) {
        this.context = context;
        this.productList = productList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utilise le layout product_item pour chaque élément
        View view = LayoutInflater.from(context).inflate(R.layout.activity_shop, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Récupérer le produit actuel
        Products product = productList.get(position);

        // Définir les données du produit dans le ProductViewHolder
        holder.txtProductName.setText(product.getPname());
        holder.txtProductType.setText(product.getPtype());
        holder.txtProductPrice.setText(product.getPrice());
        holder.addElem.setTag(position);

        // Ajouter un écouteur de clic
        holder.addElem.setOnClickListener(view -> {
            clickListener.onClick(view, position, false);
        });

        // Ajouter un écouteur de clic
        holder.setItemClickListener((view, pos, isLongClick) -> {
            if (clickListener != null) {
                clickListener.onClick(view, pos, isLongClick);
            }
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }
}
