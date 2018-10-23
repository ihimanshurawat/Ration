package com.himanshurawat.ration.respository.network;

import com.himanshurawat.ration.util.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient  {

    private static Retrofit INSTANCE;

    public static synchronized Retrofit getNetworkClient(){
        if(INSTANCE == null){
            INSTANCE = new Retrofit.Builder().
                    baseUrl(Constant.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return INSTANCE;
    }

}
