package com.magdy.nearby.data.provider

import android.location.Location

/*
* Location provider interface to get the location by instance
* */
interface LocationProvider {
    fun getLocation(): Location?
}