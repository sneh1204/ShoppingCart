package com.example.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shoppingcart.databinding.FragmentProductsBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.ShoppingCart;
import com.example.shoppingcart.models.User;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ProductsFragment extends Fragment {

    FragmentProductsBinding binding;

    IProducts am;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.products);
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        user = am.getUser();

        am.profile(new MainActivity.Return() {
            @Override
            public void response(@NonNull String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String token = user.getToken();
                ShoppingCart cart = user.getShoppingCart();

                user = gson.fromJson(response, User.class);

                binding.name.setText(user.getFullname());
                user.setToken(token);
                user.setShoppingCart(cart);
                am.setUser(user);
            }

            @Override
            public boolean showDialog() {
                return false;
            }

            @Override
            public void error(@NonNull String response) {
            }
        });


        binding.bottomNavigation.setSelectedItemId(R.id.products);
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.products:
                        return true;
                    case R.id.history:
                        am.sendHistoryView();
                        return true;
                    case R.id.cart:
                        am.sendCartView();
                        return true;
                    case R.id.logout:
                        am.setUser(null);
                        am.sendLoginView();
                        return true;
                }
                return false;
            }
        });

        binding.productView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        binding.productView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.productView.getContext(), llm.getOrientation());
        binding.productView.addItemDecoration(dividerItemDecoration);

        am.getProducts(new MainActivity.Return() {
            @Override
            public void response(@NonNull String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Product[] products = gson.fromJson(response, Product[].class);

                ArrayList<Product> productArrayList = new ArrayList<>(Arrays.asList(products));

                updateCart(productArrayList);

                binding.productView.setAdapter(new ProductAdapter(user, productArrayList));
            }

            @Override
            public boolean showDialog() {
                return true;
            }

            @Override
            public void error(@NonNull String response) {
            }
        });

       return view;
    }

    public void updateCart(ArrayList<Product> products){
        HashMap<String, Product> new_products = new HashMap<>();
        for(Map.Entry<String, Product> entry
        : user.getShoppingCart().getProductList().entrySet()){
            Product product = products.get(products.indexOf(entry.getValue()));
            product.setQty(entry.getValue().getQty());
            new_products.put(entry.getKey(), product);
        }
        user.getShoppingCart().setProductList(new_products);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IProducts) {
            am = (IProducts) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public interface IProducts {
        User getUser();
        void sendHistoryView();
        void setUser(User user);
        void sendLoginView();
        void sendCartView();
        void profile(MainActivity.Return response);
        void getProducts(MainActivity.Return response);
    }
}