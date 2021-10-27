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

import com.example.shoppingcart.databinding.FragmentHistoryBinding;
import com.example.shoppingcart.models.Transaction;
import com.example.shoppingcart.models.User;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;


public class HistoryFragment extends Fragment {

    FragmentHistoryBinding binding;

    IHistory am;

    User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IHistory) {
            am = (IHistory) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public interface IHistory {

        void setUser(User user);

        User getUser();

        void alert(String msg);

        void sendCartView();

        void sendTransactionView(Transaction transaction);

        void sendProductsView();

        void sendLoginView();

        void history(MainActivity.Return response);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("History");

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        user = am.getUser();

        am.history(new MainActivity.Return() {
            @Override
            public void response(@NonNull String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                Transaction[] transactions = gson.fromJson(response, Transaction[].class);
                binding.recyclerView.setAdapter(new HistoryAdapter(user, new ArrayList<>(Arrays.asList(transactions)), new HistoryAdapter.onClick() {
                    @Override
                    public void doWork(Transaction transaction) {
                        am.sendTransactionView(transaction);
                    }
                }));
            }

            @Override
            public void error(@NonNull String response) {
            }

            @Override
            public boolean showDialog() {
                return true;
            }
        });

        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerView.getContext(), llm.getOrientation());
        binding.recyclerView.addItemDecoration(dividerItemDecoration);

        binding.bottomNavigation.setSelectedItemId(R.id.history);
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

        return view;
    }
}