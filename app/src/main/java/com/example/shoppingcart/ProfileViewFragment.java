package com.example.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shoppingcart.databinding.FragmentProfileViewBinding;
import com.example.shoppingcart.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

public class ProfileViewFragment extends Fragment {

    FragmentProfileViewBinding binding;

    IProfileView am;

    User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IProfileView) {
            am = (IProfileView) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public interface IProfileView {

        void setUser(User user);

        User getUser();

        void alert(String msg);

        void sendUpdateProfileView();

        void sendLoginView();

        void profile(com.example.shoppingcart.MainActivity.Return response);

    }

    @Override
    public void onResume() {
        super.onResume();
        am.profile(new com.example.shoppingcart.MainActivity.Return() {
            @Override
            public void response(@NotNull String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String token = user.getToken();

                user = gson.fromJson(response, User.class);
                user.setToken(token);

                am.setUser(user);

                binding.textView6.setText(user.getFullname());
                binding.textView7.setText(user.getEmail());
                binding.textView10.setText(user.getAddress());
            }

            @Override
            public void error(@NotNull String response) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");

        binding = FragmentProfileViewBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        user = am.getUser();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.sendUpdateProfileView();
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.setUser(null);
                am.sendLoginView();
            }
        });

        return view;
    }
}