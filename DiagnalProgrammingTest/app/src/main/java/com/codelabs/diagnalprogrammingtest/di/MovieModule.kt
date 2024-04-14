package com.codelabs.diagnalprogrammingtest.di

import android.content.Context
import android.content.res.AssetManager
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieDatabase
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.feature_movies.data.paged.MovieMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun providesRepository(assets:AssetManager, movieDB: MovieDatabase):MovieRepository{
        return MovieRepository(assets, movieDB)
    }
    @Provides
    @Singleton
    fun providesAssetsManager(@ApplicationContext  app:Context):AssetManager{
        return app.assets
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(@ApplicationContext context: Context): MovieDatabase{
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providesMoviePager(movieDB:MovieDatabase,movieRepository: MovieRepository): Pager<Int, MovieEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieMediator(
                movieDB = movieDB,
                movieRepository = movieRepository
            ),
            pagingSourceFactory = {
                movieDB.movieDao.pagingSource()
            }
        )
    }
}