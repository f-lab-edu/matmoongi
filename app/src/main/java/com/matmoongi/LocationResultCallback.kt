package com.matmoongi

import com.matmoongi.data.Coordinate

interface LocationResultCallback {
    fun onLocationResult(coordinate: Coordinate)
    fun onError(error: Exception)
}
