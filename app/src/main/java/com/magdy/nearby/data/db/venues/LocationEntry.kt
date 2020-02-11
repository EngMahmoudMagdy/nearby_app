package com.magdy.nearby.data.db.venues

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.magdy.nearby.data.Converters

const val CURRENT_LOCATION_ID = 0

@Entity(tableName = "current_location")
@TypeConverters(Converters::class)
data class LocationEntry(
    val address: String,
    val cc: String,
    val city: String,
    val country: String,
    val crossStreet: String,
    val distance: Int,
    @TypeConverters(Converters::class)
    val formattedAddress: List<String>,
    val lat: Double,
    val lng: Double,
    val postalCode: String,
    val state: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_LOCATION_ID
}