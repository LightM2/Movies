package com.svitlanamozharovska.movies

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class AdapterForMain2(private var dataList: List<String>, private val context: Context) : RecyclerView.Adapter<AdapterForMain2.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayoutView = LayoutInflater.from(context).inflate(R.layout.list_view2,parent,false)
        return ViewHolder(itemLayoutView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel= dataList[position]
        holder.filtersText.text = dataModel
        /*holder.filtersButton.setOnClickListener{
            Log.d("Movies","clear")
        }*/

    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        /*var filtersButton:ImageButton = itemLayoutView.findViewById(R.id.imageButton)*/
        var filtersText:TextView = itemLayoutView.findViewById(R.id.textView)

    }


}