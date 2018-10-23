package com.himanshurawat.ration.respository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.himanshurawat.ration.respository.db.PeopleDatabase;
import com.himanshurawat.ration.respository.db.entity.People;
import com.himanshurawat.ration.respository.network.NetworkClient;
import com.himanshurawat.ration.respository.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {

    private Context context;
    private PeopleDatabase database;

    public Repository(Context context){
        this.context = context;
        database = PeopleDatabase.getDatabase(context);
    }

    public LiveData<List<People>> getPeople(){
        LiveData<List<People>> listPeople = database.getPeopleDao().getPeople();

        if(listPeople.getValue()== null){
            if(isConnected()) {
                fetchFromNetwork();
            }else{
                Toast.makeText(context,"Unable to Fetch Data",Toast.LENGTH_SHORT).show();
            }
        }
        return listPeople;
    }

    private void fetchFromNetwork() {
        Retrofit networkClient = NetworkClient.getNetworkClient();
        NetworkService networkService = networkClient.create(NetworkService.class);
        networkService.getPeopleFromNetWork("hey",1);

    }


    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo.isConnected();
    }

}
