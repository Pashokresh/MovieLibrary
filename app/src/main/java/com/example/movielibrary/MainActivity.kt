package com.example.movielibrary

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_MODEL = "MOVIE_MODEL"
        const val FAVORITE_MOVIES = "FAVORITE_MOVIES"
        const val RESULT_DETAIL_CODE = 123
        const val RESULT_FAVORITE_CODE = 321

        private const val SELECTED_MOVIES = "SELECTED_MOVIES"
    }

    private val movieItems: Array<MovieItem> by lazy {
        arrayOf(
            MovieItem(
                title = getString(R.string.firstMovieTitle),
                description = getString(R.string.firstMovieDescription),
                imgSource = R.drawable.first
            ),
            MovieItem(
                title = getString(R.string.secondMovieTitle),
                description = getString(R.string.secondMovieDescription),
                imgSource = R.drawable.second
            ),
            MovieItem(
                title = getString(R.string.thirdMovieTitle),
                description = getString(R.string.thirdMovieDescription),
                imgSource = R.drawable.third
            )
        )
    }

    private var visitedMoviesIndexes: MutableList<Int>? = null

    private var favoriteMovies: MutableSet<MovieItem>? = null

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    private val inviteButton: Button by lazy {
        findViewById(R.id.inviteBtn)
    }

    private val favoritesButton: Button by lazy {
        findViewById(R.id.favoritesBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let { state ->
            state.getIntegerArrayList(SELECTED_MOVIES)?.let { array ->
                visitedMoviesIndexes = array.toMutableList()
            }

            state.getParcelableArrayList<MovieItem>(FAVORITE_MOVIES)?.let { array ->
                favoriteMovies = array.toMutableSet()
            }
        }

        visitedMoviesIndexes?.forEach {
            movieItems[it].wasVisited = true
        }

        initRecycler()
        setButtons()
    }

    private fun initRecycler() {
        val layoutManager =
            if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) GridLayoutManager(this, 3) else LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MovieAdapter(movieItems.toList(), { item: MovieItem, position: Int ->
            item.wasVisited = true

            visitedMoviesIndexes?.add(position) ?: run {
                visitedMoviesIndexes = MutableList(1) { position }
            }

            recyclerView.adapter?.notifyItemChanged(position)

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(MOVIE_MODEL, item)
            startActivityForResult(intent, RESULT_DETAIL_CODE)
        }, { item ->
            favoriteMovies?.add(item) ?: run {
                favoriteMovies = mutableSetOf(item)
            }
            Log.d("MainActivity", "$favoriteMovies")
        })

        val itemDecoration = DividerItemDecoration(
            this,
            if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) DividerItemDecoration.HORIZONTAL else DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(itemDecoration)

    }

    private fun setButtons() {
        inviteButton.setOnClickListener {
            val smsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:"))
            smsIntent.putExtra("sms_body", getString(R.string.invitation))
            startActivity(smsIntent)
        }

        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            favoriteMovies?.let { intent.putParcelableArrayListExtra(FAVORITE_MOVIES, ArrayList(it)) }
            startActivityForResult(intent, RESULT_FAVORITE_CODE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        visitedMoviesIndexes?.let {
            outState.putIntegerArrayList(SELECTED_MOVIES, ArrayList(it))
        }

        favoriteMovies?.let {
            outState.putParcelableArrayList(FAVORITE_MOVIES, ArrayList(it))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_DETAIL_CODE) {
            data?.getParcelableExtra<FeedBackModel>(DetailActivity.FEEDBACK_VAL)?.let {
                Log.d("MainActivity", "like: ${it.like}, comment: ${it.comment}")
            }
        }

        if (requestCode == RESULT_FAVORITE_CODE) {
            data?.getParcelableArrayListExtra<MovieItem>(FAVORITE_MOVIES)?.let {
                favoriteMovies = it.toMutableSet()
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(R.string.exit)
            .setMessage(R.string.exitMessage)
            .setPositiveButton(R.string.yes) { _, _ -> super.onBackPressed() }
            .setNegativeButton(R.string.no) {_, _ -> }
            .create()
            .show()
    }
}
