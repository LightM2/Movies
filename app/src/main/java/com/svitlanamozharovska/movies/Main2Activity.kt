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

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewGenres: RecyclerView
    lateinit var recyclerViewYears: RecyclerView
    lateinit var recyclerViewDirectors: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        recyclerView= findViewById(R.id.recyclerView)
        recyclerView.adapter= DataAdapter(dataList,this)
        recyclerView.layoutManager= LinearLayoutManager(this)


        val filters = intent.getStringExtra(FiltersActivity.TOTAL_FILTERS)
        if (filters!=null){
            val listOfFilters = stringToList(filters)
            callWebService(listOfFilters)
            val filtersName = FiltersName()
            val yearFilters = sortFitlers(listOfFilters,filtersName.years)
            if (yearFilters.isNotEmpty()){
                recyclerViewYears = findViewById(R.id.recyclerviewYears)
                recyclerViewYears.adapter = AdapterForMain2(yearFilters, this)
                recyclerViewYears.layoutManager = LinearLayoutManager(this)
            }
            val directorsFilters = sortFitlers(listOfFilters,filtersName.directors)
            if (directorsFilters.isNotEmpty()){
                recyclerViewDirectors = findViewById(R.id.recyclerviewDirectors)
                recyclerViewDirectors.adapter = AdapterForMain2(directorsFilters, this)
                recyclerViewDirectors.layoutManager = LinearLayoutManager(this)
            }
            val genresFilters = sortFitlers(listOfFilters,filtersName.genres)
            if (genresFilters.isNotEmpty()){
                recyclerViewGenres = findViewById(R.id.recyclerviewGenres)
                recyclerViewGenres.adapter= AdapterForMain2(genresFilters,this)
                recyclerViewGenres.layoutManager= LinearLayoutManager(this)
            }






        }
    }
    fun sortFitlers(filters: List<String>, mainFilters:List<String>): MutableList<String> {
        var sortedFilters = mutableListOf<String>()
        for(i in 0 until filters.size){
            for (j in 0 until mainFilters.size){
                if (filters[i].equals(mainFilters[j])){
                    sortedFilters.add(filters[i])
                }
            }
        }
        return sortedFilters

    }
    fun callWebService(filters:List<String>) {
        val apiService = ApiInterface.create()
        val call = apiService.getMovies()
        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: retrofit2.Response<CategoryResponse>?) {
                if (response != null) {
                    val newList = ArrayList<DataModel>()
                    newList.addAll(response.body()!!.values!!)
                    for (i in 0 until filters.size){
                        for (j in 0 until newList.size){
                            val year = newList[j].year.toString()
                            if (filters[i].equals(year)){
                                dataList.add(newList[j])
                            }
                        }
                    }
                    dataList.add(newList[1])
                    dataList.add(newList[2])
                    recyclerView.adapter?.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d("onFailure","onFailure")
            }
        })
    }
}


