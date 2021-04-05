package com.mahdikaseatashin.weathermvp.weather_forcast

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mahdikaseatashin.weatherforcast.models.ListModel
import com.mahdikaseatashin.weatherforcast.models.WeatherModel
import kotlinx.android.synthetic.main.activity_main.*

class WeatherPresenter(
    private var view: WeatherContract.View?
) :
    WeatherContract.Presenter, WeatherContract.Model.OnFinishedListener {

    private var weatherListModel: WeatherContract.Model = WeatherListModel()

    override fun requestPermissions() {
        if (!isLocationEnabled()) {
            view!!.showSnackBarLocationRequest()
        } else {
            Dexter.withContext(view!!.getViewActivity()).withPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {

                        requestLocationData()

                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        view!!.showSnackBarPermissionDenied()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    view!!.showLocationPermissionDialog()
                }

            }).onSameThread().check()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            view!!.getViewActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mFusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(view!!.getViewActivity())
        mFusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val mLastLocation: Location = result.lastLocation
            val lat = mLastLocation.latitude
            val lng = mLastLocation.longitude
            Log.e(TAG, "onLocationResult:\n lat : $lat\n lng : $lng")
            requestDataFromServer(lat, lng)
        }
    }

    override fun onPermissionsResult() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        view = null
    }

    fun requestDataFromServer(lat: Double, lng: Double) {
        if (view != null) {
            view!!.showProgress()
        }
        weatherListModel.getWeatherList(this, lat, lng)
    }

    override fun onFinished(weatherList: WeatherModel) {
        val list: ArrayList<ListModel> = weatherList.list
        view!!.setTitleData(weatherList)
        view!!.setDataToRecyclerView(list)
        if (view != null) {
            view!!.hideProgress()
        }
    }

    override fun onFailure(t: Throwable?) {
        view!!.onResponseFailure(t)
        if (view != null) {
            view!!.hideProgress()
        }
    }

    companion object {
        private const val TAG = "WeatherPresenter"
    }


}
