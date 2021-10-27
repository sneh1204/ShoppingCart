package com.example.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.example.shoppingcart.databinding.FragmentShoppingCartBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.ShoppingCart;
import com.example.shoppingcart.models.User;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class ShoppingCartFragment extends Fragment {

    FragmentShoppingCartBinding binding;
    ICart am;
    User user;
    CartAdapter adapter;

    ActivityResultLauncher<Intent> dropinLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (result.getResultCode() == MainActivity.RESULT_OK) {
                    DropInResult dropresult = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                    String paymentMethodNonce = dropresult.getPaymentMethodNonce().getNonce();
                    am.checkout(new MainActivity.Return() {
                        @Override
                        public void response(@NonNull String response) {
                            user.setShoppingCart(new ShoppingCart());
                            binding.totalPrice.setText("$" + String.format("%.2f", user.getShoppingCart().getTotalCartCost()));
                            adapter.updateData();
                            am.alert("Purchase was successful!");
                        }

                        @Override
                        public void error(@NonNull String response) {
                        }

                        @Override
                        public boolean showDialog() {
                            return true;
                        }
                    }, paymentMethodNonce, user.getShoppingCart().getTotalCartCost(), user.getShoppingCart().getProductList());

                } else if (result.getResultCode() == MainActivity.RESULT_CANCELED) {
                    Toast.makeText(getContext(), "Checkout cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    // an error occurred, checked the returned exception
                    Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                    Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

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
                        am.sendHistoryView();
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

        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setShoppingCart(new ShoppingCart());
                adapter.updateData();
                binding.totalPrice.setText("$" + String.format("%.2f", user.getShoppingCart().getTotalCartCost()));
                am.alert("Cart cleared!");
            }
        });

        am.getProducts(new MainActivity.Return() {
            @Override
            public void response(@NonNull String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                Product[] products = gson.fromJson(response, Product[].class);

                adapter = new CartAdapter(user, new ArrayList<>(Arrays.asList(products)), new CartAdapter.onUpdate() {
                    @Override
                    public void update(User user) {
                        binding.totalPrice.setText("$" + String.format("%.2f", user.getShoppingCart().getTotalCartCost()));
                        am.setUser(user);
                    }
                });
                binding.recyclerView.setAdapter(adapter);
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
                if(user.getShoppingCart().getProductList().size() == 0){
                    am.alert("Please add something to cart to checkout!");
                    return;
                }

                am.clientToken(new MainActivity.Return() {
                    @Override
                    public void response(@NonNull String response) {
                        DropInRequest dropInRequest = new DropInRequest()
                                .clientToken(response); // client token
                        dropinLauncher.launch(dropInRequest.getIntent(getContext()));
                    }

                    @Override
                    public void error(@NonNull String response) {

                    }

                    @Override
                    public boolean showDialog() {
                        return true;
                    }
                });

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
        void checkout(MainActivity.Return response, String nonce, double amount, HashMap<String, Product> productList);
        void alert(String msg);
        void setUser(User user);
        void sendHistoryView();
        void clientToken(MainActivity.Return response);
        User getUser();
        void sendLoginView();
        void sendProductsView();
        void getProducts(MainActivity.Return response);
    }
}