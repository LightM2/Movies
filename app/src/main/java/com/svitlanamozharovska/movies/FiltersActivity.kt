package com.svitlanamozharovska.movies

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FiltersActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    private val APP_PREFERENCES = "meSettings"
    private val APP_PREFERENCES_COUNTER = "counter"

    lateinit var recyclerViewGenres: RecyclerView
    lateinit var recyclerViewYears: RecyclerView
    lateinit var recyclerViewDirectors: RecyclerView
    lateinit var button: Button
    private var filtersInString:String = ""
    companion object {
        const val TOTAL_YEARS = "total_years"
        const val TOTAL_DIRECTORS = "total_directors"
        const val TOTAL_FILTERS = "total_filters"
        var filters = mutableListOf<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        val dataListYears = listOf<String>("All","1954","1957","1972","1974","1975","1990","1993","1994","1997","1998"
            ,"1999","2001","2002","2003","2008","2014")
        val dataListDirectors = listOf<String>("All","Akira Kurosawa","Christopher Nolan","David Fincher","Fernando Meirelles"
            ,"Francis Ford Coppola","Frank Darabont","Martin Scorsese","Milos Forman","Peter Jackson","Quentin Tarantino"
            ,"Robert Zemeckis","Roberto Benigni","Sidney Lumet","Steven Spielberg")
        val dataListGenres = listOf<String>("All","Action","Adventure","Biography","Comedy","Crime","Drama","Fantasy"
            ,"History","Romance","Sci-Fi","War")



        button = findViewById(R.id.apply_filter_button)
        button.setOnClickListener {
            val intent = Intent(this,Main2Activity::class.java)
            filtersInString = listToString(filters)
            Log.d("Movies", "filters $filtersInString")
            intent.putExtra(FiltersActivity.TOTAL_FILTERS, filtersInString)
            startActivity(intent)
        }

        recyclerViewGenres = findViewById(R.id.recyclerviewGenres)
        recyclerViewGenres.adapter= FiltersAdapter(dataListGenres,this)
        recyclerViewGenres.layoutManager= LinearLayoutManager(this)

        recyclerViewYears = findViewById(R.id.recyclerviewYears)

        recyclerViewYears.adapter = FiltersAdapter(dataListYears, this)
        recyclerViewYears.layoutManager = LinearLayoutManager(this)

        recyclerViewDirectors = findViewById(R.id.recyclerviewDirectors)

        recyclerViewDirectors.adapter = FiltersAdapter(dataListDirectors, this)
        recyclerViewDirectors.layoutManager = LinearLayoutManager(this)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_filters_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.clear_filters -> {
            filters.clear()
            msgShow("clear")
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun msgShow(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        val editor = pref.edit()
        editor.putString(APP_PREFERENCES_COUNTER, filtersInString)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()

        if (pref.contains(APP_PREFERENCES_COUNTER)){
            val saveFilters = pref.getString(APP_PREFERENCES_COUNTER,"")
            if (saveFilters!=null) {
                filters = stringToList(saveFilters).toMutableList()
            }
        }
    }
}

fun listToString(list: List<String>):String{
    var listInString:String = ""
    for (i in 0 until list.size){
        if (i == list.size-1){
            listInString += list[i]
        }else listInString += "${list[i]},"
    }
    return listInString
}

fun stringToList(string: String): List<String> {
    val stringToList = string.split(",")
    return stringToList

}
