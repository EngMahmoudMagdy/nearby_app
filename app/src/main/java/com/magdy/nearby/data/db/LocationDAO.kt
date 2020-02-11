package com.magdy.nearby.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magdy.nearby.data.db.venues.CURRENT_LOCATION_ID
import com.magdy.nearby.data.db.venues.LocationEntry

@Dao
interface LocationDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun upsert(itemVenueEntry: LocationEntry)

  @Query("select * from current_location where id = $CURRENT_LOCATION_ID")
  fun getRecentAddedLocation(): LiveData<LocationEntry>
}
