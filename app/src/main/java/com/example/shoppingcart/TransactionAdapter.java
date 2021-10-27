package com.example.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.databinding.CartItemViewBinding;
import com.example.shoppingcart.databinding.TransactionViewBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.UViewHolder>{

    TransactionViewBinding binding;
    ViewGroup parent;
    private String[] mKeys;
    HashMap<String, Product> products;

    public TransactionAdapter(HashMap<String, Product> products) {
        this.products = products;
        updateData();
    }

    public void updateData(){
        mKeys = products.keySet().toArray(new String[products.size()]);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = TransactionViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.parent = parent;
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
        if (mKeys.length < position + 1){
            return;
        }

        Product product = products.get(mKeys[position]);

        holder.binding.name.setText(product.getName());
        holder.binding.quantity.setText(product.getQty() + "");
        holder.binding.price.setText("$" + String.format("%.2f", product.getUpdatedPrice()));
        holder.binding.textView20.setText("Total - $" + product.getTotalCostString());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class UViewHolder extends RecyclerView.ViewHolder {

        TransactionViewBinding binding;

        public UViewHolder(@NonNull TransactionViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
