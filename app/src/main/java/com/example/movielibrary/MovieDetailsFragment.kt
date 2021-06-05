package com.example.movielibrary

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.appbar.CollapsingToolbarLayout


class MovieDetailsFragment : Fragment(), MainActivity.NavigationListener {

    private lateinit var imageView: ImageView

    private lateinit var titleView: TextView

    private lateinit var toolbar: CollapsingToolbarLayout

    private lateinit var descriptionView: TextView

    private lateinit var likeCheckBox: CheckBox

    private lateinit var commentField: EditText


    companion object {
        const val TAG = "MovieDetailsFragment"
        const val MOVIE_ITEM = "MOVIE_ITEM"
        const val COMMENT_RESULT = "COMMENT_RESULT"


        fun newInstance(item: MovieItem): MovieDetailsFragment {
            val args = Bundle()
            args.putParcelable(MOVIE_ITEM, item)

            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.movieImg)
        titleView = view.findViewById(R.id.movieTitle)
        toolbar = view.findViewById(R.id.collapsingToolbar)
        descriptionView = view.findViewById(R.id.movieDescription)
        likeCheckBox = view.findViewById(R.id.likeCheckBox)
        commentField = view.findViewById(R.id.commentField)

        setViews()
    }

    private fun setViews() {
        arguments?.getParcelable<MovieItem>(MOVIE_ITEM)?.let {
            imageView.setImageResource(it.imgSource)
            titleView.text = it.title
            descriptionView.text = it.description

            setToolbar(it.title)
        }
    }

    private fun setToolbar(title: String) {
        toolbar.title = title
        toolbar.setCollapsedTitleTextColor(Color.WHITE)
        toolbar.setExpandedTitleColor(Color.TRANSPARENT)
    }

    override fun onBackPressed() {
        val args = Bundle()
        args.putParcelable(COMMENT_RESULT, FeedBackModel(likeCheckBox.isChecked, commentField.text.toString()))
        setFragmentResult(COMMENT_RESULT, args)
    }
}