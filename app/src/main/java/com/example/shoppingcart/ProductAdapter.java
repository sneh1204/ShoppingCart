package com.example.shoppingcart;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.databinding.ProductViewBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.ShoppingCart;
import com.example.shoppingcart.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UViewHolder>{

    ArrayList<Product> products;
    ProductViewBinding binding;
    ViewGroup parent;
    User user;

    public ProductAdapter(User user, ArrayList<Product> products) {
        this.products = products;
        this.user = user;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.parent = parent;
        return new UViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void checkQuantity(UViewHolder holder, Product product){
        int quantity = user.getShoppingCart().quantity(product);
        if(quantity <= 0){
            holder.binding.negbtn.setVisibility(View.GONE);
            holder.binding.addbtn.setVisibility(View.GONE);
            holder.binding.qtytxt.setVisibility(View.GONE);

            holder.binding.add.setVisibility(View.VISIBLE);
        }else{
            holder.binding.negbtn.setVisibility(View.VISIBLE);
            holder.binding.addbtn.setVisibility(View.VISIBLE);
            holder.binding.qtytxt.setVisibility(View.VISIBLE);
            holder.binding.qtytxt.setText(quantity + "");

            holder.binding.add.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {

        Product product = products.get(position);
        holder.binding.productName.setText(product.getName());
        holder.binding.productPrice.setText("$" + product.getPrice());
        holder.binding.productPrice.setPaintFlags(holder.binding.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.binding.textView4.setText("$" + String.format("%.2f", product.getUpdatedPrice()));
        holder.binding.textView4.setTextColor(0xFF50C878);

        holder.binding.productDiscount.setText("Discount " + product.getDiscount() + "%");

        String photo = product.getPhoto();
        String photoName;

        if(photo != null){
            photoName = photo.split("[.]")[0];
        }else{
            photoName = "noimage";
        }

        checkQuantity(holder, product);

        int resId = parent.getContext().getResources().getIdentifier(photoName, "drawable", parent.getContext().getPackageName());
        Picasso.get()
                .load(resId)
                .into(holder.binding.productImage);

        holder.binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShoppingCart shoppingCart = user.getShoppingCart();
                if(shoppingCart.getProductArrayList().containsKey(product.get_id())){
                    Product product1 = shoppingCart.getProductArrayList().get(product.get_id());
                    product1.incr_qty();
                    shoppingCart.getProductArrayList().put(product.get_id(), product1);
                }else{
                    shoppingCart.getProductArrayList().put(product.get_id(), product);
                }

                checkQuantity(holder, product);

            }

        });

        holder.binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCart shoppingCart = user.getShoppingCart();
                Product product1 = shoppingCart.getProductArrayList().get(product.get_id());
                product1.incr_qty();
                shoppingCart.getProductArrayList().put(product.get_id(), product1);

                checkQuantity(holder, product);

            }
        });

        holder.binding.negbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCart shoppingCart = user.getShoppingCart();
                Product product1 = shoppingCart.getProductArrayList().get(product.get_id());
                product1.decr_qty();
                shoppingCart.getProductArrayList().put(product.get_id(), product1);

                checkQuantity(holder, product);

            }
        });

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
