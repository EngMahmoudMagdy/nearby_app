package com.magdy.nearby.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magdy.nearby.R
import com.magdy.nearby.data.db.venues.ItemVenueEntry
import com.magdy.nearby.ui.nearby.NearByActivityViewModel
import kotlinx.android.synthetic.main.item_place.view.*

class VenueAdapter(
    private val activity: FragmentActivity,
    private val viewModel: NearByActivityViewModel,
    private val list: List<ItemVenueEntry>
) :
    RecyclerView.Adapter<VenueAdapter.Holder>() {

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
        fun bindView() = with(itemView) {
            val i = adapterPosition
            val venue = list[i].venue
            title.text = venue.name

            val sb = StringBuilder()
            if (venue.categories.isNotEmpty()) {
                val cat = venue.categories[0]
                sb.append(cat.name)
            }
            val addressList = venue.location.formattedAddress
            for (i in 0 until addressList.size) {
                val s = addressList[i]
                sb.append(", ").append(s)
            }
            address.text = sb.toString()
            viewModel.getPhotosById(venue.id).observe(activity, Observer { photoResponse ->
                if (photoResponse != null && photoResponse.response.photos.items.isNotEmpty()) {
                    val firstPhoto = photoResponse.response.photos.items[0]
                    Glide.with(activity).load("${firstPhoto.prefix}/original/${firstPhoto.suffix}")
                        .fitCenter()
                        .into(image)
                } else {
                    image.setImageResource(R.drawable.ic_image_black_24dp)
                }
            })
        }
    }
}
