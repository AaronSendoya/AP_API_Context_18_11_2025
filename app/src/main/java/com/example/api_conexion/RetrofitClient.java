package com.example.api_conexion;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    // OJO: La URL base debe terminar siempre en "/"
    private static final String BASE_URL = "http://demoapi.somee.com/";

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Convierte JSON a Objetos automáticamente
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
