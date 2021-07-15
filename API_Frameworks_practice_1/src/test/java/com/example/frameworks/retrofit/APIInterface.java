package com.example.frameworks.retrofit;

import com.example.frameworks.pojo.CreateUserRequest;
import com.example.frameworks.pojo.CreateUserResponse;
import com.example.frameworks.pojo.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @GET("users/2")
    Call<User> getUser();

    @POST("users")
    Call<CreateUserResponse> createUser(@Body CreateUserRequest body);
}
