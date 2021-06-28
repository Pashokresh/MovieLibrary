package com.example.movielibrary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(
    private val items: List<MovieItem>,
    private val detailAction: ((item: MovieItem, position: Int) -> Unit),
    private val addFavoriteAction: ((item: MovieItem, position: Int) -> Unit)
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoviesViewHolder) {
            val item = items[position]
            holder.bind(item, position, detailAction, addFavoriteAction)
        }
    }

    override fun getItemCount(): Int = items.size
}