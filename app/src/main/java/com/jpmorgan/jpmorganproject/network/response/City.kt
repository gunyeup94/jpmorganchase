package com.jpmorgan.jpmorganproject.network.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val country: String,
    val lat: Double,
    val local_names: LocalNames?,
    val lon: Double,
    val name: String,
    val state: String
) : Parcelable {
    override fun hashCode(): Int {
        var result = country.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lon.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}