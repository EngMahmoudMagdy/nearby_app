package com.magdy.nearby.ui.nearby

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.magdy.nearby.R
import com.magdy.nearby.adapters.VenueAdapter
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList


class NearByActivity : AppCompatActivity(), KodeinAware {
    private val DELAY: Long = 10000
    private val REQUEST_ACCESS_FINE_LOCATION_CODE = 888
    override val kodein by closestKodein()
    private val handler by lazy {
        Handler()
    }

    private var venueList: ArrayList<ItemVenueEntry> = ArrayList()
    private var adapter: VenueAdapter? = null
    private val viewModel: NearByActivityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NearByActivityViewModel::class.java)
    }

    private val viewModelFactory: NearByActivityViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refreshAllList()
        setupSwipeLayout()
        setupStateToggleButton()
        setupHandler()
        viewModel.venueList.observe(this, Observer {
            hideProgress()
            hideError()
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
        viewModel.getVenueListThrowable().observe(this, Observer {
            hideProgress()
            hideError()
            if (venueList.isEmpty())
                showNetworkError()
        })
    }

    //Check for the permission of location if granted or not
    //if the location permission is not granted, it requests from the user to allow it
    //if user closing GPS it will redirect it to the settings to enable GPS again
    private fun refreshAllList() {
        if (ContextCompat.checkSelfPermission(
                Objects.requireNonNull(this.applicationContext),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@NearByActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_ACCESS_FINE_LOCATION_CODE
            )
        }
        showProgress()
        hideError()
        viewModel.refreshVenueList()
    }

    private fun setupStateToggleButton() {
        updateTypeToggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                removeHandler()
            } else setupHandler()
        }
    }

    private fun setupHandler() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                refreshAllList()
                handler.postDelayed(this, DELAY)
            }
        }, DELAY)
    }

    private fun removeHandler() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null)
    }

    private fun setupSwipeLayout() {
        swipeLayout.setOnRefreshListener {
            refreshAllList()
        }
    }

    //Shows the progress bar during fetching new data
    private fun showProgress() {
        progressLinearLayout.visibility = VISIBLE
    }

    //hiding the progress bar after fetching new data
    private fun hideProgress() {
        swipeLayout.isRefreshing = false
        progressLinearLayout.visibility = GONE
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


    private fun hideError() {
        errorLayout.visibility = GONE
    }

    //Setting up the recycler view with the adapter after its initialization and setting it for the recycler
    private fun setupRecyclerView() {
        if (adapter == null) {
            adapter = VenueAdapter(this, viewModel, venueList)
            recyclerView.adapter = adapter
        } else {
            adapter!!.notifyDataSetChanged()
        }
    }

    //Getting the permission to refresh and get the data of nearby places or venues
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION_CODE && (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            showProgress()
            hideError()
            viewModel.refreshVenueList()
        }
    }
}
