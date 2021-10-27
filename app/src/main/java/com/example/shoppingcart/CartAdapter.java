package com.example.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.databinding.CartItemViewBinding;
import com.example.shoppingcart.models.Product;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.UViewHolder>{

    public static final String TAG = "demo";
    ArrayList<Product> products;
    CartItemViewBinding binding;
    ViewGroup parent;
    private Context context;

    public CartAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CartItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.parent = parent;
        this.context = parent.getContext();
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
        Product product = products.get(position);
        binding.name.setText(product.getName());
        binding.quantity.setText(String.valueOf(product.getQty()));
        //binding.price.setText(String.valueOf(product.getTotal_product_price()));

    }
    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public static class UViewHolder extends RecyclerView.ViewHolder {

        CartItemViewBinding binding;

        public UViewHolder(@NonNull CartItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
