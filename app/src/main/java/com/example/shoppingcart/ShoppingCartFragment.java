package com.example.shoppingcart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.databinding.FragmentShoppingCartBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.ShoppingCart;
import com.example.shoppingcart.models.User;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;


public class ShoppingCartFragment extends Fragment {

    FragmentShoppingCartBinding binding;
    ICart am;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.shoppingCart);
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        user = am.getUser();

        binding.bottomNavigation.setSelectedItemId(R.id.cart);
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.products:
                        am.sendProductsView();
                        return true;
                    case R.id.history:
                        return true;
                    case R.id.cart:
                        return true;
                    case R.id.logout:
                        am.setUser(null);
                        am.sendLoginView();
                        return true;
                }
                return false;
            }
        });

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), llm.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);

        am.getProducts(new MainActivity.Return() {
            @Override
            public void response(@NonNull String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Product[] products = gson.fromJson(response, Product[].class);

                binding.recyclerView.setAdapter(new CartAdapter(user, new ArrayList<>(Arrays.asList(products)), new CartAdapter.onUpdate() {
                    @Override
                    public void update(User user) {
                        binding.totalPrice.setText("$" + String.format("%.2f", user.getShoppingCart().getTotalCartCost()));
                        am.setUser(user);
                    }
                }));
            }

            @Override
            public boolean showDialog() {
                return true;
            }

            @Override
            public void error(@NonNull String response) {
            }
        });

        binding.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ICart) {
            am = (ICart) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public interface ICart {
        void setUser(User user);
        User getUser();
        void sendLoginView();
        void sendProductsView();
        void getProducts(MainActivity.Return response);
    }
}