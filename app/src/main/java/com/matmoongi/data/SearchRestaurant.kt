package com.matmoongi.data

data class SearchRestaurant(
    val placeId: String,
    val name: String,
    val thumbnailPhoto: String,
    val rating: String,
    val ratingCount: String,
    val distance: String,
    val isLike: Boolean,
    val review: Review,
)
