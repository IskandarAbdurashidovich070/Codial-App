package com.example.codialapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.Models.Courses
import com.example.codialapp.Models.Mentors
import com.example.codialapp.databinding.RvItemMentorsBinding

class RvMentorsAdapter(var list: List<Mentors>, var click1: Click1) : RecyclerView.Adapter<RvMentorsAdapter.Vh>() {

    inner class Vh(var rvItemBinding: RvItemMentorsBinding):RecyclerView.ViewHolder(rvItemBinding.root){
        fun onBind(mentors: Mentors, position: Int ){
            rvItemBinding.name.text = mentors.name

            rvItemBinding.delete.setOnClickListener {
                click1.onClick(mentors, position)
            }
            rvItemBinding.edit.setOnClickListener {
                click1.onClick1(mentors, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemMentorsBinding.inflate(LayoutInflater.from(parent.context) , parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

}

interface Click1{
    fun onClick(mentors: Mentors, position: Int)
    fun onClick1(mentors: Mentors, position: Int)
}