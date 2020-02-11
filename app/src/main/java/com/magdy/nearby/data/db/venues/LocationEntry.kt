package com.magdy.nearby.data.db.venues

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.magdy.nearby.data.Converters

const val CURRENT_LOCATION_ID = 0

@Entity(tableName = "current_location")
@TypeConverters(Converters::class)
data class LocationEntry(
    val address: String?,
    val city: String?,
    val country: String?,
    @TypeConverters(Converters::class)
    val formattedAddress: List<String>,
    val lat: Double,
    val lng: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_LOCATION_ID
}