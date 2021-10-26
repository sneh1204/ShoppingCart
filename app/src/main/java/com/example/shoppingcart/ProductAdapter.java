package com.example.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppingcart.databinding.ProductViewBinding;
import com.example.shoppingcart.models.Product;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UViewHolder>{

    ArrayList<Product> products;

    ProductViewBinding binding;

    ViewGroup parent;

    public ProductAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.parent = parent;
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
        Product product = products.get(position);

        binding.productName.setText(product.getName());
        binding.productPrice.setText(String.valueOf(product.getPrice()));
        binding.productDiscount.setText(String.valueOf(product.getDiscount()));
        Glide.with(parent)
                .load(product.getPhotoUrl())
                .into(binding.productImage);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // double total_product_price = calculateProductPrice(product.getPrice(), product.getDiscount());
                Toast.makeText(view.getContext(), "Item is added to the cart", Toast.LENGTH_LONG ).show();
            }
        });

    }

    private double calculateProductPrice(double price, double discount) {
        double discountPrice ;
        double total_amount;
        discountPrice = (price * discount)/100;
        total_amount = price - discountPrice;
        return total_amount;
    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    public static class UViewHolder extends RecyclerView.ViewHolder {

        ProductViewBinding binding;

        public UViewHolder(@NonNull ProductViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
