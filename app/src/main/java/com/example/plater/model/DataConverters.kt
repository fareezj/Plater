package com.example.plater.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class DataConverters {
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

}