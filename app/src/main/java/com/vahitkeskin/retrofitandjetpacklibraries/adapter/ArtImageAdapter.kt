package com.vahitkeskin.retrofitandjetpacklibraries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
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

        holder.itemView.apply {
            glide.load(imageUrl).into(imageView)
            setOnItemClickListener {
                onItemClickListener?.let {
                    it(imageUrl)
                    println(imageUrl)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}