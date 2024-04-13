package com.training.pagingcompose.model

import com.google.gson.annotations.SerializedName
import com.training.pagingcompose.model.Page


data class MovieJSON (

  @SerializedName("page" ) var page : Page? = Page()

)