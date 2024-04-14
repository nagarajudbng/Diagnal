package com.codelabs.diagnalprogrammingtest.feature_search.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.diagnalprogrammingtest.R
import com.codelabs.diagnalprogrammingtest.feature_movies.presentation.pxToDp
import com.codelabs.diagnalprogrammingtest.ui.theme.titilliumFamily

@Preview
@Composable
fun searchBarPreview(){
    SearchBar(Modifier.padding(horizontal = 16.dp),onSearchTextEntered={})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchTextEntered:(String)->Unit
) {

    var textState by remember { mutableStateOf("") }
    var focusState by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(156f.pxToDp(LocalContext.current.applicationContext).dp)
            .onFocusChanged {
                if (it.isFocused) {
                    focusState = it.isFocused
                } else {
                    focusState = it.isFocused
                }
            }
            .focusRequester(focusRequester),

        value = textState,
        onValueChange = {
            textState = it
            onSearchTextEntered(it)
        },
        leadingIcon = {
            if(focusState) {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        trailingIcon = {
            if(textState.isNotEmpty()){
                IconButton(
                    onClick = {
                        textState = ""
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        focusState =true
                        focusRequester.requestFocus()

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Black,
            containerColor = Color.Black,
            focusedTextColor = Color.White,
            focusedBorderColor = Color.Black

        ),
        singleLine = true,
         placeholder = {
            Text(
                stringResource(R.string.placeholder_search),
                style = TextStyle(
                    color = Color.White
                ),

                fontFamily = titilliumFamily,
                fontWeight = FontWeight.Light,
                fontSize = 36f.pxToDp(LocalContext.current).sp
            )
        }
    )
}