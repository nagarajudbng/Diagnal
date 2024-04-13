package com.codelabs.diagnalprogrammingtest.feature_movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // or any other appropriate type for your primary key
    val name:String?,
    val imageUrl:String?
)

