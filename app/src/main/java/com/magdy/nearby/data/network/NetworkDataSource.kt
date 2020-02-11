package com.magdy.nearby.data.network

import androidx.lifecycle.LiveData
import com.magdy.nearby.data.network.response.PhotoResponse
import com.magdy.nearby.data.network.response.VenueResponse
import java.lang.Exception


/*
* Network data source of the lists and throwables that the ViewModel is listening to their changes
* */

interface NetworkDataSource {
    val downloadedVenueList : LiveData<VenueResponse?>
    val downloadedVenuePhotos : LiveData<PhotoResponse?>
    val venueListThrowable : LiveData<Throwable>

    //To fetsh the venue list from Foresquare API we need implementation for this function
    fun fetchVenueList(
        latlng : String
    )
    //To fetsh the venue's all photos from Foresquare API we need implementation for this function
    fun fetchVenuePhotos (
        id : String
    )
}