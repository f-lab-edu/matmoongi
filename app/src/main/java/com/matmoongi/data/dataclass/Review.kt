package com.matmoongi.data.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val author: String,
    val profilePhoto: String,
    val text: String,
    val rating: Double,
    val timeSinceWriting: String,
) : Parcelable
