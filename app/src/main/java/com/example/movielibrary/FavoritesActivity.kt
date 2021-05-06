package com.example.movielibrary

import android.content.Intent
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

        favoriteMovieItems = intent.getParcelableArrayListExtra(MainActivity.FAVORITE_MOVIES)

        savedInstanceState?.let {
            it.getParcelableArrayList<MovieItem>(MainActivity.FAVORITE_MOVIES)?.let { array ->
                favoriteMovieItems = array.toMutableList()
            }
        }

        setViews()
    }

    private fun setViews() {
        favoriteMovieItems?.let { favItems ->
            noMoviesMessage.visibility = View.GONE

            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = FavoritesAdapter(favItems) { item, position ->
                val currentPosition = favItems.indexOf(item)
                favItems.remove(item)

                recyclerView.adapter?.notifyItemRemoved(currentPosition)
            }
            val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            recyclerView.addItemDecoration(itemDecoration)
        } ?: run {
            recyclerView.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        favoriteMovieItems?.let {
            outState.putParcelableArray(MainActivity.FAVORITE_MOVIES, it.toTypedArray())
        }
    }

    override fun onBackPressed() {
        favoriteMovieItems?.let {
            setResult(MainActivity.RESULT_FAVORITE_CODE,
                Intent().putParcelableArrayListExtra(MainActivity.FAVORITE_MOVIES, ArrayList(it)))
        }
        finish()
        super.onBackPressed()
    }
}