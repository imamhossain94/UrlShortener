package com.newagedevs.url_shortener.model.cuttly

import com.google.gson.annotations.SerializedName


data class Cuttly (

  @SerializedName("url" ) var url : CuttlyData? = CuttlyData()

)