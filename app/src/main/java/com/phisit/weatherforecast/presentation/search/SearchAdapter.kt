package com.phisit.weatherforecast.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.phisit.weatherforecast.databinding.LayoutListViewHolderBinding
import com.phisit.weatherforecast.domain.model.GeocodingModel

class SearchAdapter(
    private val listener: (geocoding: GeocodingModel) -> Unit
) : ListAdapter<GeocodingModel, SearchAdapter.ListViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutListViewHolderBinding.inflate(layoutInflater, parent, false)
        return ListViewHolder(binding.root, listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ListViewHolder(
        view: View,
        private val listener: (geocoding: GeocodingModel) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val binding: LayoutListViewHolderBinding by lazy {
            LayoutListViewHolderBinding.bind(view)
        }

        fun bind(geocoding: GeocodingModel) {
            binding.dateTextView.text = String.format(
                "%s, %s, %s",
                geocoding.name,
                geocoding.country,
                geocoding.state
            )

            binding.root.setOnClickListener {
                listener.invoke(geocoding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GeocodingModel>() {

        override fun areItemsTheSame(
            oldItem: GeocodingModel,
            newItem: GeocodingModel
        ) = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: GeocodingModel,
            newItem: GeocodingModel
        ) = oldItem == newItem
    }
}