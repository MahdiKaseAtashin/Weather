package com.mahdikaseatashin.weathermvp.networks

import com.mahdikaseatashin.weatherforcast.models.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("2.5/forecast")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon : Double,
        @Query("appid") appid : String,
        @Query("units") units : String
    ): Call<WeatherModel>

}
