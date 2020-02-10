package com.magdy.nearby.data.db.venues

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.magdy.nearby.data.Converters

@TypeConverters(Converters::class)
data class VenueEntry(
    @TypeConverters(Converters::class)
    val categories: List<Category>,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_")
    val id: String,
    @Embedded
    val location: LocationEntry,
    val name: String
)