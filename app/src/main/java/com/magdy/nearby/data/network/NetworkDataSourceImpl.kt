package com.magdy.nearby.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.magdy.nearby.data.network.response.PhotoResponse
import com.magdy.nearby.data.network.response.VenueResponse
import com.magdy.nearby.internal.NoConnectionException

class NetworkDataSourceImpl(
    private val apiService: ApiService
) : NetworkDataSource {

    private val _downloadedVenueList = MutableLiveData<VenueResponse>()
    override val downloadedVenueList: LiveData<VenueResponse>
        get() = _downloadedVenueList
    private val _downloadedVenuePhotos = MutableLiveData<PhotoResponse>()
    override val downloadedVenuePhotos: LiveData<PhotoResponse>
        get() = _downloadedVenuePhotos

    override suspend fun fetchVenueList(latlng: String) {
        try {

            val fetchedList = apiService.getVenueList(latlng).await()
            _downloadedVenueList.postValue(fetchedList)

        } catch (e: NoConnectionException) {
            Log.e("NearBy", "no internet connection", e)
        }
    }

    override suspend fun fetchVenuePhotos(id: String) {
        try {

            val fetchedList = apiService.getVenuePhotos(id).await()
            _downloadedVenuePhotos.postValue(fetchedList)

        } catch (e: NoConnectionException) {
            Log.e("NearBy", "no internet connection", e)
        }    }
}