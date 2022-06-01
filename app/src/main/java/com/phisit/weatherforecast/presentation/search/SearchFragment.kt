package com.phisit.weatherforecast.presentation.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.phisit.weatherforecast.R
import com.phisit.weatherforecast.common.core.view.ViewExtensions.hideKeyboard
import com.phisit.weatherforecast.common.core.view.viewBinding
import com.phisit.weatherforecast.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewListener()
        setupSearchAdapter()
        observeLiveData()
    }

    private fun observeLiveData() = with(viewModel) {
        getGeocodingLiveData().observe(viewLifecycleOwner) { geocodingList ->
            searchAdapter.submitList(geocodingList)
        }
    }

    private fun setupViewListener() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.searchEditText.setOnEditorActionListener { textView: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = textView?.text ?: ""
                if (keyword.isEmpty()) {
                    return@setOnEditorActionListener false
                }
                textView?.hideKeyboard()
                viewModel.searchGeocodingList(keyword.toString())
            }
            return@setOnEditorActionListener false
        }

    }

    private fun setupSearchAdapter() {
        searchAdapter = SearchAdapter {
            val bundle = Bundle().apply {
                putString("city", Gson().toJson(it))
            }
            setFragmentResult("search", bundle)
            findNavController().popBackStack()
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchAdapter
        }
    }


}