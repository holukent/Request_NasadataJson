package com.chinlung.testimageview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.testimageview.databinding.ListReaultBinding
import com.chinlung.testimageview.fragment.PageTwoDirections
import com.chinlung.testimageview.model.NasaDataItem

class MyListAdapter() :
    ListAdapter<NasaDataItem, MyListAdapter.ItemViewHolder>(NasaItemDiffCallback) {

    class ItemViewHolder(private val binding: ListReaultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataitem: NasaDataItem) {
            binding.nasaDataItem = dataitem

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_reault,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(
                PageTwoDirections.actionPageTwoToPageThree(
                    nasasataitem = getItem(position)!!
                )
            )
        }
    }


    object NasaItemDiffCallback : DiffUtil.ItemCallback<NasaDataItem>() {
        override fun areItemsTheSame(oldItem: NasaDataItem, newItem: NasaDataItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: NasaDataItem, newItem: NasaDataItem): Boolean {
            return oldItem == newItem
        }
    }
}