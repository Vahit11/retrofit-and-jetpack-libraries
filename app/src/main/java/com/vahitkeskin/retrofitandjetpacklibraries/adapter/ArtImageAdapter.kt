package com.vahitkeskin.retrofitandjetpacklibraries.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vahitkeskin.retrofitandjetpacklibraries.R
import javax.inject.Inject

class ArtImageAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ArtImageAdapter.ArtImageViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    class ArtImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var images: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row, parent, false)
        return ArtImageViewHolder(view)
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ArtImageViewHolder, position: Int) {
        val imageUrl = images[position]
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imSingleArt)
        val progressBar = holder.itemView.findViewById<ProgressBar>(R.id.pbSingleArt)

        holder.itemView.apply {
            glide.load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
                .into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(imageUrl)
                    println("Click URL: $imageUrl")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}