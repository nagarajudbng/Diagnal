package com.codelabs.diagnalprogrammingtest.feature_movies.data

import android.content.Context
import android.content.res.AssetManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieDatabase
import com.codelabs.diagnalprogrammingtest.feature_movies.domain.repository.Repository
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
import com.training.pagingcompose.model.Movie
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.FileNotFoundException
import java.io.InputStream

class MovieRepositoryTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var context: AssetManager

    @Mock
    private lateinit var movieDB: MovieDatabase

    private lateinit var repository: Repository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = MovieRepository(context, movieDB)
    }

    @Test
    fun `test getMovieList failure`() {
        `when`(context.open("CONTENTLISTINGPAGE-PAGE1.json")).thenThrow(FileNotFoundException())

        runBlocking {
            try {
                val result = repository.getMovieList(1)
            } catch (e:Exception) {
                assertEquals(e, FileNotFoundException())
            }
        }
    }

    @Test
    fun `test getMovieList success`() {
        val jsonString = "{\"page\":{\"content-items\":{\"content\":[]}}}"
        val inputStream: InputStream = ByteArrayInputStream(jsonString.toByteArray())
        `when`(context.open("CONTENTLISTINGPAGE-PAGE1.json")).thenReturn(inputStream)

        runBlocking {
            val result = repository.getMovieList(1)
            assertEquals(result.page?.contentItems?.content, emptyList<Movie>())
        }
    }
    /*
    @Test
    fun getMovieList()= runBlockingTest {

        val jsonString = "{\"page\": {\"contentItems\": {\"content\": []}}}"
        val fileName = "CONTENTLISTINGPAGE-PAGE1.json"

        val mockedAssets = mock<AssetManager> {
            on { open(fileName) } doReturn ByteArrayInputStream(jsonString.toByteArray())
        }
        `when`(context.assets).thenReturn(mockedAssets)

        val movieJson = MovieJSON(Page())

        val gson: Gson = mock()
        `when`(gson.fromJson(jsonString, MovieJSON::class.java)).thenReturn(movieJson)

        val movieRepository = MovieRepository( context,movieDB)

        val page = movieRepository.getMovieList(1)

        assertThat(page).isNotNull()
        assertThat(page?.contentItems?.content).isEmpty()
    }

     */
}