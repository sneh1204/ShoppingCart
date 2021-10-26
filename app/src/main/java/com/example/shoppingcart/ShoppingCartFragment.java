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
import com.example.shoppingcart.models.ShoppingCart;
import com.example.shoppingcart.models.User;
import com.google.android.material.navigation.NavigationBarView;


public class ShoppingCartFragment extends Fragment {

    FragmentShoppingCartBinding binding;
    ICart am;
    ShoppingCart shoppingCart = new ShoppingCart();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.shoppingCart);
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

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

       // Log.d("demo", "onCreateView: "+ shoppingCart.getProductArrayList());
        //binding.recyclerView.setAdapter(new CartAdapter(shoppingCart.getProductArrayList()));

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
        void sendLoginView();
        void sendProductsView();
    }
}