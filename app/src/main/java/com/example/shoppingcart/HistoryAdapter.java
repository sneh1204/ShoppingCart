package com.example.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingcart.databinding.CartItemViewBinding;
import com.example.shoppingcart.databinding.HistoryViewBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.Transaction;
import com.example.shoppingcart.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.UViewHolder>{

    HistoryViewBinding binding;
    ViewGroup parent;
    ArrayList<Transaction> transactions;
    User user;
    onClick am;

    public HistoryAdapter(User user, ArrayList<Transaction> transactions, onClick am) {
        this.transactions = transactions;
        this.user = user;
        this.am = am;
    }

    interface onClick{
        void doWork(Transaction transaction);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public UViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = HistoryViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        this.parent = parent;
        return new UViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UViewHolder holder, int position) {

        Transaction transaction = transactions.get(position);

        holder.binding.textView14.setText("Transaction - " + transaction.gettId());
        holder.binding.textView15.setText("Amount - $" + transaction.getAmount());
        holder.binding.textView16.setText(transaction.prettyDate());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.doWork(transaction);
            }
        });

    }
    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class UViewHolder extends RecyclerView.ViewHolder {

        HistoryViewBinding binding;

        public UViewHolder(@NonNull HistoryViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
