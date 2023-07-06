package com.matmoongi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.matmoongi.viewmodels.MapViewModel

class MapActivity : AppCompatActivity() {
    val mapViewModel: MapViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
