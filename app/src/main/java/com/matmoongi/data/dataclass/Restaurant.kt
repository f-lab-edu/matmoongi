package com.matmoongi.data.dataclass

data class Restaurant(
    val placeId: String,
    val name: String,
    val thumbnailPhoto: String,
    val photos: List<String>,
    val rating: String,
    val ratingCount: String,
    val address: String,
    val location: String,
    val isLike: Boolean,
    val reviews: List<Review>,
)
