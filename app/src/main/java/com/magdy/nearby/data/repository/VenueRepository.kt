package com.magdy.nearby.data.repository

import androidx.lifecycle.LiveData
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import com.magdy.nearby.data.network.response.PhotoResponse

interface VenueRepository {
    fun getVenueList(): LiveData<MutableList<ItemVenueEntry>?>
    fun getVenueListThrowable(): LiveData<Throwable>
    fun getVenuePhotos(venueId: String): LiveData<PhotoResponse?>
}