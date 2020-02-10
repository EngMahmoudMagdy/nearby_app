package com.magdy.nearby.adapters


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magdy.nearby.R
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import com.magdy.nearby.data.db.venues.VenueEntry
import kotlinx.android.synthetic.main.item_place.view.*

class PlacesAdapter(private val activity: Activity, private val list: List<ItemVenueEntry>) :
    RecyclerView.Adapter<PlacesAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        return Holder(LayoutInflater.from(activity).inflate(R.layout.item_place, viewGroup, false))
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {
        holder.bindView()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView() = with(itemView){
            val i = adapterPosition
            val venue = list[i].venue
            title.text = venue.name
            address.text = venue.location.address
     /*       description!!.movementMethod = LinkMovementMethod.getInstance()
            name!!.setText(getTextHTML(about.title()))
            name!!.movementMethod = LinkMovementMethod.getInstance()
            name!!.visibility = if (about.title().trim().isEmpty()) GONE else VISIBLE*/
        }
    }
}
