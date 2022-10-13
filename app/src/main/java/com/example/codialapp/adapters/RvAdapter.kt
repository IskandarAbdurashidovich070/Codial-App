package com.example.codialapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.Models.Courses
import com.example.codialapp.databinding.ItemRvBinding

class RvAdapter(var list: List<Courses>, var click: Click) : RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(var rvItemBinding: ItemRvBinding):RecyclerView.ViewHolder(rvItemBinding.root){
        fun onBind(courses: Courses, position: Int ){
            rvItemBinding.name.text = courses.name
            rvItemBinding.card.setOnClickListener {
                click.onClick(courses, position )
            }
            rvItemBinding.card.setOnLongClickListener {
                click.onLongClick(courses, position)
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context) , parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}

interface Click{
    fun onClick(courses: Courses, position: Int)
    fun onLongClick(courses: Courses, position: Int)
}

