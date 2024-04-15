package com.codelabs.diagnalprogrammingtest.feature_movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.feature_movies.data.mapper.toMovie
import com.training.pagingcompose.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    pager:Pager<Int,MovieEntity>
) : ViewModel() {

    val movies = pager
        .flow
        .cachedIn(viewModelScope)
}