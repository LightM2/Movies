package com.svitlanamozharovska.movies

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataModel(
    //@Expose
    @SerializedName("title")
    val title: String,
    //@Expose
    @SerializedName("year")
    val year: Int = 0,
    //@Expose
    @SerializedName("genre")
    val genre: List<String>,
    //@Expose
    @SerializedName("director")
    val director: String,
    //@Expose
    @SerializedName("desription")
    val desription: String,
    //@Expose
    @SerializedName("image")
    val image: String



)