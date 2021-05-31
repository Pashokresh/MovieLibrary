package com.example.movielibrary

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val img: ImageView = itemView.findViewById(R.id.favoriteImg)
    private val title: TextView = itemView.findViewById(R.id.favoriteTitle)
    private val button = itemView.findViewById<View>(R.id.removeBtn)

    fun bind(item: MovieItem, position: Int, removeAction: ((item: MovieItem, position: Int) -> Unit)) {
        img.setImageResource(item.imgSource)

        title.text = item.title

        button.setOnClickListener {
            removeAction(item, position)
        }
    }
}