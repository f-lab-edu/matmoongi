package com.matmoongi.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchRestaurant(
    @SerializedName("place_id") val placeId: String,
    val name: String,
    val thumbnailPhoto: String? = null,
    val rating: String,
    val ratingCount: String,
    val distance: String,
    val isLike: Boolean = false,
    val review: Review? = null,
) : Parcelable
