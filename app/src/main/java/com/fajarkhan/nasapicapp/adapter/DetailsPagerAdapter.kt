package com.fajarkhan.nasapicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fajarkhan.nasapicapp.R
import com.fajarkhan.nasapicapp.model.PictureResponseModel
import kotlinx.android.synthetic.main.picture_details.view.*

class DetailsPagerAdapter :
    ListAdapter<PictureResponseModel, DetailsPagerAdapter.PictureViewHolder>(PictureResponseDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PictureViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.picture_details, parent, false)
    )

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun swapData(data: List<PictureResponseModel>?) {
        submitList(data?.toMutableList())
    }

    inner class PictureViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PictureResponseModel) = with(itemView) {
            tvTitle.text = item.title
            tvDate.text = item.date
            //Handle null check to make the view gone
            item.copyright?.let {
                tvCopyright.text = it
            } ?: kotlin.run { tvCopyright.visibility = View.GONE }
            explanationText.text = item.explanation
            Glide.with(this).load(item.imageUrl)
                .placeholder(CircularProgressDrawable(itemView.context).apply {
                    strokeWidth = 10f
                    centerRadius = 80f
                    start()
                })
                .into(ivItemImage)
        }
    }

    private class PictureResponseDC : DiffUtil.ItemCallback<PictureResponseModel>() {
        override fun areItemsTheSame(
            oldItem: PictureResponseModel,
            newItem: PictureResponseModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: PictureResponseModel,
            newItem: PictureResponseModel
        ): Boolean {
            return oldItem == newItem
        }
    }

}
