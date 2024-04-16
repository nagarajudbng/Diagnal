package com.codelabs.diagnalprogrammingtest.feature_movies.data.paged

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieDatabase
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
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
            var loadKey = -1
            when(loadType){
                LoadType.REFRESH -> {
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                LoadType.APPEND ->{
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        loadKey = 1
                    } else {
                       loadKey = (ceil(
                           (lastItem.id.toFloat() /
                                   state.config.pageSize.toFloat()))  + 1).toInt()
                    }
               }
            }
           var movieJson1 = listOf<Movie>()
            if(loadKey>0) {
                val movieJson = movieRepository.getMovieList(
                    pageNo = loadKey
                )
                movieDB.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDB.movieDao.clearALL()
                    }
                    if( movieJson.page?.contentItems?.content!=null)
                        movieJson1 = movieJson.page?.contentItems?.content!!
                    val movieEntities =
                        movieJson.page?.contentItems?.content?.map { it.toMovieEntity() }
                    if (movieEntities != null) {
                        movieDB.movieDao.upsertAll(movieEntities)
                    }
                }
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