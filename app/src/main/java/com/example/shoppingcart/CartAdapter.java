package com.example.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.databinding.CartItemViewBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.UViewHolder>{

    ArrayList<Product> og_products;
    CartItemViewBinding binding;
    ViewGroup parent;
    private String[] mKeys;
    HashMap<String, Product> cartProducts;
    User user;

    onUpdate am;

    public CartAdapter(User user, ArrayList<Product> og_products, onUpdate am) {
        this.og_products = og_products;
        this.am = am;
        this.user = user;
        updateData();
    }

    public void updateData(){
        cartProducts = user.getShoppingCart().getProductList();
        mKeys = cartProducts.keySet().toArray(new String[cartProducts.size()]);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    interface onUpdate{
        void update(User user);
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = CartItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.parent = parent;
        return new UViewHolder(binding);
    }

    public void update(UViewHolder holder, Product product){
        am.update(user);
        holder.binding.name.setText(product.getName());
        holder.binding.quantity.setText(String.valueOf(product.getQty()));
        holder.binding.textView9.setText(product.getQty() + "");
        holder.binding.price.setText(product.getQty() + " x " + String.format("%.2f", product.getUpdatedPrice()) + " = " + product.getTotalCostString());
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {
        if (mKeys.length < position + 1){
            return;
        }

        Product product = cartProducts.get(mKeys[position]);

        update(holder, product);

        holder.binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add
                Product product1 = user.getShoppingCart().getProductList().get(product.get_id());
                product1.incr_qty();
                user.getShoppingCart().getProductList().put(product.get_id(), product1);
                updateData();
            }
        });

        holder.binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // remove
                Product product1 = user.getShoppingCart().getProductList().get(product.get_id());
                product1.decr_qty();
                if(product1.getQty() <= 0){
                    user.getShoppingCart().getProductList().remove(product1.get_id());
                    am.update(user);
                }else{
                    user.getShoppingCart().getProductList().put(product1.get_id(), product1);
                }
                updateData();
            }
        });

    }
    @Override
    public int getItemCount() {
        return user.getShoppingCart().getProductList().size();
    }

    public static class UViewHolder extends RecyclerView.ViewHolder {

        CartItemViewBinding binding;

        public UViewHolder(@NonNull CartItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
