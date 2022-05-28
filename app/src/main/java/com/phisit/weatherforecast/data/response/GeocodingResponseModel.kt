package com.phisit.weatherforecast.data.response

import com.google.gson.annotations.SerializedName


class GeocodingResponseModel : ArrayList<GeocodingResponseModelItem>()

data class GeocodingResponseModelItem(
    @SerializedName("country")
    val country: String = "",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("state")
    val state: String = ""
)