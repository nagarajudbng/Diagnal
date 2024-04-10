package com.codelabs.diagnalprogrammingtest.feature_movies.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.codelabs.diagnalprogrammingtest.R
import com.training.pagingcompose.model.Content
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(viewModel: MovieViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Movies List")
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(bottom=paddingValues.calculateBottomPadding())
        ){
            MovieList(movies = viewModel.movies)
        }

    }
}

@Composable
fun MovieList(movies: Flow<PagingData<Content>>) {
    val lazyMovieList = movies.collectAsLazyPagingItems()

    LazyColumn{
        items(lazyMovieList){lazyItem->
            MovieItem(movie = lazyItem!!)

        }
    }
}


@Composable
fun MovieItem(movie: Content) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovieTitle(
            movie.name!!,
            modifier = Modifier.weight(1f)
        )
        movie.posterImage?.let {
            MovieImage(
                //			BuildConfig.LARGE_IMAGE_URL + movie.backdrop_path,
                it,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(90.dp)
            )
        }
    }
}

@Composable
fun MovieImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    var  resId = LocalContext.current.resources.getIdentifier(imageUrl.split(".")[0],"drawable",
        LocalContext.current.packageName
    )

    AsyncImage(
        model = resId,
        placeholder = painterResource(R.drawable.ic_launcher_background), alpha = 0.45f,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )

}

@Composable
fun MovieTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = title,
        maxLines = 2,
        style = MaterialTheme.typography.bodyMedium,
        overflow = TextOverflow.Ellipsis
    )
}