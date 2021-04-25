package com.example.movielibrary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class MovieModel(val title: String, val description: String, val imgSource: Int): Parcelable
