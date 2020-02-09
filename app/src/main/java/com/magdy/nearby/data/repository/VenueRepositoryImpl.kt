package com.magdy.nearby.data.repository

import androidx.lifecycle.LiveData
import com.magdy.nearby.data.db.venues.VenueEntry
import com.magdy.nearby.data.network.NetworkDataSource
import com.magdy.nearby.data.network.response.VenueResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VenueRepositoryImpl (
    private val networkDataSource: NetworkDataSource
): VenueRepository {
    init {
        networkDataSource.downloadedVenueList.observeForever {
            presistFetchedVenueList(it)
        }
    }
    override suspend fun getCurrentVenues(): LiveData<VenueEntry> {
     /*   return withContext(Dispatchers.IO)
        {
            initWeatherData()
            return@withContext
        }*/
    }

    private fun initWeatherData() {

    }

    private fun presistFetchedVenueList(fetchedListResponse: VenueResponse)
    {

    }
}