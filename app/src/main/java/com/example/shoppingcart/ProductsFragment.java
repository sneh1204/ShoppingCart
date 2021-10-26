package com.example.shoppingcart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.databinding.FragmentProductsBinding;
import com.example.shoppingcart.models.Product;
import com.example.shoppingcart.models.ShoppingCart;
import com.example.shoppingcart.models.User;
import com.example.shoppingcart.models.Utils;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductsFragment extends Fragment {

    private final OkHttpClient client = new OkHttpClient();
    FragmentProductsBinding binding;
    IProducts am;
    User user;
    ShoppingCart cart = new ShoppingCart();

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

        getAllProducts();

       return view;
    }

    /*.....To Get ALL Products.....*/
    public void getAllProducts() {
        Request request = new Request.Builder()
                .url("https://mysterious-beach-05426.herokuapp.com/product/getAll")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseObject = response.body().string();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Product>>(){}.getType();
                    ArrayList<Product> productArrayList = gson.fromJson(responseObject, type);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            reloadProductList(productArrayList);
                        }
                    });
                }else{
                    try {
                        JSONObject responseObject = new JSONObject(response.body().string());
                        String errorMessage = responseObject.getString(Utils.MESSAGE);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getAlertDialogBox(errorMessage);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
    }

    public void reloadProductList(ArrayList<Product> productArrayList){
        binding.productView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        binding.productView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.productView.getContext(), llm.getOrientation());
        binding.productView.addItemDecoration(dividerItemDecoration);

        binding.productView.setAdapter(new ProductAdapter(productArrayList));
    }

    /* Alert Dialog Box */
    public void getAlertDialogBox(String errorMessage){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.errorMessage))
                .setMessage(errorMessage);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

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