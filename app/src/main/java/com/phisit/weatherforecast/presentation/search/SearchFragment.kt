package com.phisit.weatherforecast.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.viewBinding
import com.phisit.weatherforecast.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewListener()
    }

    private fun setupViewListener() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}