package com.magdy.nearby.data.repository

import androidx.lifecycle.LiveData
import com.magdy.nearby.data.db.venues.VenueEntry

interface VenueRepository {
    suspend fun getCurrentVenues():LiveData<VenueEntry>
}