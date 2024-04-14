package com.codelabs.diagnalprogrammingtest.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.feature_movies.data.mapper.toMovie
import com.codelabs.diagnalprogrammingtest.feature_search.SearchEvent
import com.training.pagingcompose.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val repository : MovieRepository
) : ViewModel() {
//    fun searchQuery(it: String) {
//
//    }
//    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)){
//        MovieSource(movieRepository = repository, movieDB = movieDB)
//    }.flow

//    val movies = pager
//        .flow
//        .map { pagingData->
//            pagingData.map { it.toMovie() }
//        }
//        .cachedIn(viewModelScope)
        private val _movies = MutableStateFlow<List<Movie>>(
    listOf())
        val movies = _movies.asStateFlow()


        fun onEvent(event:SearchEvent){
            when(event){
                is SearchEvent.OnSearchQuery->{
                    viewModelScope.launch {
//                        var movieList:List<Movie> = repository.searchQuery(event.query)
//                        _movies.value = movieList

//                        repository.searchQuery(event.query).collect { movies ->
//                            _movieList.value = movies
//                        }
                        repository.searchQuery(event.query).flowOn(Dispatchers.IO).collect {
                            books: List<MovieEntity> ->
                            _movies.value = books.map { it.toMovie() }
                        }
                    }
                }

            }
        }
}