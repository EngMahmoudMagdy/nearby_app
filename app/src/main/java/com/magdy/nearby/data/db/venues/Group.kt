package com.magdy.nearby.data.db.venues


import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("items")
    val entries: List<Item>
)