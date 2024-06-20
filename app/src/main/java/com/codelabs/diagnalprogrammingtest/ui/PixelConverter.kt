package com.codelabs.diagnalprogrammingtest.ui

import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun Float.pxToDp(context: Context): Float =
    (this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))


@Composable
fun ptToSp(pt:Float):Float{
    val scaledDensity: Float = LocalContext.current.resources.displayMetrics.density
    return pt / scaledDensity
}