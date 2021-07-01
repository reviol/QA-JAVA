package com.example.apiHelpers;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class APIClientHelper {

    public static final String BASE_URL = "https:/reqres.in/api/users/";

    private static Retrofit retrofit = null;

    static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit;
    }
}
