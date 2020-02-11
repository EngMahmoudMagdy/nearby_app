package com.magdy.nearby.ui.nearby

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.magdy.nearby.data.repository.VenueRepository

class NearByActivityViewModel(
    private val venueRepository: VenueRepository
) : ViewModel() {
    val permissionRequest = MutableLiveData<String>()
    fun onPermissionResult(granted: Boolean) {
        if (granted)
            venueList = venueRepository.getVenueList()
    }

    var venueList = venueRepository.getVenueList()
}