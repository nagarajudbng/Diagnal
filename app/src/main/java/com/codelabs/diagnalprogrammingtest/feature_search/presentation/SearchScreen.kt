package com.codelabs.diagnalprogrammingtest.feature_search.presentation

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.codelabs.diagnalprogrammingtest.feature_search.SearchEvent
import com.codelabs.diagnalprogrammingtest.navigation.NavigationItem
import com.codelabs.diagnalprogrammingtest.ui.MyCard
import com.codelabs.diagnalprogrammingtest.ui.cellHorizontalSpace
import com.codelabs.diagnalprogrammingtest.ui.cellVerticalSpace
import com.codelabs.diagnalprogrammingtest.ui.gridViewEndPadding
import com.codelabs.diagnalprogrammingtest.ui.pxToDp
import com.codelabs.diagnalprogrammingtest.ui.gridViewStartPadding

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
                Spacer(Modifier.height(10.dp))
                MovieListStaggeredGrid(viewModel)
        }

    }
}

@Composable
fun MovieListStaggeredGrid(viewModel: SearchViewModel) {
    val movieState = viewModel.movies.collectAsState()
    val moviesList = movieState.value
    val query = viewModel.searchQuery.value

    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Fixed(7)
    } else StaggeredGridCells.Fixed(3)
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = gridViewStartPadding.dp, end = gridViewEndPadding.dp)
            .background(Color.Black),

        columns = cellConfiguration,
        verticalItemSpacing = cellVerticalSpace.pxToDp(LocalContext.current).dp,
        horizontalArrangement = Arrangement.spacedBy(cellHorizontalSpace.pxToDp(LocalContext.current).dp)
    ) {


        items(moviesList.size){ i->
            var movie = moviesList.get(i)
            MyCard(movie, query)
        }
    }
}

