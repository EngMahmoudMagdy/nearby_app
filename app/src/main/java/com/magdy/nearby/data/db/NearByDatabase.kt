package com.magdy.nearby.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.magdy.nearby.data.Converters
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import com.magdy.nearby.data.db.venues.LocationEntry

@Database(
  entities = [ItemVenueEntry::class, LocationEntry::class],
  version = 1
)
@TypeConverters(Converters::class)
abstract class NearByDatabase : RoomDatabase() {
  abstract fun venueListDao(): VenueListDAO
  abstract fun locationDao(): LocationDAO

  companion object {
    @Volatile
    private var instance: NearByDatabase? = null
    private val LOCK = Any()
    operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
      instance ?: buildDatabse(context).also { instance = it }
    }

    private fun buildDatabse(context: Context) =
      Room.databaseBuilder(
        context.applicationContext,
        NearByDatabase::class.java,
        "nearby.db"
      )
        .build()
  }

}
