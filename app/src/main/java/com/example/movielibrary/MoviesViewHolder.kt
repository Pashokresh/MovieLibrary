package com.example.movielibrary

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image = itemView.findViewById<ImageView>(R.id.itemImage)
    private val titleView = itemView.findViewById<TextView>(R.id.itemTitle)
    private val detailButton = itemView.findViewById<View>(R.id.detailBtn)
    private val favoriteBtn = itemView.findViewById<ImageButton>(R.id.favoriteBtn)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(
        item: MovieItem,
        position: Int,
        detailAction: ((item: MovieItem, position: Int) -> Unit),
        addFavoriteAction: ((item: MovieItem, position: Int) -> Unit)
    ) {
        titleView.text = item.title

        titleView.setTextColor(
            itemView.resources.getColor(if (item.wasVisited) R.color.selectedTextColor else R.color.textColor, null)
        )

        image.setImageResource(item.imgSource)
        image.setOnLongClickListener {
            addFavoriteAction(item, position)
            true
        }

        detailButton.setOnClickListener {
            detailAction(item, position)
        }

        favoriteBtn.setImageDrawable(
            itemView.resources.getDrawable(
                if (item.isFavorite) android.R.drawable.btn_star_big_on else android.R.drawable.btn_star_big_off,
                null
            )
        )
        favoriteBtn.setOnClickListener {
            addFavoriteAction(item, position)
        }

    }
}