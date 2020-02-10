package com.magdy.nearby.data.repository

import androidx.lifecycle.LiveData
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import com.magdy.nearby.data.db.venues.VenueEntry

interface VenueRepository {
    fun getVenueList():LiveData<MutableList<ItemVenueEntry>>
    fun getVenuePhotos():LiveData<MutableList<ItemVenueEntry>>
}