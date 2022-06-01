package com.phisit.weatherforecast.common.core.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object ViewExtensions {

    fun View.hideKeyboard() {
        clearFocus()
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}