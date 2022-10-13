package com.example.codialapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codialapp.Models.Students
import com.example.codialapp.adapters.RvMentorsAdapter
import com.example.codialapp.databinding.RvItemMentorsBinding

class RvAdapterStudents(var list: List<Students>, var clickStudents: ClickStudents) : RecyclerView.Adapter<RvAdapterStudents.Vh>() {

    inner class Vh(var rvItemBinding: RvItemMentorsBinding):RecyclerView.ViewHolder(rvItemBinding.root){
        fun onBind(students: Students, position: Int ){
            rvItemBinding.name.text = students.name
            rvItemBinding.delete.setOnClickListener {
                clickStudents.delete(students, position)
            }
            rvItemBinding.edit.setOnClickListener {
                clickStudents.edit(students, position)
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
interface ClickStudents{
    fun delete(students: Students, position: Int)
    fun edit(students: Students, position: Int)
}