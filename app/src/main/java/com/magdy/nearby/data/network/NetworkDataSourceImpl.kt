package com.magdy.nearby.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.magdy.nearby.data.network.response.PhotoResponse
import com.magdy.nearby.data.network.response.VenueResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkDataSourceImpl(
    private val apiService: ApiService
) : NetworkDataSource {

    private val _downloadedVenueList = MutableLiveData<VenueResponse>()
    override val downloadedVenueList: LiveData<VenueResponse>
        get() = _downloadedVenueList
    private val _downloadedVenuePhotos = MutableLiveData<PhotoResponse>()
    override val downloadedVenuePhotos: LiveData<PhotoResponse>
        get() = _downloadedVenuePhotos

    override fun fetchVenueList(latlng: String) {
        apiService.getVenueList(latlng).enqueue(object : Callback<VenueResponse> {
            override fun onFailure(call: Call<VenueResponse>, t: Throwable) {
                _downloadedVenueList.postValue(null)
            }

            override fun onResponse(
                call: Call<VenueResponse>,
                response: Response<VenueResponse>
            ) {
                _downloadedVenueList.postValue(response.body())
            }
        })
    }

    override fun fetchVenuePhotos(id: String) {
        apiService.getVenuePhotos(id).enqueue(object : Callback<PhotoResponse> {
            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                _downloadedVenueList.postValue(null)
            }

            override fun onResponse(
                call: Call<PhotoResponse>,
                response: Response<PhotoResponse>
            ) {
                _downloadedVenuePhotos.postValue(response.body())
            }
        })
    }
}