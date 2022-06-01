package com.phisit.weatherforecast.presentation.today

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.StringFormatUtils.toKiloMeter
import com.phisit.weatherforecast.common.core.StringFormatUtils.toPercentageString
import com.phisit.weatherforecast.common.core.view.viewBinding
import com.phisit.weatherforecast.databinding.FragmentTodayBinding
import com.phisit.weatherforecast.databinding.LayoutInfoGroupViewBinding
import com.phisit.weatherforecast.databinding.LayoutWetherGroupViewBinding
import com.phisit.weatherforecast.domain.model.CurrentModel
import com.phisit.weatherforecast.domain.model.GeocodingModel

class TodayFragment : Fragment(R.layout.fragment_today) {

    private val binding: FragmentTodayBinding by viewBinding(FragmentTodayBinding::bind)
    private val weatherViewGroup: LayoutWetherGroupViewBinding
        get() = binding.weatherViewGroup
    private val infoViewGroup: LayoutInfoGroupViewBinding
        get() = binding.weatherViewGroup.infoViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewListener()
    }

    private fun setupViewListener(){
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

    @SuppressLint("SetTextI18n")
    private fun setWeatherToView(current: CurrentModel) {
        current.apply {
            infoViewGroup.humidityTextView.text = humidity.toPercentageString()
            infoViewGroup.precipitationTextView.text = dewPoint.toPercentageString()
            infoViewGroup.windTextView.text = windSpeed.toKiloMeter()
            weatherViewGroup.feelingTextView.text = weather.firstOrNull()?.description.orEmpty()
            weatherViewGroup.tempTextView.text = temp.toString()
        }
    }
}