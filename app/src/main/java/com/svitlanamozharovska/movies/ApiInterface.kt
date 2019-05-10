package com.svitlanamozharovska.movies

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("movies")
    fun getMovies(): Call<CategoryResponse>//List<DataModel>>

    companion object Factory {
        val BASE_URL = "http://demo0216585.mockable.io/"
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}