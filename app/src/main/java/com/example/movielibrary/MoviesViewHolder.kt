package com.example.movielibrary

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image = itemView.findViewById<ImageView>(R.id.itemImage)
    private val titleView = itemView.findViewById<TextView>(R.id.itemTitle)
    private val detailButton = itemView.findViewById<View>(R.id.detailBtn)

    fun bind(item: MovieItem, position: Int, detailAction: ((item: MovieItem, position: Int) -> Unit), addFavoriteAction: ((item: MovieItem) -> Unit)) {
        titleView.text = item.title

        titleView.setTextColor(
            itemView.resources.getColor(if (item.wasVisited) R.color.selectedTextColor else R.color.textColor, null)
        )

        image.setImageResource(item.imgSource)
        image.setOnLongClickListener {
            addFavoriteAction(item)
            true
        }

        detailButton.setOnClickListener {
            detailAction(item, position)
        }
    }
}