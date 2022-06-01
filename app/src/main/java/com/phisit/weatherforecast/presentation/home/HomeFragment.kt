package com.phisit.weatherforecast.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.Constants
import com.phisit.weatherforecast.common.core.StringFormatUtils.toKiloMeter
import com.phisit.weatherforecast.common.core.StringFormatUtils.toPercentageString
import com.phisit.weatherforecast.common.core.view.viewBinding
import com.phisit.weatherforecast.databinding.FragmentHomeBinding
import com.phisit.weatherforecast.databinding.LayoutInfoGroupViewBinding
import com.phisit.weatherforecast.databinding.LayoutWetherGroupViewBinding
import com.phisit.weatherforecast.domain.model.CurrentModel
import com.phisit.weatherforecast.domain.model.GeocodingModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val weatherViewGroup: LayoutWetherGroupViewBinding
        get() = binding.weatherViewGroup
    private val infoViewGroup: LayoutInfoGroupViewBinding
        get() = binding.weatherViewGroup.infoViewGroup

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

        binding.toggleTemp.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                if (checkedId == binding.celsiusButton.id) {
                    viewModel.setUnitsOfTemp(Constants.TEMP_UNIT_C)
                } else {
                    viewModel.setUnitsOfTemp(Constants.TEMP_UNIT_F)
                }
            }
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
            weatherViewGroup.cityNameTextView.text = name
            weatherViewGroup.stateNameTextView.text = country
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherToView(current: CurrentModel) {
        val tempUnit = binding.toggleTemp.checkedButtonId.let {
            if (it == binding.celsiusButton.id) "C" else "F"
        }
        current.apply {
            infoViewGroup.humidityTextView.text = humidity.toPercentageString()
            infoViewGroup.precipitationTextView.text = dewPoint.toPercentageString()
            infoViewGroup.windTextView.text = windSpeed.toKiloMeter()
            weatherViewGroup.feelingTextView.text = weather.firstOrNull()?.description.orEmpty()
            weatherViewGroup.tempTextView.text = temp.toString() + tempUnit
        }
    }
}