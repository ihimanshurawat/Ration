package com.himanshurawat.ration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    private Button signupButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.activity_signup_username_edit_text);
        passwordEditText = findViewById(R.id.activity_signup_password_edit_text);
        progressBar = findViewById(R.id.activity_signup_progress_bar);
        signupButton = findViewById(R.id.activity_signup_button);

        usernameEditText.setOnClickListener(this);
        passwordEditText.setOnClickListener(this);
        signupButton.setOnClickListener(this);


        retrofit = NetworkClient.getNetworkClient();
        //Progress Bar Visibility GONE
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if(signupButton.getId() == v.getId()){
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please Enter Valid Username or Password",Toast.LENGTH_SHORT).show();
                return;
            }
            //Progress Bar Visibility VISIBLE
            progressBar.setVisibility(View.VISIBLE);
            signupUser(username,password);

        }
    }

    private void signupUser(final String username, String password) {
        NetworkService networkService = retrofit.create(NetworkService.class);
        networkService.getSignupInfo(username,password).enqueue(new Callback<SignupReponse>() {
            @Override
            public void onResponse(Call<SignupReponse> call, Response<SignupReponse> response) {
                if(response.body() != null){
                    //Progress Bar Visibility GONE
                    progressBar.setVisibility(View.GONE);
                    if(response.body().getStatus().equals(Constant.RESPONSE_SUCCESS)){
                        Intent intent = new Intent();
                        intent.putExtra(Constant.USERNAME_KEY,username);
                        intent.putExtra(Constant.SESSION_KEY,response.body().getToken());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }

                if(response.errorBody() != null){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this,"Unable to Signup",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<SignupReponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this,"Error "+t.getMessage(),Toast.LENGTH_LONG).show();

                //Progress Bar Visibility GONE
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
