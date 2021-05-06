package com.example.movielibrary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FavoritesAdapter(private val items: List<MovieItem>, private val removeAction: ((item: MovieItem, position: Int) -> Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_favorite, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoritesViewHolder) {
            holder.bind(items[position], position, removeAction)
        }
    }

    override fun getItemCount(): Int = items.size
}