package com.codelabs.diagnalprogrammingtest.feature_movies.data.mapper

import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.training.pagingcompose.model.Movie


fun Movie.toMovieEntity():MovieEntity{
    return MovieEntity(
        name = name,
        imageUrl = posterImage
    )
}

fun MovieEntity.toMovie(): Movie{
    return Movie(
        name = name,
        posterImage = imageUrl
    )
}
