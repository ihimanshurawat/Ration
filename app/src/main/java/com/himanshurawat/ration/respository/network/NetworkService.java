package com.himanshurawat.ration.respository.network;

import com.himanshurawat.ration.respository.db.entity.People;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @GET("people/{page}")
    Call<PeopleResponse> getPeopleFromNetWork(@Header("token") String token, @Path("page")int page);


    @FormUrlEncoded
    @POST("signup")
    Call<SignupReponse>getSignupInfo(@Field("username") String user, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<SignupReponse>getLoginInfo(@Field("username") String user, @Field("password") String password);

    @GET("findPerson")
    Call<SearchResponse> getSearchInfo(@Header("token")String token, @Query("word") String name);




}
