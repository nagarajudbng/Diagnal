package com.codelabs.diagnalprogrammingtest.feature_movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.pages.PageDao
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.pages.PageEntity

@Database(
    entities = [MovieEntity::class,PageEntity::class],
    version = 1, exportSchema = false
)
abstract class MovieDatabase:RoomDatabase() {

    abstract val movieDao:MovieDao

    abstract val pageDao:PageDao

}