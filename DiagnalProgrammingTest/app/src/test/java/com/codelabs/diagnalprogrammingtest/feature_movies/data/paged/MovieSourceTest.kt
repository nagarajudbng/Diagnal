package com.codelabs.diagnalprogrammingtest.feature_movies.data.paged

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingSource.LoadParams
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.training.pagingcompose.model.Content
import com.training.pagingcompose.model.ContentItems
import com.training.pagingcompose.model.Page
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieSourceTest {

    @Mock
    lateinit var movieRepository: MovieRepository

    private lateinit var movieSource: MovieSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieSource = MovieSource(movieRepository)
    }

    @Test
    fun `load data successfully`() = runBlockingTest {
        // Given
        val expectedPage = Page(contentItems = ContentItems( arrayListOf(Content("Movie 1"), Content
            ("Movie 2"))
        ))
        val expectedLoadParams = LoadParams.Refresh(1, 20, false)
        `when`(movieRepository.getMovieList(1)).thenReturn(expectedPage)

        // When
        val result = movieSource.load(expectedLoadParams)
        // Then
        assertEquals(LoadResult.Page(expectedPage.contentItems?.content as List<Content>, null,
            null),
            result)
    }

    @Test
    fun `handle exception during data loading`() = runBlockingTest {
        val expectedException = RuntimeException("Error loading data")
        val expectedLoadParams = PagingSource.LoadParams.Refresh(1, 20, false)
        `when`(movieRepository.getMovieList(1)).thenThrow(expectedException)

        // When
        val result = movieSource.load(expectedLoadParams)

        // Then
        assertEquals(PagingSource.LoadResult.Error<Int,Content>(expectedException), result)
    }
    @After
    fun tearDown() {
    }

}