package com.mahdikaseatashin.weathermvp.weather_forcast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mahdikaseatashin.weatherforcast.models.ListModel
import com.mahdikaseatashin.weatherforcast.models.WeatherModel
import com.mahdikaseatashin.weathermvp.R
import com.mahdikaseatashin.weathermvp.adapter.WeatherRecyclerViewAdapter
import com.mahdikaseatashin.weathermvp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class WeatherActivity : AppCompatActivity(), WeatherContract, WeatherContract.View {

    private var weatherPresenter: WeatherPresenter? = null
    private var weatherList: ArrayList<ListModel>? = null
    private var weatherData: WeatherModel? = null
    private var weatherAdapter: WeatherRecyclerViewAdapter? = null
    private var pbLoading: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

        //Initializing presenter
        weatherPresenter = WeatherPresenter(this)
        weatherPresenter!!.requestPermissions()


    }

    private fun initUI() {
        weatherList = ArrayList<ListModel>()
        weatherAdapter = WeatherRecyclerViewAdapter(weatherList!!, this)
        rv_main.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_main.adapter = weatherAdapter
        pbLoading = circularProgressIndicator
    }

    companion object {
        private const val TAG = "WeatherActivity"
    }


    override fun showLocationPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission")
            .setMessage("Please give us the permission to access the location")
            .setPositiveButton("Setting") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

    }

    override fun showSnackBarPermissionDenied() {
        val snackBar = Snackbar.make(
            parent_layout_main,
            "Give location permission",
            Snackbar.LENGTH_LONG
        ).setAction(
            "Setting"
        ) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        snackBar.show()

    }

    override fun showSnackBarLocationRequest() {
        Snackbar.make(parent_layout_main, "Turn on location provider!", Snackbar.LENGTH_LONG)
            .setAction("Setting") {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }.show()
    }

    override fun setTitleData(weather: WeatherModel) {
        tv_city.text = weather.city.name
        tv_date.text = Constants.getCurrentDate()
        tv_temp.text = weather.list[0].main.temp.toString()
    }

    override fun getViewActivity(): WeatherActivity {
        return this
    }

    override fun showProgress() {
        pbLoading!!.visibility = View.VISIBLE
        tv_wait.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pbLoading!!.visibility = View.GONE
        tv_wait.visibility = View.GONE
        tv_metrics.visibility = View.VISIBLE
        tv_metrics2.visibility = View.VISIBLE
    }

    override fun setDataToRecyclerView(weatherArrayList: List<ListModel>) {
        weatherList!!.addAll(weatherArrayList)
        weatherAdapter!!.notifyDataSetChanged()
    }

    override fun onResponseFailure(throwable: Throwable?) {
        Toast.makeText(this, "Communication Error!", Toast.LENGTH_LONG).show()
    }
}
