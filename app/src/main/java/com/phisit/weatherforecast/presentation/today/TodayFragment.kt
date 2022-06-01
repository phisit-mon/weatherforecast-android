package com.phisit.weatherforecast.presentation.today

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.Constants
import com.phisit.weatherforecast.common.core.StringFormatUtils.toKiloMeter
import com.phisit.weatherforecast.common.core.StringFormatUtils.toPercentageString
import com.phisit.weatherforecast.common.core.view.viewBinding
import com.phisit.weatherforecast.databinding.FragmentTodayBinding
import com.phisit.weatherforecast.databinding.LayoutInfoGroupViewBinding
import com.phisit.weatherforecast.databinding.LayoutWetherGroupViewBinding
import com.phisit.weatherforecast.domain.model.DailyModel
import com.phisit.weatherforecast.domain.model.GeocodingModel
import com.phisit.weatherforecast.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodayFragment : Fragment(R.layout.fragment_today) {

    private val binding: FragmentTodayBinding by viewBinding(FragmentTodayBinding::bind)
    private val homeViewModel: HomeViewModel by sharedViewModel()

    private val weatherViewGroup: LayoutWetherGroupViewBinding
        get() = binding.weatherViewGroup
    private val infoViewGroup: LayoutInfoGroupViewBinding
        get() = binding.weatherViewGroup.infoViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewListener()
        observeViewModel()
    }

    private fun observeViewModel() {
        homeViewModel.getDailyWeatherLiveData().observe(viewLifecycleOwner) {
            setWeatherToView(it)
        }
        homeViewModel.getGeocodingLiveData().observe(viewLifecycleOwner) {
            setGeocodingToView(it)
        }
    }

    private fun setupViewListener() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setGeocodingToView(geocodingModel: GeocodingModel) {
        geocodingModel.apply {
            weatherViewGroup.cityNameTextView.text = name
            weatherViewGroup.stateNameTextView.text = country
        }
    }

    private fun getUnitOfTemp(): String {
        return if (homeViewModel.unitsOfTemp == Constants.TEMP_UNIT_C) {
            "C"
        } else {
            "F"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setWeatherToView(daily: DailyModel) {
        daily.apply {
            infoViewGroup.humidityTextView.text = humidity.toPercentageString()
            infoViewGroup.precipitationTextView.text = dewPoint.toInt().toPercentageString()
            infoViewGroup.windTextView.text = windSpeed.toKiloMeter()
            weatherViewGroup.feelingTextView.text = weather.firstOrNull()?.description.orEmpty()
            weatherViewGroup.tempTextView.text = "${daily.feelsLike.day}${getUnitOfTemp()}"
            weatherViewGroup.dateTextView.text = getString(R.string.today)

            daily.feelsLike.let {
                binding.morningTextView.text = "${it.morn}${getUnitOfTemp()}"
                binding.dayTextView.text = "${it.day}${getUnitOfTemp()}"
                binding.eveTextView.text = "${it.eve}${getUnitOfTemp()}"
                binding.nightTextView.text = "${it.night}${getUnitOfTemp()}"
            }

        }
    }
}