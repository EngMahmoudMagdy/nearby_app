package com.magdy.nearby.data.provider

import android.location.Location

interface LocationProvider {
    fun getLocation(): Location?
}