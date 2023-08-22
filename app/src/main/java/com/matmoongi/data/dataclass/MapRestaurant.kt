package com.matmoongi.data.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapRestaurant(
    @SerializedName("place_id") val placeId: String,
    val name: String,
    val photos: List<String>? = null,
    val rating: String,
    val ratingCount: String,
    val coordinate: Coordinate,
    val isLike: Boolean = false,
) : Parcelable
