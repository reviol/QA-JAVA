package com.example.apiHelpers;

import com.example.apiHelpers.pojo.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("2")
    Call<User> getUser();
}
