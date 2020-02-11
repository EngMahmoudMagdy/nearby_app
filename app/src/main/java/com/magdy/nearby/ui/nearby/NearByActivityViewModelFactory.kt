package com.magdy.nearby.ui.nearby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.magdy.nearby.data.repository.VenueRepository

class NearByActivityViewModelFactory (
    private val venueRepository: VenueRepository
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NearByActivityViewModel(venueRepository) as T
    }

}