package com.codelabs.diagnalprogrammingtest.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.diagnalprogrammingtest.R
import com.codelabs.diagnalprogrammingtest.feature_movies.data.local.MovieEntity
import com.codelabs.diagnalprogrammingtest.ui.theme.titilliumFamily

@Composable
fun MyCard(
    movie: MovieEntity,
    query: String
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
            movie.imageUrl?.split(".")?.get(0) ?: "","drawable", LocalContext.current.packageName)
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
                    var movieName =  if(name.length>12){
                        name.subSequence(0,11).toString()+" ..."
                    } else {
                        name
                    }
                    Text(
                    text = buildAnnotatedString {
                        append(movieName)
                        addStyle(
                            style = SpanStyle(
                                color = Color.Yellow,
                                fontWeight = FontWeight.Bold
                            ),
                            start = 0,
                            end = if (query.isNotEmpty() && name.length>query.length) query.length else 0
                        )
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