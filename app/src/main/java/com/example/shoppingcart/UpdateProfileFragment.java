package com.example.shoppingcart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.shoppingcart.databinding.FragmentUpdateProfileBinding;
import com.example.shoppingcart.models.User;
import com.example.shoppingcart.models.Utils;
import com.example.shoppingcart.databinding.FragmentUpdateProfileBinding;

import org.jetbrains.annotations.NotNull;


public class UpdateProfileFragment extends Fragment {

    FragmentUpdateProfileBinding binding;

    IUpdateProfile am;

    User user;

    String email, fullname, address;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IUpdateProfile) {
            am = (IUpdateProfile) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public interface IUpdateProfile {

        User getUser();

        void alert(String msg);

        void setUser(User user);

        void goBack();

        void update(com.example.shoppingcart.MainActivity.Return response, String ...data);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Update Profile");

        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        user = am.getUser();

        binding.editTextTextPersonName.setText(user.getFullname());
        binding.editTextTextPersonName4.setText(user.getAddress());
        binding.editTextTextPersonName5.setText(user.getEmail());

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fullname = binding.editTextTextPersonName.getText().toString();
                address = binding.editTextTextPersonName4.getText().toString();
                email = binding.editTextTextPersonName5.getText().toString();

                if(email.isEmpty() || fullname.isEmpty()  || address.isEmpty()){
                    am.alert("Please enter all values for updating!");
                    return;
                }

                am.update(new com.example.shoppingcart.MainActivity.Return() {
                    @Override
                    public void response(@NotNull String response) {
                        user.updateUser(fullname, address, email);
                        am.setUser(user);
                        am.alert("Profile Updated!");
                        am.goBack();
                    }

                    @Override
                    public boolean showDialog() {
                        return true;
                    }

                    @Override
                    public void error(@NotNull String response) {
                    }

                }, fullname, address, email);
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.goBack();
            }
        });

        return view;
    }

}