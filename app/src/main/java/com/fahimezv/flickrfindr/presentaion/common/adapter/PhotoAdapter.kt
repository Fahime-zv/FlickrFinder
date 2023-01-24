package com.fahimezv.flickrfindr.presentaion.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fahimezv.flickrfindr.core.model.Photo
import com.fahimezv.flickrfindr.core.model.thumbnail
import com.fahimezv.flickrfindr.databinding.PhotoItemBinding
import com.fahimezv.flickrfindr.presentaion.common.BounceClickEffectAnimator
import com.fahimezv.flickrfindr.presentaion.common.ImageLoader

class PhotoAdapter(
    private val size: CellDimension.Size,
    private val onPhotoClickListener: (photo: Photo) -> Unit,
    ) : PagingDataAdapter<Photo, PhotoAdapter.ViewHolder>(SearchComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val itemBinding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(private val itemBinding: PhotoItemBinding) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        init {
            //Setting
            itemView.apply {
                layoutParams = ViewGroup.LayoutParams(size.width, size.height)
                setOnClickListener(this@ViewHolder)
            }

            // For click animation
            BounceClickEffectAnimator(itemView)
        }

        override fun onClick(view: View?) {
            if(bindingAdapterPosition==-1) return
            getItem(bindingAdapterPosition)?.let { photo->
                onPhotoClickListener.invoke(photo)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            getItem(position)?.let { photo ->
                itemBinding.nameTextView.text = photo.title
                ImageLoader.with(itemBinding.root.context).load(photo.thumbnail).into(itemBinding.thumbnialImageView)
            }
        }
    }


    object SearchComparator : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

}