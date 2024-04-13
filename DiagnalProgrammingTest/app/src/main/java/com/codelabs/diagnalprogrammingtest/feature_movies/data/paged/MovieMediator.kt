package com.codelabs.diagnalprogrammingtest.feature_movies.data.paged

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieDatabase
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.feature_movies.data.mapper.toJsonEntity
import com.codelabs.diagnalprogrammingtest.feature_movies.data.mapper.toMovieEntity
import com.training.pagingcompose.model.Movie
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.ceil

@OptIn(ExperimentalPagingApi::class)
class MovieMediator(
    private val movieRepository: MovieRepository,
    private val movieDB: MovieDatabase
) : RemoteMediator<Int, MovieEntity>(){

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try{
            var insert = false
            var loadKey = -1
            when(loadType){
                LoadType.REFRESH -> {
//                    loadKey = 1
                    Log.d("MovieMediator "," LoadType.REFRESH ")
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    Log.d("MovieMediator "," LoadType.PREPEND ")
                }
                LoadType.APPEND ->{
                    Log.d("MovieMediator "," LoadType.APPEND ")
/*
                    val lastItem = state.lastItemOrNull()

                    Log.d("MovieMediator - 1",
                        "LastItem = "+ lastItem?.id)

                    if(lastItem == null) {
                        1
                        Log.d("MovieMediator - 2",
                            "LastItem = "+ lastItem)

                    } else {
                        Log.d("MovieMediator - 3",
                            "LastItem = "+ lastItem?.id+
                            ",state.config.pageSize = "+ state.config.pageSize+
                            ",  (lastItem.id / state.config.pageSize) = "+  (lastItem.id / state
                                .config.pageSize)+
                                    ",  (lastItem.id / state.config.pageSize)+1 = "+  (lastItem.id
                                    / state
                                .config.pageSize)+1
                        )
                        (lastItem.id / state.config.pageSize) + 1
                    }
                    Log.d("MovieMediator",
                        "LastItem = "+ lastItem?.id)
                    */

                    // Calculate the next page based on the
                // current number of loaded pages
                    Log.d("MovieMediator - 1",
                        "state.pages.size = "+ state.pages.size+
                                ",  state.lastItemOrNull() = "+ state.lastItemOrNull())
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        Log.d("MovieMediator - 2",
                            "lastItem = "+ lastItem)

                        loadKey = 1
                    } else {
                       loadKey = (ceil(
                           (lastItem.id.toFloat() /
                                   state.config.pageSize.toFloat()))  + 1).toInt()
                        Log.d("MovieMediator - 2",
                            "lastItem.id = "+ lastItem.id+
                            ", state.config.pageSize = "+  state.config.pageSize+
                            ", (lastItem.id / state.config.pageSize) float value= "+  ((lastItem.id.toFloat() /
                                state.config.pageSize.toFloat()))+
                            ", (lastItem.id / state.config.pageSize) ceil value = "+  ceil(
                                (lastItem.id.toFloat() /
                                    state.config.pageSize.toFloat())) +
                            ", (lastItem.id / state.config.pageSize) + 1 = "+  ( (lastItem.id /
                                    state.config.pageSize) + 1)
                        )
                    }
//                    Lod
                 /*   movieDB.withTransaction {
                        Log.d("MovieMediator - 1",
                            "state.pages.size = "+ state.pages.size+
                        ",  state.lastItemOrNull() = "+ state.lastItemOrNull())

                        var nextPage =  state.pages.size //+ 1
                        if(movieDB.pageDao.getPageRecord(nextPage)!=null )
                        {
                            Log.d("MovieMediator - 1",
                                "insert = false")
                            nextPage = nextPage+1
                            insert = true
                        } else {
                            insert =true
                            Log.d("MovieMediator - 1",
                                "insert = true")

                        }
                        Log.d("MovieMediator - 1",
                            "nextPage = "+ nextPage)
                        nextPage
                    }*/


//                    val lastItem = state.lastItemOrNull()
//                    Log.d("MovieMediator - 1",
//                        "LastItem = "+ lastItem)
//                    val nextPage = if(lastItem== null) 1 else state.pages.size + 1 // Calculate the
//                    Log.d("MovieMediator - 2",
//                        "nextPage = "+ nextPage)

                }
            }
            Log.d("MovieMediator - 3",
                "loadKey = "+ loadKey)
            var movieJson1 = listOf<Movie>()
            if(loadKey>0) {
                val movieJson = movieRepository.getMovieList(
                    pageNo = loadKey
                )
//            if(insert) {
                Log.d(
                    "MovieMediator 4- if()",
                    "insert = true"
                )
                movieDB.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDB.movieDao.clearALL()
                        movieDB.pageDao.clearALL()
                    }
                    if( movieJson.page?.contentItems?.content!=null)
                        movieJson1 = movieJson.page?.contentItems?.content!!
                    val pageEntities = movieJson.page?.let { it.toJsonEntity() }
                    val movieEntities =
                        movieJson.page?.contentItems?.content?.map { it.toMovieEntity() }
//                Log.d("MovieMediator - 4",
//                    "beerEntities = "+ beerEntities.size)

                    if (movieEntities != null) {
                        movieDB.movieDao.upsertAll(movieEntities)
                    }
                    if (pageEntities != null) {
                        movieDB.pageDao.upsertAll(pageEntities)
                    }
                }
//                insert = false
//            }
//                var mov = listOf<Movie>()
//                if ((movieJson.page?.contentItems?.content) != null) {
//                    var mov = (movieJson.page?.contentItems?.content) as List<Movie>
//                }
            }
            MediatorResult.Success(
                endOfPaginationReached = if(loadKey==-1) false else movieJson1.isEmpty()
            )
        } catch(e: IOException) {
            e.printStackTrace()
                        MediatorResult.Error(e)
        } catch(e: HttpException) {

            e.printStackTrace()
                        MediatorResult.Error(e)
        }
    }


}