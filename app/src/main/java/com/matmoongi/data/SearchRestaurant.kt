package com.matmoongi.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchRestaurant(
    val placeId: String,
    val name: String,
    val thumbnailPhoto: String,
    val rating: String,
    val ratingCount: String,
    val distance: String,
    val isLike: Boolean,
    val review: Review,
) : Parcelable
