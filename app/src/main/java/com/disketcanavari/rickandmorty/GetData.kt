package com.disketcanavari.rickandmorty

import com.google.gson.annotations.SerializedName

data class GetData(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val result: ArrayList<Results>
)
