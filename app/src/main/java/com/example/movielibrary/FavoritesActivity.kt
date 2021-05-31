package com.example.movielibrary

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    private var favoriteMovieItems: MutableList<MovieItem>? = null

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.favoritesRecycler)
    }

    private val noMoviesMessage: TextView by lazy {
        findViewById(R.id.noMoviesMessage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        favoriteMovieItems = DataRepository.movieItems.filter { item -> item.isFavorite}.toMutableList()

        setViews()
    }

    private fun setViews() {
        favoriteMovieItems?.let { favItems ->
            noMoviesMessage.visibility = View.GONE

            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = FavoritesAdapter(favItems) { item, _ ->
                val currentPosition = favItems.indexOf(item)
                item.isFavorite = false
                favItems.removeAt(currentPosition)

                recyclerView.adapter?.notifyItemRemoved(currentPosition)
            }
            val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            recyclerView.addItemDecoration(itemDecoration)
        } ?: run {
            recyclerView.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}