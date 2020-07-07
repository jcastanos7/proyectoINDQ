package com.example.proyectoindq.remote;

public class ApiUtils {
    private ApiUtils(){
    }
    public static final String base_url = "https://api.events.indqtech.com/";

    public static APIService getAPIApiService(){
        return RetrofitClient.getClient(base_url).create(APIService.class);
    }
}
