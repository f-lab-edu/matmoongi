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
    val reviews: List<Review>,
//    api 호출시 parameter로 넘기면 같은 위치의 다음 리스트 호출 가능
    val pageToken: String?,
)
