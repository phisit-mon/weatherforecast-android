package com.phisit.weatherforecast.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phisit.weatherforecast.common.core.viewInflateBinding
import com.phisit.weatherforecast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewInflateBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}