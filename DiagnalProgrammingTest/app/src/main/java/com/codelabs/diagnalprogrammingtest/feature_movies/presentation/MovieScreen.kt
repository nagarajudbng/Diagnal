package com.codelabs.diagnalprogrammingtest.feature_movies.presentation

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.codelabs.diagnalprogrammingtest.R
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.navigation.NavigationItem
import com.codelabs.diagnalprogrammingtest.ui.HomeAppBar
import com.codelabs.diagnalprogrammingtest.ui.MyCard
import com.codelabs.diagnalprogrammingtest.ui.cellHorizontalSpace
import com.codelabs.diagnalprogrammingtest.ui.cellVerticalSpace
import com.codelabs.diagnalprogrammingtest.ui.gridViewEndPadding
import com.codelabs.diagnalprogrammingtest.ui.pxToDp
import com.codelabs.diagnalprogrammingtest.ui.gridViewStartPadding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
@Preview
@Composable
fun MovieScreenPreview(){
    val movieViewModel: MovieViewModel = viewModel()
    MovieScreen(
        navController = rememberNavController(),
        viewModel = movieViewModel
    )
}

var content: List<MovieEntity> = listOf<MovieEntity>(
    MovieEntity(id=1,name="The Birds", imageUrl = "poster1.jpg"),
    MovieEntity(id=2,name="Rear Window",imageUrl = "poster2.jpg"),
    MovieEntity(id=2,name="Family Pot",imageUrl = "poster3.jpg"),
    MovieEntity(id=2,name="Family Pot Family Pot Family Pot",imageUrl = "poster1.jpg"),
    MovieEntity(id=2,name="Rear Window",imageUrl = "poster2.jpg"),
    MovieEntity(name="The Birds",imageUrl = "poster3.jpg"))
fun createPagingDataFlow(content: List<MovieEntity>): Flow<PagingData<MovieEntity>> {
    return flowOf(PagingData.from(content))
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    navController:NavHostController,
    viewModel: MovieViewModel
) {
    Scaffold(
        topBar = {
            HomeAppBar(
                title = stringResource(id = R.string.app_bar_title),
                searchClick = { navController.navigate(NavigationItem.SEARCH.route) },
                backClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    bottom = paddingValues.calculateBottomPadding(),
                    top = paddingValues.calculateTopPadding()
                )
                .background(Color.Black)
        ){
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                MovieListStaggeredGrid(movies = viewModel.movies)
                Image(
                    painter = painterResource(id = R.drawable.nav_bar),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20f.pxToDp(LocalContext.current).dp),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = ""
                )
            }
        }

    }
}
@Composable
fun MovieListStaggeredGrid(movies: Flow<PagingData<MovieEntity>>) {
    val lazyMovieList:LazyPagingItems<MovieEntity> = movies.collectAsLazyPagingItems()
    val state = rememberLazyStaggeredGridState()

    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Fixed(7)
    } else StaggeredGridCells.Fixed(3)
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = gridViewStartPadding.pxToDp(LocalContext.current).dp,
                end = gridViewEndPadding.pxToDp(LocalContext.current).dp)
            .background(Color.Black),
        state  = state,
        columns = cellConfiguration,
        verticalItemSpacing = cellVerticalSpace.pxToDp(LocalContext.current).dp,
        horizontalArrangement = Arrangement.spacedBy(cellHorizontalSpace.pxToDp(LocalContext.current).dp)
    ) {
        items(lazyMovieList.itemCount) { index ->
            val movie = lazyMovieList[index] ?: return@items // Handle null item
            MyCard(movie = movie, query = "")
        }
        lazyMovieList.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val errorMessage = (loadState.refresh as LoadState.Error).error.localizedMessage
                    item {
                        RetrySection(errorMessage = errorMessage) { retry() }
                    }
                }
                loadState.append is LoadState.Error -> {
                    val errorMessage = (loadState.append as LoadState.Error).error.localizedMessage
                    item {
                        RetrySection(errorMessage = errorMessage) { retry() }
                    }
                }
            }
        }
    }
}
@Composable
fun RetrySection(errorMessage: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(errorMessage)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onRetry() }) {
                Text("Retry")
            }
        }
    }
}


