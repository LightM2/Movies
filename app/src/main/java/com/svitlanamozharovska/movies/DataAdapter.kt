package com.svitlanamozharovska.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class DataAdapter(private var dataList: List<DataModel>, private val context: Context) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayoutView = LayoutInflater.from(context).inflate(R.layout.list_view,parent,false)
        return ViewHolder(itemLayoutView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel= dataList[position]

        holder.titleTextView.text=dataModel.title
        holder.yearTextView.text= dataModel.year.toString()
        holder.directorTextView.text= dataModel.director
        holder.genreTextView.text= listToString(dataModel.genre)
        holder.desriptionTextView.text= dataModel.desription
        Picasso.get().load(dataModel.image).into(holder.imageView)
    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var titleTextView:TextView = itemLayoutView.findViewById(R.id.title)
        var yearTextView:TextView = itemLayoutView.findViewById(R.id.year)
        var directorTextView:TextView = itemLayoutView.findViewById(R.id.director)
        var genreTextView:TextView = itemLayoutView.findViewById(R.id.genre)
        var desriptionTextView:TextView = itemLayoutView.findViewById(R.id.desription)
        var imageView: ImageView = itemLayoutView.findViewById(R.id.imageView)

    }

}
