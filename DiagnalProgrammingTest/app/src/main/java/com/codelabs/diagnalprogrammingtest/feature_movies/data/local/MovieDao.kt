package com.codelabs.diagnalprogrammingtest.feature_movies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import androidx.room.util.query
import com.training.pagingcompose.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAll(movies:List<MovieEntity>)
    
    @Query("SELECT * FROM movieentity")
    fun pagingSource(): PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM movieentity WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchMoviesByName(searchQuery:String): Flow<List<MovieEntity>>

//    @Query("SELECT * FROM movieentity ")
//     fun searchMoviesByName(): Flow<List<MovieEntity>>

    @Query("DELETE FROM movieentity")
    suspend fun clearALL()
}