package com.example.shoppingcart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.databinding.FragmentHistoryBinding;
import com.example.shoppingcart.databinding.FragmentTransactionBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class TransactionFragment extends Fragment {

    private static final String TRANSACTION  = "transaction";
    Transaction transaction;
    FragmentTransactionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaction = (Transaction) getArguments().getSerializable(TRANSACTION);
        }
    }

    public static TransactionFragment newInstance(Transaction transaction){
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRANSACTION, transaction);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Transaction Details");
        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.textView17.setText("Total - $" + transaction.getAmount());
        binding.textView18.setText("Date - " + transaction.prettyDate());

        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Product>>(){}.getType();
        HashMap<String, Product> productsMap = gson.fromJson(transaction.getProducts(), type);

        binding.transactions.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        binding.transactions.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.transactions.getContext(), llm.getOrientation());
        binding.transactions.addItemDecoration(dividerItemDecoration);

        binding.transactions.setAdapter(new TransactionAdapter(productsMap));

        return view;
    }
}