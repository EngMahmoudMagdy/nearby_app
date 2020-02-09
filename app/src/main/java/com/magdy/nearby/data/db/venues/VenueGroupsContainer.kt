package com.magdy.nearby.data.db.venues


data class VenueGroupsContainer(
    val groups: List<Group>,
    val headerFullLocation: String,
    val headerLocation: String,
    val headerLocationGranularity: String,
    val suggestedRadius: Int,
    val totalResults: Int
)