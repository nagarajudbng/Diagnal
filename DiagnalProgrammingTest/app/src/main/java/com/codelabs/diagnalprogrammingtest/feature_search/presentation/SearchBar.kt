package com.codelabs.diagnalprogrammingtest.feature_search.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.codelabs.diagnalprogrammingtest.ui.pxToDp
import com.codelabs.diagnalprogrammingtest.ui.theme.titilliumFamily

@Preview
@Composable
fun searchBarPreview(){
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchTextEntered: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onBackPressed: ()-> Unit,
    onClearPressed:()->Unit,
    searchQueryState:String,
    onFocusState:Boolean
) {
    var textState = searchQueryState
    var focusState =  onFocusState
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(156f.pxToDp(LocalContext.current.applicationContext).dp)
            .onFocusChanged {
                Log.d("SearchBar",  "value = "+focusState)
                onFocusChange(it.isFocused)
            }
            .focusRequester(focusRequester),

        value = textState,
        onValueChange = {
            onSearchTextEntered(it)

        },
        leadingIcon = {
            if(focusState) {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        onBackPressed()
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
                        onSearchTextEntered("")
                        onClearPressed()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Clear,
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