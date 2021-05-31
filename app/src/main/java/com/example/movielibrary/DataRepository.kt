package com.example.movielibrary

object DataRepository {
    val movieItems: Array<MovieItem> by lazy {
        arrayOf(
            MovieItem(
                title = App.context?.getString(R.string.firstMovieTitle) ?: "",
                description = App.context?.getString(R.string.firstMovieDescription) ?: "",
                imgSource = R.drawable.first
            ),
            MovieItem(
                title = App.context?.getString(R.string.secondMovieTitle) ?: "",
                description = App.context?.getString(R.string.secondMovieDescription) ?: "",
                imgSource = R.drawable.second
            ),
            MovieItem(
                title = App.context?.getString(R.string.thirdMovieTitle) ?: "",
                description = App.context?.getString(R.string.thirdMovieDescription) ?: "",
                imgSource = R.drawable.third
            )
        )
    }
}
