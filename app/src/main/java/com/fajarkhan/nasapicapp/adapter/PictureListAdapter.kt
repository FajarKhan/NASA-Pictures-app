package com.fajarkhan.nasapicapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fajarkhan.nasapicapp.R
import com.fajarkhan.nasapicapp.model.PictureResponseModel
import kotlinx.android.synthetic.main.item_picture.view.*

class PictureListAdapter :
    ListAdapter<PictureResponseModel, PictureListAdapter.PictureViewHolder>(PictureResponseDC()) {

    //Setup the Item ClickListener to pass the click position
    var itemClickListener: (position: Int) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_picture, parent, false)
        )
    }


    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun swapData(data: List<PictureResponseModel>?) {
        submitList(data?.toMutableList())
    }

    inner class PictureViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return
            itemClickListener.invoke(adapterPosition)
        }

        fun bind(item: PictureResponseModel) = with(itemView) {
            tvTitle.text = item.title
            Glide.with(this).load(item.imageUrl)
                .placeholder(CircularProgressDrawable(itemView.context).apply {
                    strokeWidth = 10f
                    centerRadius = 50f
                    start()
                }).into(photoThumbnail)
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
