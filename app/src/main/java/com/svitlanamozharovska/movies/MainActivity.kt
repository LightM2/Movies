package com.svitlanamozharovska.movies

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class MainActivity : AppCompatActivity(){
    lateinit var progerssProcessDialog: ProgressDialog
    var dataList = ArrayList<DataModel>()

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView= findViewById(R.id.recyclerview)



        progerssProcessDialog= ProgressDialog(this)
        progerssProcessDialog.setTitle("Loading")
        progerssProcessDialog.setCancelable(false)
        progerssProcessDialog.show()

        callWebService()
        recyclerView.adapter= DataAdapter(dataList,this)
        recyclerView.layoutManager= LinearLayoutManager(this)



    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.filter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.filters_settings -> {
            val intentt = Intent(this,FiltersActivity::class.java)

            var dataListYears = "All"
            var dataListDirectors = "All"


            for(i in 1 until dataList.size){
                dataListYears += ",${dataList[i].year}"
                dataListDirectors += ",${dataList[i].director.toString()}"
            }



            intentt.putExtra(FiltersActivity.TOTAL_YEARS,dataListYears)
            intentt.putExtra(FiltersActivity.TOTAL_DIRECTORS,dataListDirectors)

            startActivity(intentt)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun callWebService() {
        val apiService = ApiInterface.create()
        val call = apiService.getMovies()
        Log.d("REQUEST", call.toString() + "")
        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: retrofit2.Response<CategoryResponse>?) {
                if (response != null) {
                    if (progerssProcessDialog != null && progerssProcessDialog!!.isShowing()) {
                        progerssProcessDialog.dismiss()
                    }
                    dataList.addAll(response.body()!!.values!!)
                    recyclerView.adapter?.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {

                if (progerssProcessDialog != null && progerssProcessDialog.isShowing()) {
                    progerssProcessDialog.dismiss()
                }
            }
        })
    }

}
