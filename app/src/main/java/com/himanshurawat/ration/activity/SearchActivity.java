package com.himanshurawat.ration.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.himanshurawat.ration.R;
import com.himanshurawat.ration.adapter.PeopleAdapter;
import com.himanshurawat.ration.respository.db.entity.People;
import com.himanshurawat.ration.respository.network.NetworkClient;
import com.himanshurawat.ration.respository.network.NetworkService;
import com.himanshurawat.ration.respository.network.SearchResponse;
import com.himanshurawat.ration.util.Constant;
import com.himanshurawat.ration.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, PeopleAdapter.OnPeopleSelectedListener {



    private List<People> peopleList;
    private NetworkService networkService;

    private PeopleAdapter peopleAdapter;
    private ProgressBar progressBar;
    private RecyclerView peopleRecyclerView;
    private Retrofit retrofit;

    private SharedPreferences userPref;
    private String token;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.activity_search_progress_bar);
        peopleRecyclerView = findViewById(R.id.activity_search_recycler_view);
        peopleList = new ArrayList<>();
        peopleAdapter = new PeopleAdapter(this,peopleList,this);
        peopleRecyclerView.setAdapter(peopleAdapter);
        peopleRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        progressBar.setIndeterminate(true);
        viewGone(progressBar);


        retrofit = NetworkClient.getNetworkClient();
        networkService = retrofit.create(NetworkService.class);
        userPref = getApplicationContext().getSharedPreferences(Constant.USER_PREF,MODE_PRIVATE);
        token = userPref.getString(Constant.SESSION_KEY,null);
        if(token == null){
            invalidateSession(this);
        }

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        if(menu != null){
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_search_icon).getActionView();
            if(searchView != null){
                searchView.setIconified(false);
                searchView.setIconifiedByDefault(true);
                searchView.setMaxWidth(Integer.MAX_VALUE);
                searchView.setOnQueryTextListener(this);
            }
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(!query.trim().isEmpty()) {
            viewVisible(progressBar);
            networkService.getSearchInfo(token,query).enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    if(response.body() != null){
                        if(response.body().getStatus().equals(Constant.RESPONSE_SUCCESS)) {
                            peopleList.clear();
                            peopleList.addAll(response.body().getPeople());
                            peopleAdapter.notifyDataSetChanged();
                            viewGone(progressBar);
                        }else if(response.body().getStatus().equals(Constant.RESPONSE_FAILURE)){
                            viewGone(progressBar);
                            if(response.body().getError().equals("invalid token")){
                                Toast.makeText(SearchActivity.this,"Invalid Token", Toast.LENGTH_LONG).show();
                                invalidateSession(SearchActivity.this);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Toast.makeText(SearchActivity.this,"Error "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public void onPeopleSelected(int position) {

    }


    private void viewGone(View view){
        view.setVisibility(View.GONE);
    }

    private void viewVisible(View view){
        view.setVisibility(View.VISIBLE);
    }

    private void invalidateSession(Context context){
        Toast.makeText(this,"Session has Expired",Toast.LENGTH_LONG).show();
        SharedPreferences userPref = context.getApplicationContext().getSharedPreferences(Constant.USER_PREF,MODE_PRIVATE);
        userPref.edit().putString(Constant.SESSION_KEY,null).apply();
        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
