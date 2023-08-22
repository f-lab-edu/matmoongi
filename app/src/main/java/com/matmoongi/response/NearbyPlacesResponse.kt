package com.matmoongi.response

import com.google.gson.annotations.SerializedName
import com.matmoongi.data.dataclass.Coordinate

data class NearbyPlacesResponse(
    val results: List<Place>,
    @SerializedName("next_page_token") val nextPageToken: String? = null,
)

data class Place(
    @SerializedName("place_id") val placeId: String,
    val name: String,
    val photos: List<PlacePhoto>? = null,
    val rating: String,
    val geometry: Geometry,
    @SerializedName("user_ratings_total") val userRatingsTotal: String,
)

data class PlacePhoto(
    @SerializedName("photo_reference") val photoReference: String,
    val width: String,
    val height: String,
)

data class Geometry(
    val location: Coordinate,
)
