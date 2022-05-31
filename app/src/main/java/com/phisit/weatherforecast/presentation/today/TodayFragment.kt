package com.phisit.weatherforecast.presentation.today

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.viewBinding
import com.phisit.weatherforecast.databinding.FragmentTodayBinding

class TodayFragment : Fragment(R.layout.fragment_today) {

    private val binding: FragmentTodayBinding by viewBinding(FragmentTodayBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewListener()
    }


    private fun setupViewListener(){
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}