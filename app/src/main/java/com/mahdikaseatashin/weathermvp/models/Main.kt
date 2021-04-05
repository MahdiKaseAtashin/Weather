package com.mahdikaseatashin.weatherforcast.models

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val sea_level: Double,
    val grnd_level: Double,
    val humidity: Double,
    val temp_kf: Double,
)
