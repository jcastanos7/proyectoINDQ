package com.example.proyectoindq.remote;

import com.example.proyectoindq.datos.Events;
import com.example.proyectoindq.datos.Login;
import com.example.proyectoindq.datos.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.Call;

public interface APIService {
    @POST("https://api.events.indqtech.com/users")
    @FormUrlEncoded
    Call<User> savePost(@Field("firstName")String firstName,
                        @Field("lastName")String lastName,
                        @Field("email")String email,
                        @Field("password")String password,
                        @Field("gender")String gender
                               );

    @POST("https://api.events.indqtech.com/users/login")
    @FormUrlEncoded
    Call<Login> saveLogin(@Field("email")String email,
                          @Field("password")String pasword
                          );

    @GET("events")
    Call<ArrayList<Events>> getEvents(@Header("Authorization")String authToken);

    @POST("https://api.events.indqtech.com/events")
    @FormUrlEncoded
    Call<Events> saveEvent(@Header("Authorization")String authToken,
                           @Field("id") String id,
                           @Field("title")String title,
                           @Field("description")String description,
                           @Field("date")String date,
                           @Field("image") String image,
                           @Field("attendances")Integer attendances,
                           @Field("willYouAttend")Boolean attend,
                           @Field("location")List<Integer> location
                            );

}
