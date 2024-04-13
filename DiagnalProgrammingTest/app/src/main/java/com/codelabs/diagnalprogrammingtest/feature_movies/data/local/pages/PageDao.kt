package com.codelabs.diagnalprogrammingtest.feature_movies.data.local.pages

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PageDao {

    @Upsert
    suspend fun upsertAll(json:PageEntity)

    @Query("SELECT * FROM pageentity WHERE id = :pageID")
    fun getPageRecord(pageID:Int):PageEntity

    @Query("DELETE FROM pageentity")
    suspend fun clearALL()

}