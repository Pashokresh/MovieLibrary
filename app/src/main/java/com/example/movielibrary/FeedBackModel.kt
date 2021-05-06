package com.example.movielibrary

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeedBackModel(val like: Boolean, val comment: String): Parcelable