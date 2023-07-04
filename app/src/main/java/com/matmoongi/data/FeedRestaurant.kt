package com.matmoongi.data

data class FeedRestaurant(
    val placeId: String,
    val name: String,
    val thumbnailPhoto: String,
    val rating: String,
    val ratingCount: String,
    val distance: String,
    val isLike: Boolean,
    val review: Review,
)
