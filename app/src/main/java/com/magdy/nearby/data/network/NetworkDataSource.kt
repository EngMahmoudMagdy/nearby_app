package com.magdy.nearby.data.network

import androidx.lifecycle.LiveData
import com.magdy.nearby.data.network.response.PhotoResponse
import com.magdy.nearby.data.network.response.VenueResponse

interface NetworkDataSource {
    val downloadedVenueList : LiveData<VenueResponse>
    val downloadedVenuePhotos : LiveData<PhotoResponse>
    suspend fun fetchVenueList(
        latlng : String
    )
    suspend fun fetchVenuePhotos (
        id : String
    )
}