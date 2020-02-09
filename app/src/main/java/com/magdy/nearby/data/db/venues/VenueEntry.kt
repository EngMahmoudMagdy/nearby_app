package com.magdy.nearby.data.db.venues


data class VenueEntry(
    val categories: List<Category>,
    val id: String,
    val location: Location,
    val name: String
)