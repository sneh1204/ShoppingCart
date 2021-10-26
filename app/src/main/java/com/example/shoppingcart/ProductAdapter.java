package com.example.shoppingcart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoppingcart.databinding.ProductViewBinding;

import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.ShoppingCart;
import com.example.shoppingcart.models.User;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UViewHolder>{

    public static final String TAG = "demo";
    ArrayList<Product> products;
    ProductViewBinding binding;
    ViewGroup parent;

    private Context context;
    ShoppingCart shoppingCart = new ShoppingCart();

    ArrayList<Product> productArrayList = new ArrayList<>();

    public ProductAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ProductViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.parent = parent;
        this.context = parent.getContext();
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {

        Product product = products.get(position);
        binding.productName.setText(product.getName());
        binding.productPrice.setText(String.valueOf(product.getPrice()));
        binding.productDiscount.setText(String.valueOf(product.getDiscount()));

        String photo = product.getPhoto();
        if(photo!=null){
            String photoname = photo.split("[.]")[0];
            int resId = context.getResources().getIdentifier(photoname, "drawable", context.getPackageName());
            Glide.with(parent)
                    .load(resId)
                    .into(binding.productImage);
        }


        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product.setQty(product.getQty() + 1);
                int updatedQuantity = product.getQty();
                double total_product_price = Product.calculateProductPrice(product.getPrice(), product.getDiscount(), updatedQuantity);
                product.setTotal_product_price(total_product_price);

                if(productArrayList.size() == 0) {
                    productArrayList.add(new Product(product.getName(), product.getPhoto(), product.getRegion(), product.getPrice(), product.getDiscount(), product.getQty(), product.getTotal_product_price()));
                }else {
                    for(Product proc: productArrayList) {
                        if(proc.getName().equals(product.getName())){
                            proc.setQty(updatedQuantity);
                            proc.setTotal_product_price(total_product_price);
                            break;
                        } else {
                            productArrayList.add(new Product(product.getName(), product.getPhoto(), product.getRegion(), product.getPrice(), product.getDiscount(), product.getQty(), product.getTotal_product_price()));
                        }
                    }
                }

                shoppingCart.setProductArrayList(productArrayList);

                Log.d(TAG, "onClick: " + shoppingCart);

                Toast.makeText(view.getContext(),"Item is added to the cart" , Toast.LENGTH_LONG ).show();
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
