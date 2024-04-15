package com.codelabs.diagnalprogrammingtest.feature_search.presentation

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.util.DisplayMetrics
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import com.codelabs.diagnalprogrammingtest.R
import com.codelabs.diagnalprogrammingtest.feature_search.SearchEvent
import com.codelabs.diagnalprogrammingtest.navigation.NavigationItem
import com.codelabs.diagnalprogrammingtest.ui.theme.titilliumFamily
import com.codelabs.diagnalprogrammingtest.ui.util.WindowType
import com.codelabs.diagnalprogrammingtest.ui.util.rememberWindowSize
import com.training.pagingcompose.model.Movie
import kotlinx.coroutines.flow.Flow

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
fun Float.pxToDp(context: Context): Float =
    (this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))

@Preview
@Composable
fun SearchScreenPreview(){



//    var viewModel =  MovieViewModel(
//        MovieRepository(LocalContext.current.assets))
//    SearchScreen(
//        rememberNavController(),
//        viewModel
//    )
}

var content = listOf<Movie>(
    Movie("The Birds","poster1.jpg"),
    Movie("Rear Window","poster2.jpg"),
    Movie("Family Pot","poster3.jpg"),
    Movie("Family Pot Family Pot Family Pot","poster1.jpg"),
    Movie("Rear Window","poster2.jpg"),
    Movie("The Birds","poster3.jpg"))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel) {
    Scaffold(
//        topBar = {
//            TopAppBar(title = {
//
//            })
//        }
    ) { paddingValues ->
        viewModel.onEvent((SearchEvent.OnFocusChange(true)))
        Column(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .background(Color.Black)
        ){
//            Spacer(Modifier.height(20f.pxToDp(LocalContext.current).dp))
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
//            Spacer(Modifier.height(36f.pxToDp(LocalContext.current).dp))
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .bottomElevation()
//                    .clip(GenericShape { size, _ ->
//                        lineTo(size.width, 0f)
//                        lineTo(size.width, Float.MAX_VALUE)
//                        lineTo(0f, Float.MAX_VALUE)
//                    })
//                    .shadow(16.dp)
//                    .background(Color.White)
//            ) {
//            Box(
//                modifier = Modifier
//                    .padding(start = 16.dp, end = 16.dp)
//                    .shadow(
//                        elevation = 4.dp,
//                        shape = RoundedCornerShape(0.dp),
//                        clip = true
//                    )
//            ) {
                MovieListStaggeredGrid(viewModel)
//            }
//                Image(
//                    modifier = Modifier
//                        .height(36f.pxToDp(LocalContext.current).dp),
//                    painter = painterResource(id = R.drawable.nav_bar),
//                    contentDescription = null,
//                )

//            }

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
//        columns = StaggeredGridCells.Fixed(2),
//        contentPadding = PaddingValues(16.dp),
//        verticalItemSpacing = 16.dp,
//        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        items(moviesList.size){ i->
            var movie = moviesList.get(i)
                MyCard(
                    movie
                )
            }

//        items(items, var key : kotlin . Any ? = arrayOf<>(it.id)) { index ->
//            lazyMovieList.forEachIndexed { index, card ->
//                item(span = { GridItemSpan(1) }) {
//                    card.posterImage?.let {
//                        card.name?.let { it1 ->
//                            MyCard(
//                                title = card.name,
//                                subtitle = card.posterImage
//                            )
//                        }
//                    }
//                }
//            }
//        }
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
@Composable
fun MovieList(movies: Flow<PagingData<Movie>>) {
    val lazyMovieList = content//movies.collectAsLazyPagingItems()
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
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Black),
//        verticalArrangement = Arrangement.spacedBy(90f.pxToDp(LocalContext.current).dp),
        verticalArrangement = Arrangement.spacedBy(LocalContext.current.resources.getDimension(R
            .dimen.vertical_space).dp),
        horizontalArrangement = Arrangement.spacedBy(LocalContext.current.resources.getDimension(R
            .dimen.horizontal_space).dp),
        columns = GridCells.Fixed(columns)
    ) {
        items(lazyMovieList.size) { index ->
            lazyMovieList.forEachIndexed { index, card ->
                this@LazyVerticalGrid.item(span = { GridItemSpan(1) }) {
                    card.posterImage?.let {
                        card.name?.let { it1 ->
//                            MyCard(
//                                movie = movie
//                            )
                        }
                    }
                }
            }
    }
    }
}

@Preview
@Composable
fun MyCardPreview(){
    MyCard(Movie("Family PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily PotFamily Pot","poster2.jpg"))
}


@Composable
fun MyCard(
    movie: Movie
) {

    Card(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black, //Card background color
            contentColor = Color.White  //Card content color,e.g.text
        ),
        shape = RoundedCornerShape(0.dp)

    ) {
        var  resId = LocalContext.current.resources.getIdentifier(
            movie.posterImage?.split(".")?.get(0) ?: "","drawable", LocalContext.current.packageName)
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter =  if(resId == 0){
                            painterResource(id = R.drawable.no_image_found)
                        } else {
                            painterResource(id = resId)
                        },
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(
                    top = 24f.pxToDp(LocalContext.current).dp,
                    bottom = 0.dp
                )
                .background(Color.Black)
        ) {

            movie.name?.let { name->
                Text(
                    text = if(name.length>12){
                            name.subSequence(0,11).toString()+" ..."
                        } else {
                        name
                        }
                        ,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = titilliumFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { ptToSp(36f).sp }
                )
            }

        }
    }
}
@Composable
fun ptToSp(pt:Float):Float{
    val scaledDensity: Float = LocalContext.current.resources.displayMetrics.density
    return pt / scaledDensity
}

@Preview
@Composable
fun previewShadow(){
    Shadow()
}
@Composable
fun Shadow(){
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .shadow(
                elevation = 25.dp,
                shape = RoundedCornerShape(25.dp),
                clip = true
            )
    ) {
        Image(
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp),
                    painter = painterResource(id = R.drawable.poster1),
                    contentDescription = null,
                )
    }
}

