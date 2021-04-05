package com.mahdikaseatashin.weatherforcast.models

data class WeatherModel(
    val cod : Int,
    val message : Int,
    val cnt : Int,
    val list : ArrayList<ListModel>,
    val city: City
)
