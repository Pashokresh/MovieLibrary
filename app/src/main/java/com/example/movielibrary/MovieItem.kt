package com.example.movielibrary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieItem(val title: String, val description: String, val imgSource: Int, var wasVisited: Boolean = false): Parcelable
