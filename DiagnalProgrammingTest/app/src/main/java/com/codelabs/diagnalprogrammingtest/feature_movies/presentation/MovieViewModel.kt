package com.codelabs.diagnalprogrammingtest.feature_movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    pager:Pager<Int,MovieEntity>
) : ViewModel() {

    val movies = pager
        .flow
        .cachedIn(viewModelScope)
}