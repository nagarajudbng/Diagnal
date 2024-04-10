package com.codelabs.diagnalprogrammingtest.feature_movies.data

import android.content.Context
import android.content.res.AssetManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.training.pagingcompose.model.MovieJSON
import com.training.pagingcompose.model.Page
import org.mockito.Mockito.`when`
import java.io.ByteArrayInputStream
import com.google.common.truth.Truth.assertThat
class MovieRepositoryTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getMovieList()= runBlockingTest {
        val context: Context = mock()

        val jsonString = "{\"page\": {\"contentItems\": {\"content\": []}}}"
        val fileName = "CONTENTLISTINGPAGE-PAGE1.json"

        val mockedAssets = mock<AssetManager> {
            on { open(fileName) } doReturn ByteArrayInputStream(jsonString.toByteArray())
        }
        `when`(context.assets).thenReturn(mockedAssets)

        val movieJson = MovieJSON(Page())

        val gson: Gson = mock()
        `when`(gson.fromJson(jsonString, MovieJSON::class.java)).thenReturn(movieJson)

        val movieRepository = MovieRepository( context)

        val page = movieRepository.getMovieList(1)

        assertThat(page).isNotNull()
        assertThat(page?.contentItems?.content).isEmpty()
    }
}