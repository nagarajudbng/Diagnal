package com.codelabs.diagnalprogrammingtest.feature_movies.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.paged.MovieSource
import com.training.pagingcompose.model.Content
import kotlinx.coroutines.flow.Flow

class MovieViewModel (private val repository:MovieRepository) : ViewModel() {

    val movies: Flow<PagingData<Content>> = Pager(PagingConfig(pageSize = 20)){
        MovieSource(movieRepository = repository)
    }.flow
}