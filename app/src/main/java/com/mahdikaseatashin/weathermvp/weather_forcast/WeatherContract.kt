package com.mahdikaseatashin.weathermvp.weather_forcast

import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.mahdikaseatashin.weatherforcast.models.ListModel
import com.mahdikaseatashin.weatherforcast.models.WeatherModel


interface WeatherContract {

    interface Model {

        interface OnFinishedListener {
            fun onFinished(weatherList: WeatherModel)
            fun onFailure(t: Throwable?)
        }

        fun getWeatherList(onFinishedListener: OnFinishedListener?, lat: Double, lng: Double)
    }

    interface View {
        fun showLocationPermissionDialog()
        fun showSnackBarPermissionDenied()
        fun showSnackBarLocationRequest()
        fun setTitleData(weather: WeatherModel)
        fun getViewActivity(): Activity
        fun showProgress()
        fun hideProgress()
        fun setDataToRecyclerView(weatherArrayList: List<ListModel>)
        fun onResponseFailure(throwable: Throwable?)
    }

    interface Presenter {
        fun requestPermissions()
        fun onPermissionsResult()
        fun onDestroy()
    }

}
