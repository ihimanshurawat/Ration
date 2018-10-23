package com.himanshurawat.ration.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.himanshurawat.ration.R;
import com.himanshurawat.ration.respository.network.NetworkClient;
import com.himanshurawat.ration.respository.network.NetworkService;
import com.himanshurawat.ration.respository.network.SignupReponse;
import com.himanshurawat.ration.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button loginButton;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private ProgressBar progressBar;
    private Retrofit retrofit;
    private TextView signupTextView;

    private SharedPreferences userPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.activity_login_button);
        passwordEditText = findViewById(R.id.activity_login_password_edit_text);
        signupTextView = findViewById(R.id.activity_login_signup_text_view);
        usernameEditText = findViewById(R.id.activity_login_username_edit_text);

        loginButton.setOnClickListener(this);
        signupTextView.setOnClickListener(this);

        retrofit = NetworkClient.getNetworkClient();
        userPref = getApplicationContext().getSharedPreferences(Constant.USER_PREF,MODE_PRIVATE);

        progressBar = findViewById(R.id.activity_login_progress_bar);
        progressBar.setVisibility(View.GONE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userPref.getString(Constant.SESSION_KEY, null) != null) {
            startActivity(new Intent(LoginActivity.this,UserActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        //Login
        if(v.getId() == loginButton.getId()){

            String password = passwordEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();

            if(password.isEmpty()|| username.isEmpty()){
                Toast.makeText(this,"Error in Username or Password",Toast.LENGTH_SHORT).show();
                return;
            }

            validateUser(username,password);


        }

        //Signup TextView
        if(v.getId() == signupTextView.getId()) {
            startActivityForResult(new Intent(this, SignupActivity.class), Constant.START_SIGNUP_ACTIVITY);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Constant.START_SIGNUP_ACTIVITY && resultCode == RESULT_OK){
            if(data != null) {
                String username = data.getStringExtra(Constant.USERNAME_KEY);
                usernameEditText.setText(username);
            }
        }
    }

    private void validateUser(final String username, String password) {
        //Progress Bar Visible
        progressBar.setVisibility(View.VISIBLE);
        NetworkService networkService = retrofit.create(NetworkService.class);
        networkService.getLoginInfo(username,password).enqueue(new Callback<SignupReponse>() {
            @Override
            public void onResponse(Call<SignupReponse> call, Response<SignupReponse> response) {

                if (response.body() != null) {
                    //Progress Bar Visibility GONE
                    progressBar.setVisibility(View.GONE);

                    if (response.body().getStatus().equals(Constant.RESPONSE_SUCCESS)) {
                        userPref.edit().putString(Constant.SESSION_KEY, response.body().getToken()).apply();
                        startActivity(new Intent(LoginActivity.this, UserActivity.class));
                        finish();
                    }

                } else if(response.errorBody() != null) {
                    //Progress Bar Visibility GONE
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignupReponse> call, Throwable t) {
                //Progress Bar Visibility GONE
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
