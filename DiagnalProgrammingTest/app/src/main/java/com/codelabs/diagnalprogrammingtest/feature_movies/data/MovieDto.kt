package com.codelabs.diagnalprogrammingtest.feature_movies.data

import com.google.gson.annotations.SerializedName

data class MovieDto (
    @SerializedName("name"         ) var name         : String? = null,
    @SerializedName("poster-image" ) var posterImage : String? = null
)