package com.phisit.weatherforecast.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.viewBinding
import com.phisit.weatherforecast.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListenerView()
    }

    private fun setupListenerView() {
        binding.searchImageView.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_search)
        }
        binding.dailyTextView.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_today)
        }
    }
}