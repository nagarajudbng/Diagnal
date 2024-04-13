package com.codelabs.diagnalprogrammingtest.feature_movies.data.paged

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieDatabase
import com.training.pagingcompose.model.Movie
/*
class MovieSource(
    private val movieRepository: MovieRepository,
    movieDB: MovieDatabase
) : PagingSource<Int, Movie>(){


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try{
            Log.d("MovieSource","page = "+params.key)
            val nextPage = params.key?:1
            val movieListResponse = movieRepository.getMovieList(nextPage)

            LoadResult.Page(
                data = movieListResponse?.contentItems?.content as List<Movie>,
                prevKey = if(nextPage == 1) null else nextPage-1,
                nextKey = movieListResponse.pageNum?.toInt()?.plus(1)
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }


}
*/