package com.jpmorgan.jpmorganproject.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jpmorgan.jpmorganproject.network.response.City
import java.time.LocalDateTime

@Entity(tableName = "searchedCity")
data class SearchedCity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @ColumnInfo(name = "city_lat")
    val lat : Double,
    @ColumnInfo(name = "city_lon")
    val lon : Double,
    @ColumnInfo(name = "searched_time")
    val searchedTime : String
)
