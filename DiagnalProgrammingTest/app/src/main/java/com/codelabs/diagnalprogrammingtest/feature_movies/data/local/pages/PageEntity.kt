package com.codelabs.diagnalprogrammingtest.feature_movies.data.local.pages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val totalContentItems:Int,
    val pageNumber:Int,
    val pageSize:Int,
)