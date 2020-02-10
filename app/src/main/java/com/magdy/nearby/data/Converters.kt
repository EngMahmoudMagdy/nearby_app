package com.magdy.nearby.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.magdy.nearby.data.db.venues.Category


public class Converters {
    @TypeConverter
    public fun fromString(value: String): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    public fun fromArrayList(list: MutableList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    public fun fromCategory(value: String): MutableList<Category> {
        val listType = object : TypeToken<MutableList<Category>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    public fun fromArrayListCategory(list: MutableList<Category>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}