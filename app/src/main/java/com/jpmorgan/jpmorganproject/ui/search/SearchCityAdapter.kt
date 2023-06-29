package com.jpmorgan.jpmorganproject.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jpmorgan.jpmorganproject.R
import com.jpmorgan.jpmorganproject.databinding.ItemSearchCityBinding
import com.jpmorgan.jpmorganproject.network.response.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchCityAdapter
@Inject
constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var context : Context
    private var itemList : List<City> = arrayListOf()

    private var onCityItemSelected : ((City) -> Unit)? = null

    fun onCityItemClickListener(onCityItemSelected : ((City) -> Unit)) {
        this.onCityItemSelected = onCityItemSelected
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding = ItemSearchCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder (
        private val binding : ItemSearchCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : City) {
            binding.city = item
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("city", item)
                it.findNavController().navigate(R.id.action_search_to_result, bundle)
                onCityItemSelected?.invoke(item)
            }
        }
    }

    fun submitList(list : List<City>) {
        itemList = list
        notifyDataSetChanged()
    }
}