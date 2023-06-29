package com.jpmorgan.jpmorganproject.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpmorgan.jpmorganproject.R
import com.jpmorgan.jpmorganproject.databinding.FragmentSearchCityBinding
import com.jpmorgan.jpmorganproject.room.SearchedCity
import com.jpmorgan.jpmorganproject.room.SearchedCityViewModel
import com.jpmorgan.jpmorganproject.ui.home.CityHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchCity : Fragment() {
    private lateinit var binding : FragmentSearchCityBinding

    private val cityHomeViewModel : CityHomeViewModel by viewModels()

    private val searchedCityViewModel : SearchedCityViewModel by viewModels()

    @Inject
    lateinit var searchCityAdapter: SearchCityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_city, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        observers()
        onEvent()
    }

    fun initView() {
        //Init recyclerview for the result of the searched cities
        with(binding.rvSearchResult) {
            adapter = searchCityAdapter
            layoutManager = LinearLayoutManager(context)
        }
        //Submitting empty list for the first time
        searchCityAdapter.submitList(arrayListOf())
    }

    fun onEvent() {
        //listen to edittext and request api to get list of corresponding cities
        binding.etSearch.doOnTextChanged { text, start, before, count ->
            if (isSearchedKeywordValid(text.toString())) {
                cityHomeViewModel.getCityInfoByName(text.toString(), 10)
            } else {
                searchCityAdapter.submitList(arrayListOf())
            }
        }

        //Saving the city in room
        searchCityAdapter.onCityItemClickListener {
            val currentTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val currentTimeString = formatter.format(currentTime)
            val searchedCity = SearchedCity(
                id = 0,
                lat = it.lat,
                lon = it.lon,
                searchedTime = currentTimeString
            )
            searchedCityViewModel.insertCity(searchedCity)
        }
    }

    fun initData() {

    }

    fun observers() {
        //observes to get the list of the cities that are searched
        cityHomeViewModel.cityList.observe(this, Observer {
            searchCityAdapter.submitList(it)
        })
    }

    fun isSearchedKeywordValid (
        keyword : String
    ) : Boolean {
        return keyword.matches("[a-zA-z]+".toRegex())
    }
}