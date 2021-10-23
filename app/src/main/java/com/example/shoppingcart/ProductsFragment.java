package com.example.shoppingcart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.databinding.FragmentProductsBinding;
import com.example.shoppingcart.models.User;
import com.google.android.material.navigation.NavigationBarView;


public class ProductsFragment extends Fragment {

    FragmentProductsBinding binding;
    IProducts am;
    User user;

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

        user = am.getUser();
        Log.d("demo", "onCreateView: "+ user);
        binding.name.setText(user.getFullname());

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
                        am.sendCartView();
                        return true;
                    case R.id.logout:
                        am.sendLoginView();
                        return true;
                }
                return false;
            }
        });

       return view;
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
        void sendLoginView();
        void sendCartView();
    }
}