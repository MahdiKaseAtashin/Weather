package com.mahdikaseatashin.weatherforcast.models

data class ListModel(
    val dt : Long,
    val main : Main,
    val weather : ArrayList<Weather>,
    val clouds : Clouds,
    val wind : Wind,
    val visibility : Int,
    val pop: Double,
    val sys : Sys,
    val dt_txt : String
)
