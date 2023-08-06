package com.matmoongi.response

import com.matmoongi.data.Coordinate

data class NearbyPlacesResponse(
    val results: List<Place>,
    val next_page_token: String? = null,
)

data class Place(
    val place_id: String,
    val name: String,
    val photos: List<PlacePhoto>? = null,
    val rating: String,
    val geometry: Geometry,
    val user_ratings_total: String,
)

data class PlacePhoto(
    val photo_reference: String,
    val width: String,
    val height: String,
)

data class Geometry(
    val location: Coordinate,
)
