package com.codelabs.diagnalprogrammingtest.feature_search.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.feature_search.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository : MovieRepository
) : ViewModel() {
        private val _searchQuery = mutableStateOf("")
        val searchQuery = _searchQuery
        private val _focusState = mutableStateOf(true)
        val focusState = _focusState

        private val _movies = MutableStateFlow<List<MovieEntity>>(listOf())
        val movies = _movies.asStateFlow()
        fun onEvent(event:SearchEvent){

            when(event){
                is SearchEvent.OnSearchQuery->{
                    searchQuery.value = event.query
                    if(event.query.length>=3) {
                        viewModelScope.launch {
                            repository.searchQuery(event.query).flowOn(Dispatchers.IO)
                                .collect { books: List<MovieEntity> ->
                                    _movies.value = books
                                }
                        }
                    } else {
                        _movies.value = emptyList()
                    }
                }
                is SearchEvent.OnFocusChange ->{
                    focusState.value = event.focus
                }

                is SearchEvent.OnClearPressed ->{
                    _searchQuery.value=""
                    _movies.value = emptyList()
                }

            }
        }
}