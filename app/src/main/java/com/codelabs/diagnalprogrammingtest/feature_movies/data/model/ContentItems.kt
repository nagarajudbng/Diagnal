package com.training.pagingcompose.model

import com.google.gson.annotations.SerializedName


data class ContentItems (

  @SerializedName("content" ) var content : ArrayList<Movie> = arrayListOf()

)