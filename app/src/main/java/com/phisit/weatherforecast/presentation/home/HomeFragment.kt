package com.phisit.weatherforecast.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.viewBinding
import com.phisit.weatherforecast.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getGeocoding()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListenerView()
        observeHomeViewModel()
    }

    private fun setupListenerView() {
        binding.searchImageView.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_search)
        }
        binding.dailyTextView.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_today)
        }
    }

    private fun observeHomeViewModel() = with(viewModel) {
        forecastWeatherTrigger.observe(viewLifecycleOwner) {}

        getGeocodingLiveData().observe(viewLifecycleOwner) {
            // TODO update ui
        }
        getWeatherLiveData().observe(viewLifecycleOwner) {
            // TODO update ui
        }
    }
}