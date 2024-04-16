package com.codelabs.diagnalprogrammingtest.feature_movies.domain.repository

import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.training.pagingcompose.model.Movie
import com.training.pagingcompose.model.MovieJSON
import kotlinx.coroutines.flow.Flow

interface Repository {
   suspend fun getMovieList(pageNo:Int): MovieJSON
   suspend fun searchQuery(query:String): Flow<List<MovieEntity>>
}