package com.codelabs.diagnalprogrammingtest.di

import android.app.Application
import android.content.res.AssetManager
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun providesRepository(assets:AssetManager):MovieRepository{
        return MovieRepository(assets)
    }
    @Provides
    @Singleton
    fun providesAssetsManager(app:Application):AssetManager{
        return app.assets
    }

}