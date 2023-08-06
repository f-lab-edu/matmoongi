package com.matmoongi.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchRestaurant(
    val placeId: String,
    val name: String,
    val thumbnailPhoto: String? = null,
    val rating: String,
    val ratingCount: String,
    val distance: String,
    val isLike: Boolean = false,
    val review: Review? = null,
) : Parcelable
