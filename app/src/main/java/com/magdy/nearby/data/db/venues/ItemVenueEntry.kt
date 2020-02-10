package com.magdy.nearby.data.db.venues

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venues")
data class ItemVenueEntry(
    @Embedded
    val venue: VenueEntry
)
{
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id__")
    var id = venue.id
}