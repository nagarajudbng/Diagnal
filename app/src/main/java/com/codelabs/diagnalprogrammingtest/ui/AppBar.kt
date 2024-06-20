package com.codelabs.diagnalprogrammingtest.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.diagnalprogrammingtest.ui.theme.titilliumFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    title: String,
    modifier: Modifier = Modifier,
    searchClick: () -> Unit,
    backClick: () -> Unit
) {
        TopAppBar(
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = backClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = White
                    )
                }
            },
            title = {
                Text(
                    text = title,
                    color = White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = titilliumFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { ptToSp(titleTextSize).sp }
                )
            },

            colors = TopAppBarDefaults.smallTopAppBarColors(Color.Black),
            actions = {
                IconButton(onClick = searchClick) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "",
                        tint = White
                    )
                }
            }


        )
}

@Preview
@Composable
fun HomeAppBarPreview() {
    HomeAppBar(title = "Romantic Comedy", searchClick = { }, backClick = { })
}