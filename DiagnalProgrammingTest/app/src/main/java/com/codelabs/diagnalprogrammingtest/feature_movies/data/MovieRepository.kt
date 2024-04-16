package com.codelabs.diagnalprogrammingtest.feature_movies.data

import android.content.res.AssetManager
import android.util.Log
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieDatabase
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.feature_movies.domain.repository.Repository
import com.google.gson.Gson
import com.training.pagingcompose.model.MovieJSON
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val context: AssetManager,
    val movieDB:MovieDatabase
) : Repository {

   override suspend fun getMovieList(pageNo:Int): MovieJSON {
        val fileName = "CONTENTLISTINGPAGE-PAGE$pageNo.json"
        return try {
            context.open(fileName).use { inputStream ->
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val gson = Gson()
                val movieJSON = gson.fromJson(jsonString, MovieJSON::class.java)
                movieJSON//.page?.contentItems?.content?:arrayListOf()
            }
        } catch (e: IOException) {
                 MovieJSON(null)// Return
        }
    }

    override suspend fun searchQuery(query: String): Flow<List<MovieEntity>> {
        Log.d("SearchBar","Query repository= "+query)
        val flow = movieDB.movieDao.searchMoviesByName(query)
        return flow
    }
}