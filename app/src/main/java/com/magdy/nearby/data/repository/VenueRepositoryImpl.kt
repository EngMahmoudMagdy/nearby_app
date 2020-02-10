package com.magdy.nearby.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.magdy.nearby.data.db.LocationDAO
import com.magdy.nearby.data.db.VenueListDAO
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import com.magdy.nearby.data.db.venues.LocationEntry
import com.magdy.nearby.data.network.NetworkDataSource
import com.magdy.nearby.data.network.response.VenueResponse
import com.magdy.nearby.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val LOCATION_LIMIT = 500.0

class VenueRepositoryImpl(
    private val venueListDAO: VenueListDAO,
    private val locationDAO: LocationDAO,
    private val locationProvider: LocationProvider,
    private val networkDataSource: NetworkDataSource
) : VenueRepository {
    override fun getVenuePhotos(): LiveData<MutableList<ItemVenueEntry>> {
        return MutableLiveData<MutableList<ItemVenueEntry>>()
    }

    init {
        networkDataSource.downloadedVenueList.observeForever {
            presistFetchedVenueList(it)
        }
    }

    override fun getVenueList(): LiveData<MutableList<ItemVenueEntry>> {
        initVenueListData()
        return venueListDAO.getVenueList()
    }

    private fun initVenueListData() {
        if (isDistanceMoreThanLimit()) {
            val location = locationProvider.getLocation()
            if (location != null)
                networkDataSource.fetchVenueList("${location.latitude},${location.longitude}")
        }

    }

    private fun isDistanceMoreThanLimit(): Boolean {
        val locationEntry = locationDAO.getRecentAddedLocation().value
        val location = locationProvider.getLocation()
        if (locationEntry==null)
            return true
        //Decide if the location is in the range limit which is here 500 or not
        return (locationEntry != null
                && location != null
                && calcDistance(
                        locationEntry,
                        location.latitude,
                        location.longitude) >= LOCATION_LIMIT)
    }

    private fun calcDistance(locationEntry: LocationEntry, lat: Double, lng: Double): Double {
        //Calculate the distance between the two locations from the mobile and Room database to decide
        val result1 = FloatArray(3)
        android.location.Location.distanceBetween(
            lat,
            lng,
            locationEntry.lat,
            locationEntry.lng,
            result1
        )
        return result1[0].toDouble()
    }

    private fun presistFetchedVenueList(fetchedListResponse: VenueResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            venueListDAO.upsert(fetchedListResponse.venueGroupsContainer.groups[0].entries)
        }
    }
}