package com.codelabs.diagnalprogrammingtest.feature_search.presentation

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.codelabs.diagnalprogrammingtest.R
import com.codelabs.diagnalprogrammingtest.feature_search.SearchEvent
import com.codelabs.diagnalprogrammingtest.navigation.NavigationItem
import com.codelabs.diagnalprogrammingtest.ui.MyCard
import com.codelabs.diagnalprogrammingtest.ui.pxToDp
import com.codelabs.diagnalprogrammingtest.ui.util.WindowType
import com.codelabs.diagnalprogrammingtest.ui.util.rememberWindowSize

private fun Modifier.bottomElevation(): Modifier = this.then(Modifier.drawWithContent {
    val paddingPx = 8.dp.toPx()
    clipRect(
        left = 0f,
        top = 0f,
        right = size.width,
        bottom = size.height + paddingPx
    ) {
        this@drawWithContent.drawContent()
    }
})

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel) {
    Scaffold(
    ) { paddingValues ->
        viewModel.onEvent((SearchEvent.OnFocusChange(true)))
        Column(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .background(Color.Black)
        ){
            SearchBar(
                Modifier.padding(horizontal = 16.dp),
                onSearchTextEntered = {
                    Log.d("SearchBar","Query = "+it)
                    viewModel.onEvent(SearchEvent.OnSearchQuery(it))
                },
                onFocusChange = {
                    viewModel.onEvent((SearchEvent.OnFocusChange(it)))
                },
                onBackPressed = {
                    viewModel.onEvent(SearchEvent.OnSearchQuery(""))
                    viewModel.onEvent(SearchEvent.OnClearPressed)
                     navController.navigate(NavigationItem.HOME.route){
                         popUpTo(NavigationItem.SEARCH.route) {
                             inclusive = true
                         }
                     }
                },
                onClearPressed = {
                    viewModel.onEvent(SearchEvent.OnClearPressed)
                },
                viewModel.searchQuery.value,
                viewModel.focusState.value
            )
                MovieListStaggeredGrid(viewModel)
        }

    }
}

@Composable
fun MovieListStaggeredGrid(viewModel: SearchViewModel) {
//    val lazyMovieList:LazyPagingItems<Movie> = movies.collect()
//    val lazyMovieList = content

    val movieState = viewModel.movies.collectAsState()
    val moviesList = movieState.value
    Log.d("Search","MovieListStaggeredGrid = "+moviesList.size)
    val windowSizeInfo = rememberWindowSize()
    var columns = 3
    if (
        windowSizeInfo.widthInfo is WindowType.Compact
        || windowSizeInfo.widthInfo is WindowType.Medium
    ) {
        columns = 3
    } else {
        columns = 7
    }
    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Fixed(7)
    } else StaggeredGridCells.Fixed(3)
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Black),

        columns = cellConfiguration,
        verticalItemSpacing = 90f.pxToDp(LocalContext.current).dp,
        horizontalArrangement = Arrangement.spacedBy(30f.pxToDp(LocalContext.current).dp)
    ) {


        items(moviesList.size){ i->
            var movie = moviesList.get(i)
                MyCard(
                    movie
                )
            }
    }
}

