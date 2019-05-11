package com.svitlanamozharovska.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList

class Main2Activity : AppCompatActivity() {

    var dataList = ArrayList<DataModel>()
    var yearFilters = listOf<String>()
    var directorsFilters = listOf<String>()
    var genresFilters = listOf<String>()

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewGenres: RecyclerView
    lateinit var recyclerViewYears: RecyclerView
    lateinit var recyclerViewDirectors: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = DataAdapter(dataList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerViewYears = findViewById(R.id.recyclerviewYears)
        recyclerViewYears.layoutManager = LinearLayoutManager(this)

        recyclerViewDirectors = findViewById(R.id.recyclerviewDirectors)
        recyclerViewDirectors.layoutManager = LinearLayoutManager(this)

        recyclerViewGenres = findViewById(R.id.recyclerviewGenres)
        recyclerViewGenres.layoutManager = LinearLayoutManager(this)


        val filters = intent.getStringExtra(FiltersActivity.TOTAL_FILTERS)
        if (filters != null) {
            val listOfFilters = stringToList(filters)
            val filtersName = FiltersName()
            yearFilters = sortFitlers(listOfFilters, filtersName.years)
            recyclerViewYears.adapter = AdapterForMain2(yearFilters, this)
            directorsFilters = sortFitlers(listOfFilters, filtersName.directors)
            recyclerViewDirectors.adapter = AdapterForMain2(directorsFilters, this)
            genresFilters = sortFitlers(listOfFilters, filtersName.genres)
            recyclerViewGenres.adapter = AdapterForMain2(genresFilters, this)
            callWebService()


        }
    }

    fun sortFitlers(filters: List<String>, mainFilters: List<String>): MutableList<String> {
        var sortedFilters = mutableListOf<String>()
        for (i in 0 until filters.size) {
            for (j in 0 until mainFilters.size) {
                if (filters[i].equals(mainFilters[j])) {
                    sortedFilters.add(filters[i])
                }
            }
        }
        return sortedFilters

    }

    fun callWebService() {
        val apiService = ApiInterface.create()
        val call = apiService.getMovies()
        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: retrofit2.Response<CategoryResponse>?) {
                if (response != null) {
                    val newList = ArrayList<DataModel>()
                    newList.addAll(response.body()!!.values!!)

                    if (yearFilters.isNotEmpty()) {
                        for (i in 0 until yearFilters.size) {
                            for (j in 0 until newList.size) {
                                val year = newList[j].year.toString()
                                if (yearFilters[i].equals(year)&& !dataList.contains(newList[j])) {
                                    dataList.add(newList[j])
                                }else if (year.equals("All")){
                                    dataList.clear()
                                    dataList.addAll(newList)
                                }
                            }
                        }
                    }

                    if (directorsFilters.isNotEmpty()) {
                        for (i in 0 until directorsFilters.size) {
                            for (j in 0 until newList.size) {
                                val director = newList[j].director
                                if (directorsFilters[i].equals(director)&& !dataList.contains(newList[j])) {
                                    dataList.add(newList[j])
                                }else if (director.equals("All")){
                                    dataList.clear()
                                    dataList.addAll(newList)
                                }
                            }
                        }

                    }

                    if (genresFilters.isNotEmpty()) {
                        for (i in 0 until genresFilters.size) {
                            for (j in 0 until newList.size) {
                                val genres = newList[j].genre
                                for (k in 0 until genres.size){
                                    val genr = genres[k]
                                    if (genresFilters[i].equals(genr)&& !dataList.contains(newList[j])){
                                        dataList.add(newList[j])
                                    }else if (genr.equals("All")){
                                        dataList.clear()
                                        dataList.addAll(newList)
                                    }
                                }
                            }
                        }
                    }
                    recyclerView.adapter?.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d("onFailure", "onFailure")
            }
        })
    }
}


