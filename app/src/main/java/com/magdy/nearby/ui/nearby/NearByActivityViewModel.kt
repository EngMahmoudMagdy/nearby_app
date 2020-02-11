package com.magdy.nearby.ui.nearby

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.magdy.nearby.data.network.response.PhotoResponse
import com.magdy.nearby.data.repository.VenueRepository

class NearByActivityViewModel(
    private val venueRepository: VenueRepository
) : ViewModel() {
    var venueList = venueRepository.getVenueList()
    fun refreshVenueList() {
        venueList = venueRepository.getVenueList()
    }

    public fun getVenueListThrowable(): LiveData<Throwable>  {
        return venueRepository.getVenueListThrowable()
    }

    public fun getPhotosById(venueId: String): LiveData<PhotoResponse?> {
        return venueRepository.getVenuePhotos(venueId)
    }
}