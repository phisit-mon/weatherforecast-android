package com.phisit.weatherforecast.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.view.viewBinding
import com.phisit.weatherforecast.databinding.FragmentHomeBinding
import com.phisit.weatherforecast.domain.model.CurrentModel
import com.phisit.weatherforecast.domain.model.GeocodingModel
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
        setupSearchResultListener()
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
            setGeocodingToView(it)
        }
        getWeatherLiveData().observe(viewLifecycleOwner) {
            setWeatherToView(it)
        }
    }

    private fun setupSearchResultListener() {
        setFragmentResultListener("search") { _: String, bundle: Bundle ->
            bundle.getString("city")?.let {
                Gson().fromJson(it, GeocodingModel::class.java)
            }?.run {
                viewModel.getGeocoding(name)
            }
        }
    }

    private fun setGeocodingToView(geocodingModel: GeocodingModel) {
        geocodingModel.apply {
            binding.cityNameTextView.text = name
            binding.stateNameTextView.text = country
        }
    }

    private fun setWeatherToView(current: CurrentModel) {
        current.apply {
            binding.weatherViewGroup.humidityTextView.text = humidity.toString()
            binding.weatherViewGroup.precipitationTextView.text = dewPoint.toString()
            binding.weatherViewGroup.windTextView.text = windSpeed.toString()
            binding.feelingTextView.text = weather.firstOrNull()?.description.orEmpty()
            binding.tempTextView.text = temp.toString()
        }
    }
}