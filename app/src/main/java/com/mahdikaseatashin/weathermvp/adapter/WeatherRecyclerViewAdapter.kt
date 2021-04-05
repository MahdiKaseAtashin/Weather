package com.mahdikaseatashin.weathermvp.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahdikaseatashin.weatherforcast.models.ListModel
import com.mahdikaseatashin.weathermvp.R
import com.mahdikaseatashin.weathermvp.utils.Constants
import kotlinx.android.synthetic.main.item_rv_weather.view.*

class WeatherRecyclerViewAdapter(
    private val weatherList: ArrayList<ListModel>,
    val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_rv_weather, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_date_item.text = Constants.convertTime(weatherList[position].dt)
        holder.itemView.tv_min_max_temp_item.text =
            "${weatherList[position].main.temp_min} \u2103 \n${weatherList[position].main.temp_max} \u2103"
        when (weatherList[position].weather[0].description) {
            "clear sky" -> holder.itemView.image_view_item.setImageResource(R.drawable.clear_sky)
            "few clouds" -> holder.itemView.image_view_item.setImageResource(R.drawable.few_clouds)
            "scattered clouds" -> holder.itemView.image_view_item.setImageResource(R.drawable.scattered_clouds)
            "broken clouds" -> holder.itemView.image_view_item.setImageResource(R.drawable.broken_cloud)
            "shower rain" -> holder.itemView.image_view_item.setImageResource(R.drawable.shower_rain)
            "rain" -> holder.itemView.image_view_item.setImageResource(R.drawable.rain)
            "thunderstorm" -> holder.itemView.image_view_item.setImageResource(R.drawable.thunder_storm)
            "snow" -> holder.itemView.image_view_item.setImageResource(R.drawable.snow)
            "mist" -> holder.itemView.image_view_item.setImageResource(R.drawable.mist)
        }
    }

    override fun getItemCount(): Int = weatherList.size

}
