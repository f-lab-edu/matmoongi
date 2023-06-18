package com.matmoongi.data

data class Restaurant(
    val place_id: String,
    val name: String,
    val thumbnail_photo: String,
    val photos: List<String>,
    val rating: String,
    val rating_count: String,
    val address: String,
    val location: String,
    val isLike: Boolean,
    val reviews: List<Review>
)
