package com.codelabs.diagnalprogrammingtest.feature_movies.data.mapper

import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieDto
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.pages.PageEntity
import com.training.pagingcompose.model.Movie
import com.training.pagingcompose.model.Page


fun MovieDto.toMovieEntity():MovieEntity{
    return MovieEntity(
        name = name,
        imageUrl = posterImage
    )
}
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

fun Page.toJsonEntity():PageEntity{
    return PageEntity(
        pageNumber = pageNum?.toInt() ?: 0,
        totalContentItems = totalContentItems?.toInt() ?: 0,
        pageSize = pageSize?.toInt() ?: 0
    )
}

fun PageEntity.toPage():Page {
    return Page(
        pageNum = pageNumber.toString(),
        totalContentItems = totalContentItems.toString(),
        pageSize = pageSize.toString()
    )
}