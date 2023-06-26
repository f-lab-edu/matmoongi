package com.matmoongi.data

data class MainRestaurant(
    val place_id: String,
    val name: String,
    val thumbnail_photo: String,
    val rating: String,
    val rating_count: String,
    val distance: String,
    val isLike: Boolean,
    val review: Review,
)
