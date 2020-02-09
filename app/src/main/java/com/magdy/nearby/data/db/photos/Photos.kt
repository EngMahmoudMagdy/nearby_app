package com.magdy.nearby.data.db.photos


data class Photos(
    val count: Int,
    val dupesRemoved: Int,
    val items: List<PhotoItem>
)