package com.jpmorgan.jpmorganproject.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.jpmorgan.jpmorganproject.R
import com.jpmorgan.jpmorganproject.databinding.FragmentHomeBinding
import com.jpmorgan.jpmorganproject.databinding.FragmentSearchResultBinding
import com.jpmorgan.jpmorganproject.network.response.City
import com.jpmorgan.jpmorganproject.network.response.WeatherRo
import com.jpmorgan.jpmorganproject.ui.home.CityHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding : FragmentSearchResultBinding

    private lateinit var city : City

    private val cityHomeViewModel : CityHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        observers()
        onEvent()
    }

    fun onEvent() {
    }

    fun initData() {
        city = arguments?.getParcelable("city") ?: City("", 0.0, null, 0.0,"","")
        cityHomeViewModel.getWeatherByLonLat(city.lat, city.lon, "metric")
    }

    fun observers() {
        //observes weather info that are selected from the searched city list by the user and shows information
        cityHomeViewModel.weatherData.observe(this, androidx.lifecycle.Observer {
            val imageUrl = "https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png"
            Glide.with(binding.ivWeatherIcon)
                .load(imageUrl)
                .into(binding.ivWeatherIcon)
            binding.weather = it
            binding.tvWeather.text = it.weather[0].main
            binding.tvTemp.text = "Current Temp : ${it.main.temp.toInt()}\u2103"
            binding.tvTempMin.text = "Lowest Temp : ${it.main.temp_min.toInt()}\u2103"
            binding.tvTempMax.text = "Highest Temp : ${it.main.temp_max.toInt()}\u2103"
            binding.tvHumidity.text = "Humidity : ${it.main.humidity}"


        })
    }
}