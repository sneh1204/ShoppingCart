package com.example.shoppingcart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.databinding.FragmentProductsBinding;
import com.google.android.material.navigation.NavigationBarView;


public class ProductsFragment extends Fragment {

    FragmentProductsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.products);
        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.bottomNavigation.setSelectedItemId(R.id.products);
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.products:
                        return true;
                    case R.id.history:
                        return true;
                    case R.id.cart:
                        return true;
                    case R.id.logout:
                        return true;
                }
                return false;
            }
        });

       return view;

    }
}