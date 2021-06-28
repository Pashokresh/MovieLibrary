package com.example.movielibrary

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MoviesListFragment : Fragment() {
    companion object {
        const val TAG = "MoviesListFragment"
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var inviteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(MovieDetailsFragment.COMMENT_RESULT) { key, bundle ->
            bundle.getParcelable<FeedBackModel>(key)?.let {
                Log.d(TAG, "like: ${it.like}, comment: ${it.comment}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        inviteButton = view.findViewById(R.id.inviteBtn)

        initRecycler()
        setButtons()
    }

    private fun initRecycler() {
        val layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) GridLayoutManager(
                requireContext(),
                3
            ) else LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MovieAdapter(DataRepository.movieItems.toList(), { item: MovieItem, position: Int ->
            item.wasVisited = true

            recyclerView.adapter?.notifyItemChanged(position)

            (activity as? ButtonsClickListener)?.onMovieClick(item)
        }) { item, position ->
            item.isFavorite = !item.isFavorite
            recyclerView.adapter?.notifyItemChanged(position)

            showSnackbar("${item.title} ${getString(if (item.isFavorite) R.string.wasAddedTo else R.string.wasRemovedFrom)} ${getString(R.string.favorites)}") {
                item.isFavorite = !item.isFavorite
                recyclerView.adapter?.notifyItemChanged(position)
            }
        }

        val itemDecoration = DividerItemDecoration(
            requireContext(),
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) DividerItemDecoration.HORIZONTAL else DividerItemDecoration.VERTICAL
        )

        recyclerView.addItemDecoration(itemDecoration)
    }

    private fun setButtons() {
        inviteButton.setOnClickListener {
            (activity as? ButtonsClickListener)?.onInviteClick()
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

    interface ButtonsClickListener {
        fun onMovieClick(movieItem: MovieItem)
        fun onFavoritesClick()
        fun onInviteClick()
    }
}