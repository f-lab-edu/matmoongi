package com.matmoongi.data

data class FavoriteRestaurant(
    val placeId: String,
    val name: String,
    val thumbnailPhoto: String,
    val rating: String,
    val ratingCount: String,
    val address: String,
    val isLike: Boolean,
)
