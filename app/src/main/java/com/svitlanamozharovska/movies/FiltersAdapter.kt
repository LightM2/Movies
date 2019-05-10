package com.svitlanamozharovska.movies

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView


class FiltersAdapter(private var dataList: List<String>, private val context: Context) : RecyclerView.Adapter<FiltersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayoutView = LayoutInflater.from(context).inflate(R.layout.filtes_list_view,parent,false)
        return ViewHolder(itemLayoutView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel= dataList[position]
        holder.filtersButton.text = dataModel
        holder.filtersButton.setOnClickListener(View.OnClickListener {
            if (holder.filtersButton.isChecked){
                FiltersActivity.filters.add(dataModel)
                Log.d("Movies", "${FiltersActivity.filters}")
            }else if (FiltersActivity.filters.contains(dataModel)){
                FiltersActivity.filters.remove(dataModel)
            }
        })

    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var filtersButton:CheckBox = itemLayoutView.findViewById(R.id.filterCheckBox)

    }

}
