package com.matmoongi.data

data class FavoriteRestaurant(
    val place_id: String,
    val name: String,
    val thumbnail_photo: String,
    val rating: String,
    val rating_count: String,
    val address: String,
    val isLike: Boolean,
)
