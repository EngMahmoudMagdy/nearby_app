package com.magdy.nearby.ui.nearby

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.magdy.nearby.R
import com.magdy.nearby.adapters.PlacesAdapter
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList


class NearByActivity : AppCompatActivity(), KodeinAware {
    private val REQUEST_ACCESS_FINE_LOCATION_CODE = 888
    override val kodein by closestKodein()

    var venueList: ArrayList<ItemVenueEntry> = ArrayList()
    var adapter: PlacesAdapter? = null
    private val viewModel: NearByActivityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NearByActivityViewModel::class.java)
    }

    private val viewModelFactory: NearByActivityViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                Objects.requireNonNull(this.applicationContext),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.venueList.observe(this, Observer {
                if (it != null) {
                    venueList.clear()
                    venueList.addAll(it)
                    setupRecyclerView()
                    if (venueList.isEmpty())
                        showDataError()
                } else {
                    showNetworkError()
                }
            })
        } else {
            ActivityCompat.requestPermissions(
                this@NearByActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_ACCESS_FINE_LOCATION_CODE
            )
        }
        viewModel.venueList.observe(this, Observer {
            if (it != null) {
                venueList.clear()
                venueList.addAll(it)
                setupRecyclerView()
                if (venueList.isEmpty())
                    showDataError()
            } else {
                showNetworkError()
            }
        })
    }

    private fun showNetworkError() {
        errorLayout.visibility = VISIBLE
        errorImage.setImageResource(R.drawable.ic_cloud_off_black_24dp)
        errorTextView.text = getString(R.string.network_error)

    }

    private fun showDataError() {
        errorLayout.visibility = VISIBLE
        errorImage.setImageResource(R.drawable.ic_error_outline_black_24dp)
        errorTextView.text = getString(R.string.no_data_found)

    }

    private fun setupRecyclerView() {
        if (adapter == null) {
            adapter = PlacesAdapter(this, venueList)
            recyclerView.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION_CODE)
            viewModel.onPermissionResult((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
    }
}
