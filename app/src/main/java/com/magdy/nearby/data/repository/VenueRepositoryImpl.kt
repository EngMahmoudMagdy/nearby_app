package com.magdy.nearby.data.repository

import androidx.lifecycle.LiveData
import com.magdy.nearby.data.db.LocationDAO
import com.magdy.nearby.data.db.VenueListDAO
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import com.magdy.nearby.data.db.venues.LocationEntry
import com.magdy.nearby.data.network.NetworkDataSource
import com.magdy.nearby.data.network.response.PhotoResponse
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
    override fun getVenuePhotos(venueId:String): LiveData<PhotoResponse?> {
        networkDataSource.fetchVenuePhotos(venueId)
        return networkDataSource.downloadedVenuePhotos
    }

    override fun getVenueListThrowable(): LiveData<Throwable> {
        return networkDataSource.venueListThrowable
    }
    init {
        networkDataSource.downloadedVenueList.observeForever {
            presistFetchedVenueList(it)
        }
    }

    override fun getVenueList(): LiveData<MutableList<ItemVenueEntry>?> {
        initVenueListData()
        return venueListDAO.getVenueList()
    }

    private fun initVenueListData() {
        if (isDistanceMoreThanLimit() || venueListDAO.getVenueList().value == null) {
            val location = locationProvider.getLocation()
            if (location != null)
                networkDataSource.fetchVenueList("${location.latitude},${location.longitude}")
        }

    }

    private fun isDistanceMoreThanLimit(): Boolean {
        var locationEntry = locationDAO.getRecentAddedLocation().value
        val location = locationProvider.getLocation()
        var lat = 0.0
        var lng = 0.0
        if (location != null) {

            lat = location.latitude
            lng = location.latitude
        }

        if (locationEntry == null) {

            locationEntry = LocationEntry(
                "",
                "",
                "",
                ArrayList(),
                lat,
                lng
            )
            GlobalScope.launch(Dispatchers.IO) {
                locationDAO.upsert(locationEntry)
            }
        }
        //Decide if the location is in the range limit which is here 500 or not
        return (locationEntry != null
                && location != null
                && calcDistance(
            locationEntry,
            location.latitude,
            location.longitude
        ) >= LOCATION_LIMIT)
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

    private fun presistFetchedVenueList(fetchedListResponse: VenueResponse?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (fetchedListResponse != null && fetchedListResponse.venueGroupsContainer.groups.isNotEmpty()) {
                val list = fetchedListResponse.venueGroupsContainer.groups[0].entries
                venueListDAO.clearVenueList()
                venueListDAO.upsert(list)
            }
        }
    }
}