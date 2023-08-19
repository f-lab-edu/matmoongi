package com.matmoongi.data.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinate(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lng") val longitude: Double,
) : Parcelable
