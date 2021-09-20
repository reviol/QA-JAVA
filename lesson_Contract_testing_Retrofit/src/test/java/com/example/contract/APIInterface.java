package com.example.contract;

import com.example.contract.pojo.CreateUserRequest;
import com.example.contract.pojo.CreateUserResponse;
import com.example.contract.pojo.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("users/{userId}")
    Call<User> getUser(@Path("userId") String userId);

    @POST("users")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest body);
}
