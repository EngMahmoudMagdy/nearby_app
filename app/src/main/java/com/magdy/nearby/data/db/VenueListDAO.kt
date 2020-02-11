package com.magdy.nearby.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magdy.nearby.data.db.venues.ItemVenueEntry

@Dao
interface VenueListDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun upsert(itemVenueEntryList: MutableList<ItemVenueEntry>)

  @Query("select * from venues")
  fun getVenueList(): LiveData<MutableList<ItemVenueEntry>>

}
