package com.jpmorgan.jpmorganproject.ui.home

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import com.jpmorgan.jpmorganproject.R
import com.jpmorgan.jpmorganproject.databinding.FragmentHomeBinding
import com.jpmorgan.jpmorganproject.room.SearchedCityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding

    private val searchedCityViewModel : SearchedCityViewModel by viewModels()
    private val cityHomeViewModel : CityHomeViewModel by viewModels()
    private var locationPermitted = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationPermitted = isLocationPermissionGranted()
        binding.hasCity = true
        initData()
        observers()
        onEvent()
    }

    fun onEvent() {
        binding.btnSearch.setOnClickListener {
            it.findNavController().navigate(R.id.action_home_to_search)
        }
    }

    fun initData() {
        if (locationPermitted) {
            binding.locationPermitted = true
            startLocationUpdates()
        } else {
            binding.locationPermitted = false
            searchedCityViewModel.getCities()
        }
    }

    /**
     * When location is permitted, this method will get called to get user's location.
     * With user's location information (latitude and longitude), we will get weather info by using cityHomeViewModel
     */
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                for (location in p0.locations) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Use the latitude and longitude values
                    binding.locationPermitted = true
                    binding.hasCity = true
                    cityHomeViewModel.getWeatherByLonLat(latitude, longitude, "metric")
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun observers() {
        //This observer is for searched list that is stored in room
        searchedCityViewModel.searchedCities.observe(this, Observer {
            binding.hasCity = true
            val sorted = it.sortedByDescending { it.searchedTime }
            cityHomeViewModel.getWeatherByLonLat(sorted[0].lat, sorted[0].lon, "metric")
        })

        //This observes if there is at least one searched city or not
        searchedCityViewModel.hasNoCity.observe(this, Observer {
            binding.hasCity = false
        })

        //This observes when weather data is retrieved from the api request
        cityHomeViewModel.weatherData.observe(this, Observer {
            val imageUrl = "https://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png"
            Glide.with(binding.ivWeatherIcon)
                .load(imageUrl)
                .into(binding.ivWeatherIcon)
            binding.weather = it
            binding.tvWeather.text = it.weather[0].main
            binding.tvTemp.text = "Current Temp : ${it.main.temp.toInt()}\u2103"
            binding.tvTempMin.text = "Lowest Temp : ${it.main.temp_min.toInt()}\u2103"
            binding.tvTempMax.text = "Highest Temp : ${it.main.temp_max.toInt()}\u2103"
            binding.tvHumidity.text = "Humidity : ${it.main.humidity}"        })
    }
}