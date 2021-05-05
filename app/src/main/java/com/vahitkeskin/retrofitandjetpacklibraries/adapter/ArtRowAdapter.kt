package com.vahitkeskin.retrofitandjetpacklibraries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.vahitkeskin.retrofitandjetpacklibraries.R
import com.vahitkeskin.retrofitandjetpacklibraries.roomdb.ArtRoom
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class ArtRowAdapter @Inject constructor(
    private val glideImage: RequestManager
) : RecyclerView.Adapter<ArtRowAdapter.ArtRowViewHolder>() {

    class ArtRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffUtil = object : DiffUtil.ItemCallback<ArtRoom>() {
        override fun areItemsTheSame(oldItem: ArtRoom, newItem: ArtRoom): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtRoom, newItem: ArtRoom): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var artsDB: List<ArtRoom>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row, parent, false)
        return ArtRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtRowViewHolder, position: Int) {

        val artsList = artsDB[position]

        val imageView = holder.itemView.findViewById<CircleImageView>(R.id.artRowCim)
        val imageName = holder.itemView.findViewById<TextView>(R.id.artRowImageName)
        val savedUserName = holder.itemView.findViewById<TextView>(R.id.artRowSavedUserName)
        val savedHistoryTime = holder.itemView.findViewById<TextView>(R.id.artRowSavedHistoryTime)
        val savedHourTime = holder.itemView.findViewById<TextView>(R.id.artRowSavedHourTime)

        holder.itemView.apply {
            imageName.text = artsList.imageName
            savedUserName.text = artsList.savedUserName

            savedHistoryTime.text = artsList.savedHistoryTimeName
            savedHourTime.text = artsList.savedHourTimeName

            glideImage.load(artsList.imageUrl).into(imageView)

        }
    }

    override fun getItemCount(): Int {
        return artsDB.size
    }
}