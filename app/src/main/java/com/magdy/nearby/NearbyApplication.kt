package com.magdy.nearby

import android.app.Application
import com.magdy.nearby.data.db.NearByDatabase
import com.magdy.nearby.data.network.ApiService
import com.magdy.nearby.data.network.NetworkDataSource
import com.magdy.nearby.data.network.NetworkDataSourceImpl
import com.magdy.nearby.data.provider.LocationProvider
import com.magdy.nearby.data.provider.LocationProviderImpl
import com.magdy.nearby.data.repository.VenueRepository
import com.magdy.nearby.data.repository.VenueRepositoryImpl
import com.magdy.nearby.ui.nearby.NearByActivityViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NearbyApplication() : Application(), KodeinAware {

  //Dependencies injection are here in our application class implementation

  override val kodein: Kodein = Kodein.lazy {
    import(androidXModule(this@NearbyApplication))
    bind() from singleton { NearByDatabase(instance()) }
    bind() from singleton { instance<NearByDatabase>().venueListDao() }
    bind() from singleton { instance<NearByDatabase>().locationDao() }
    bind() from singleton { ApiService() }

    bind<LocationProvider>() with singleton { LocationProviderImpl(instance()) }
    bind<NetworkDataSource>() with singleton { NetworkDataSourceImpl(instance()) }
    bind<VenueRepository>() with singleton {
      VenueRepositoryImpl(
        instance(),
        instance(),
        instance(),
        instance()
      )
    }
    bind() from provider { NearByActivityViewModelFactory(instance()) }

  }
}
