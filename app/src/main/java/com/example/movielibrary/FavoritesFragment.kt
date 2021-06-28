package com.example.movielibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : Fragment() {

    private var favoriteMovieItems: MutableList<MovieItem>? = null

    private lateinit var recyclerView: RecyclerView

    private lateinit var noMoviesMessage: TextView

    companion object {
        const val TAG = "FavoritesFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteMovieItems = DataRepository.movieItems.filter { item -> item.isFavorite}.toMutableList()
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        activity?.actionBar?.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.favoritesRecycler)
        noMoviesMessage = view.findViewById(R.id.noMoviesMessage)

        setViews()
    }

    private fun setViews() {
        favoriteMovieItems?.let { favItems ->
            if (favItems.isEmpty()) {
                recyclerView.visibility = View.GONE
            } else {
                noMoviesMessage.visibility = View.GONE

                val layoutManager = LinearLayoutManager(requireContext())
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = FavoritesAdapter(favItems) { item, _ ->
                    val currentPosition = favItems.indexOf(item)
                    item.isFavorite = false
                    favItems.removeAt(currentPosition)

                    recyclerView.adapter?.notifyItemRemoved(currentPosition)

                    showSnackbar("${item.title} ${getString(R.string.wasRemovedFrom)} ${getString(R.string.favorites)}") {
                        item.isFavorite = true
                        favItems.add(currentPosition, item)

                        recyclerView.adapter?.notifyItemInserted(currentPosition)
                    }
                }
                val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                recyclerView.addItemDecoration(itemDecoration)
            }
        } ?: run {
            recyclerView.visibility = View.GONE
        }
    }

    private fun showSnackbar(message: String, cancelAction: () -> Unit) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.cancel) { cancelAction() }
                .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                .show()
        }
    }

}