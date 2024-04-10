package com.training.pagingcompose.model

import com.google.gson.annotations.SerializedName


data class Content (

  @SerializedName("name"         ) var name         : String? = null,
  @SerializedName("poster-image" ) var posterImage : String? = null

)