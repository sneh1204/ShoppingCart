package com.example.shoppingcart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppingcart.models.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements ShoppingCartFragment.ICart, ProductsFragment.IProducts, com.example.shoppingcart.LoginFragment.ILogin, ProfileViewFragment.IProfileView, RegisterFragment.IRegister, UpdateProfileFragment.IUpdateProfile {

    private final OkHttpClient client = new OkHttpClient();

    User user = null;

    public static final String BASE_URL  = "https://mysterious-beach-05426.herokuapp.com/"; // http://10.0.2.2:3000/ or https://mysterious-beach-05426.herokuapp.com/

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendLoginView();
    }

    public void profile(Return response){
        Request request = new Request.Builder()
                .url(BASE_URL + "profile/view")
                .addHeader("x-jwt-token", user.getToken())
                .build();
        sendRequest(request, response);
    }

    public void update(Return response, String ...data){
        FormBody formBody = new FormBody.Builder()
                .add("fullname", data[0])
                .add("age", data[1])
                .add("weight", data[2])
                .add("address", data[3])
                .add("email", data[4])
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL + "profile/update")
                .addHeader("x-jwt-token", user.getToken())
                .post(formBody)
                .build();
        sendRequest(request, response);
    }

    public void register(Return response, String... data){
        FormBody formBody = new FormBody.Builder()
                .add("fullname", data[0])
                .add("age", data[1])
                .add("weight", data[2])
                .add("address", data[3])
                .add("email", data[4])
                .add("pass", data[5])
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL + "auth/signup")
                .post(formBody)
                .build();
        sendRequest(request, response);
    }

    public void login(Return response, String... data){
        FormBody formBody = new FormBody.Builder()
                .add("email", data[0])
                .add("pass", data[1])
                .build();
        Request request = new Request.Builder()
                .url(BASE_URL + "auth/login")
                .post(formBody)
                .build();
        sendRequest(request, response);
    }

    @Override
    public void sendProductsView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, new com.example.shoppingcart.ProductsFragment())
                .commit();
    }

    public void sendLoginView(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, new com.example.shoppingcart.LoginFragment())
                .commit();
    }

    @Override
    public void sendCartView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, new com.example.shoppingcart.ShoppingCartFragment())
                .commit();
    }

    public void sendRegisterView(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }

    public void sendProfileView(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, new ProfileViewFragment())
                .commit();
    }

    public void sendUpdateProfileView(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, new UpdateProfileFragment())
                .addToBackStack(null)
                .commit();
    }

    public void goBack(){
        getSupportFragmentManager().popBackStack();
    }

    ProgressDialog dialog;

    public void alert(String alert) {
        runOnUiThread(() -> new AlertDialog.Builder(this)
                .setTitle("Info")
                .setMessage(alert)
                .setPositiveButton("Okay", null)
                .show());
    }

    private void sendRequest(Request request, Return callback) {
        toggleDialog(true, "Processing...");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                toggleDialog(false, null);
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                toggleDialog(false, null);

                ResponseBody responseBody = response.body();

                String res_string;
                if (responseBody != null) {
                    res_string = responseBody.string();

                    if (response.isSuccessful()) {
                        runOnUiThread(() -> callback.response(res_string));
                    } else {
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonObject = new JSONObject(res_string);
                                if (jsonObject.has("message"))
                                    alert(jsonObject.getString("message"));
                            }catch (JSONException exc){
                            }
                        });
                        callback.error(res_string);
                    }
                }
            }
        });
    }

    interface Return{

        void response(@NotNull String response);

        void error(@NotNull String response);

    }

    public void toggleDialog(boolean show) {
        toggleDialog(show, null);
    }

    public void toggleDialog(boolean show, String msg) {
        if (show) {
            dialog = new ProgressDialog(this);
            if (msg == null)
                dialog.setMessage("Loading...");
            else
                dialog.setMessage(msg);
            dialog.setCancelable(false);
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }

}