package com.magdy.nearby.data.provider

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat

class LocationProviderImpl(
    context: Context
) : LocationProvider {
    private val appContext = context.applicationContext

    // The minimum distance to change Updates in meters
    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters
    // The minimum time between updates in milliseconds
    private val MIN_TIME_BW_UPDATES = (1000 * 60).toLong() // 1 minute

    override fun getLocation(): Location? {
        var location: Location? = null
        val locationManager = appContext.getSystemService(LOCATION_SERVICE) as LocationManager
        if (appContext != null && locationManager != null)
            try {
                if (ContextCompat.checkSelfPermission(
                        appContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return null
                }
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), object : LocationListener {
                        override fun onLocationChanged(location: Location) {

                        }

                        override fun onStatusChanged(
                            provider: String,
                            status: Int,
                            extras: Bundle
                        ) {

                        }

                        override fun onProviderEnabled(provider: String) {

                        }

                        override fun onProviderDisabled(provider: String) {

                        }
                    })
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                // if Network is disabled get lat/long using GPS Services
                if (location == null) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), object : LocationListener {
                            override fun onLocationChanged(location: Location) {

                            }

                            override fun onStatusChanged(
                                provider: String,
                                status: Int,
                                extras: Bundle
                            ) {

                            }

                            override fun onProviderEnabled(provider: String) {

                            }

                            override fun onProviderDisabled(provider: String) {

                            }
                        })
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        if (location == null) {
            appContext.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        return location
    }
}