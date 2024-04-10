package com.codelabs.diagnalprogrammingtest.feature_movies.data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.training.pagingcompose.model.ContentItems
import com.training.pagingcompose.model.MovieJSON
import com.training.pagingcompose.model.Page
import java.io.IOException
import javax.inject.Inject

class MovieRepository @Inject constructor(val context: AssetManager) {

    fun getMovieList(pageNo:Int): Page? {
        val fileName = "CONTENTLISTINGPAGE-PAGE$pageNo.json"
        return try {
            context.open(fileName).use { inputStream ->
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val gson = Gson()
                val movieJSON = gson.fromJson(jsonString, MovieJSON::class.java)
                movieJSON.page
            }
        } catch (e: IOException) {
            Page(contentItems = ContentItems(content = arrayListOf())) // Return
        }
    }
}