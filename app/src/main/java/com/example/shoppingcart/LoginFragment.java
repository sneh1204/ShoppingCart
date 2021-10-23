package com.example.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shoppingcart.databinding.FragmentLoginBinding;
import com.example.shoppingcart.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;

    String email, pass;

    ILogin am;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ILogin) {
            am = (ILogin) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public interface ILogin {

        void setUser(User user);

        void alert(String msg);

        void sendRegisterView();

        void sendProfileView();

        void login(MainActivity.Return response, String... data);

        void sendProductsView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Login");

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.createNewAccountId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.sendRegisterView();
            }
        });

        binding.loginButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = binding.emailTextFieldId.getText().toString();
                pass = binding.passwordTextFieldId.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    am.alert("Please enter all values!");
                    return;
                }

                am.login(new MainActivity.Return() {
                    @Override
                    public void response(@NotNull String response) {
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        User user = gson.fromJson(response, User.class);

                        am.setUser(user);
                        //am.sendProfileView();
                        am.sendProductsView();
                    }

                    @Override
                    public void error(@NotNull String response) {
                    }

                }, email, pass);

            }
        });

        return view;
    }

}