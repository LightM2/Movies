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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        recyclerView= findViewById(R.id.recyclerView)
        recyclerView.adapter= DataAdapter(dataList,this)
        recyclerView.layoutManager= LinearLayoutManager(this)

        val filters = intent.getStringExtra(FiltersActivity.TOTAL_FILTERS)
        if (filters!=null){
            val listOfFilters = stringToList(filters)
        }
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
                    recyclerView.adapter?.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.d("onFailure","onFailure")
            }
        })
    }
}


