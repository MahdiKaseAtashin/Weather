package com.mahdikaseatashin.weathermvp.weather_forcast

import android.util.Log
import com.mahdikaseatashin.weatherforcast.models.ListModel
import com.mahdikaseatashin.weatherforcast.models.WeatherModel
import com.mahdikaseatashin.weathermvp.networks.ApiClient
import com.mahdikaseatashin.weathermvp.networks.ApiInterface
import com.mahdikaseatashin.weathermvp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherListModel : WeatherContract.Model {

    override fun getWeatherList(
        onFinishedListener: WeatherContract.Model.OnFinishedListener?,
        lat: Double,
        lng: Double
    ) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<WeatherModel> =
            apiService.getWeather(lat, lng, Constants.API_KEY, Constants.METRIC_UNIT)
        call.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(
                call: Call<WeatherModel>,
                response: Response<WeatherModel>
            ) {
                if (response.isSuccessful) {
                    val weatherList: WeatherModel = response.body()!!
                    onFinishedListener!!.onFinished(weatherList)
                } else {
                    Log.e(TAG, "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                onFinishedListener!!.onFailure(t)
            }
        })
    }

    companion object {
        private const val TAG = "WeatherListModel"
    }
}
