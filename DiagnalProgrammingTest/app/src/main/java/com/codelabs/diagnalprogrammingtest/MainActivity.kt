package com.codelabs.diagnalprogrammingtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.codelabs.diagnalprogrammingtest.feature_movies.data.MovieRepository
import com.codelabs.diagnalprogrammingtest.feature_movies.presentation.MovieScreen
import com.codelabs.diagnalprogrammingtest.feature_movies.presentation.MovieViewModel
import com.codelabs.diagnalprogrammingtest.ui.theme.DiagnalProgrammingTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiagnalProgrammingTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieScreen(
                        viewModel = MovieViewModel(MovieRepository(LocalContext.current))
                    )
                }
            }
        }
    }
}
