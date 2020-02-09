package com.magdy.nearby.data.network.response

import com.google.gson.annotations.SerializedName
import com.magdy.nearby.data.db.venues.VenueGroupsContainer

data class VenueResponse(
    @SerializedName("response")
    val venueGroupsContainer: VenueGroupsContainer
)