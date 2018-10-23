package com.himanshurawat.ration.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.himanshurawat.ration.respository.db.PeopleDatabase;
import com.himanshurawat.ration.respository.db.entity.People;
import com.himanshurawat.ration.respository.network.NetworkClient;
import com.himanshurawat.ration.respository.network.NetworkService;
import com.himanshurawat.ration.respository.network.PeopleResponse;
import com.himanshurawat.ration.util.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserViewModel extends AndroidViewModel {


    private PeopleDatabase database = PeopleDatabase.getDatabase(this.getApplication());
    private SharedPreferences userPref = this.getApplication().getSharedPreferences(Constant.USER_PREF,Context.MODE_PRIVATE);
    public MutableLiveData<Boolean> isSessionValid = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        isSessionValid.setValue(true);
    }





    public LiveData<List<People>> getData(){
        return database.getPeopleDao().getPeople();
    }

    public void fetchFromInternet(String token,int page){
        Retrofit retrofit = NetworkClient.getNetworkClient();
        NetworkService service = retrofit.create(NetworkService.class);
        service.getPeopleFromNetWork(token,page).enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                if(response.body() != null){
                    if(response.body().getStatus().equals(Constant.RESPONSE_SUCCESS)){
                        long[] size = database.getPeopleDao().addPeople(response.body().getPeople());
                        Log.i("ItemsAdded",size.length+"");
                    }else if(response.body().getStatus().equals(Constant.RESPONSE_FAILURE)) {
                        isSessionValid.setValue(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {

            }
        });
    }


}
